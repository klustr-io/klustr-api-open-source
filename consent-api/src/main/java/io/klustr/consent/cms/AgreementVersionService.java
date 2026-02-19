package io.klustr.consent.cms;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.SemanticVersion;
import io.klustr.console.agreements.AgreementVersionLogic;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.cdn.CdnHost;
import io.klustr.integrations.cdn.CdnProvider;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementContent;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.agreements.AgreementVersionType;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.storage.StorageObject;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.util.*;

@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/agreements/{agreementId}")
@Tag(name = "Agreements API", description = "Provides the functionality to manage, record, and track agreements for orgs, apps, and experiments.")
public class AgreementVersionService {

    private final AgreementStorageProvider agreementFileStorage;
    private final Storage storage;

    private static final Tika tika = new Tika();

    private final CdnProvider cdn;

    public AgreementVersionService(Storage storage, AgreementStorageProvider agreementStorage, CdnProvider cdn) {
        this.agreementFileStorage = agreementStorage;
        this.storage = storage;
        this.cdn = cdn;
    }

    @GetMapping("/versions")
    @Operation(
summary = "Retrieve all versions of a specific agreement",
            operationId = "getAgreementVersions",
            description = """
This endpoint allows users to access all historical versions of a specified agreement. It provides insights into changes and updates made over time, enabling effective management of agreements. Ideal for organizations needing to track the evolution of their agreements.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Map<SemanticVersion, AgreementVersionHierarchy> getVersions(
            @PathVariable("agreementId") String agreementId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);

        // ensure we sort versions
        agreement.getVersions().sort(new Comparator<AgreementVersion>() {
            @Override
            public int compare(AgreementVersion o1, AgreementVersion o2) {
                SemanticVersion v1 = new SemanticVersion(o1.getVersionNumber());
                SemanticVersion v2 = new SemanticVersion(o2.getVersionNumber());
                int i = v1.compareTo(v2);
                return i;
            }
        });

        // find the active version
        Optional<AgreementVersion> latestVersion = AgreementVersionLogic.getLatestVersion(agreement);
        final SemanticVersion activeVersion = !latestVersion.isEmpty() && !agreement.getVersions().isEmpty() ?
                new SemanticVersion(latestVersion.get().getVersionNumber()) : new SemanticVersion("1.0.0");
        AgreementVersionValidation validator = new AgreementVersionValidation();

        Map<SemanticVersion, AgreementVersionHierarchy> versions = Maps.newConcurrentMap() ;
        agreement.getVersions().stream().forEach(agreementVersion -> {
            SemanticVersion semanticVersion = new SemanticVersion(agreementVersion.getVersionNumber());
            if (semanticVersion.isMajor()) {
                versions.put(semanticVersion, new AgreementVersionHierarchy());
                AgreementVersionHierarchy result = versions.get(semanticVersion);
                result.major = agreementVersion;
                result.active = activeVersion.equals(semanticVersion);
                result.validation.put(semanticVersion.toString(), validator.validate(agreement, agreementVersion));
            } else {
                SemanticVersion major = semanticVersion.getMajorVersion();
                versions.get(major).minors.add(agreementVersion);
                versions.get(major).validation.put(semanticVersion.toString(), validator.validate(agreement, agreementVersion));
            }
        });

        // reverse it for desc order
        Map<SemanticVersion, AgreementVersionHierarchy> reverseMap = new TreeMap<>(Collections.reverseOrder());
        reverseMap.putAll(versions);

        return reverseMap;
    }

    public static class AgreementVersionHierarchy {
        public AgreementVersion major;
        public boolean active;
        public List<AgreementVersion> minors = Lists.newArrayList();

        public Map<String, AgreementVersionValidationResult> validation = Maps.newConcurrentMap();
    }

    public static class NewAgreementVersionRequest {
        public String major_version;
    }

    @PostMapping("/versions/{type}")
    @Operation(
summary = "Add a major version to an agreement",
            operationId = "addMajorAgreementVersion",
            description = """
This endpoint allows users to add a major version to an existing agreement within the organization. Ensure that the provided agreement ID and version type are valid for a successful update. This operation is crucial for managing agreement versions effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion addMajorVersion(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("type") String agreementVersionType,
            @RequestBody NewAgreementVersionRequest body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        AgreementVersionType versionType = AgreementVersionType.fromValue(agreementVersionType);

        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }

        Optional<AgreementVersion> maxVersionNumber = agreement.getVersions().stream().max(new Comparator<AgreementVersion>() {
            @Override
            public int compare(AgreementVersion o1, AgreementVersion o2) {
                return new SemanticVersion(o1.getVersionNumber()).compareTo(new SemanticVersion(o2.getVersionNumber()));
            }
        });

        // if this is minor check and see if someone specified the major version it is based on
        // this enables a person to go back in time and make an update to a minor version even
        // though there is a major version. For example 1.0.0 exists and a new 2.0.0 exists, the user
        // wants to add a minor update to 1.0.0.
        if (versionType == AgreementVersionType.MINOR) {
            if (body != null && body.major_version != null) {
                SemanticVersion semanticVersion = new SemanticVersion(body.major_version);
                maxVersionNumber = agreement.getVersions().stream().filter(x -> {
                    return new SemanticVersion(x.getVersionNumber()).equals(semanticVersion);
                }).findFirst();
            }
        }

        // find the base version number we are currently at
        SemanticVersion baseVersion = maxVersionNumber.map(agreementVersion -> new SemanticVersion(agreementVersion.getVersionNumber())).orElseGet(() -> new SemanticVersion("1.0.0"));
        // add a new version (minor or major update)
        SemanticVersion.Version updateType = versionType == AgreementVersionType.MAJOR ? SemanticVersion.Version.Major : SemanticVersion.Version.Minor;
        String newVersionNumber = maxVersionNumber.isEmpty() ? baseVersion.toString() : baseVersion.increment(updateType).toString();

        AgreementVersion version = new AgreementVersion()
                .withId(UUID.randomUUID().toString())
                .withVersionNumber(newVersionNumber)
                .withVersionType(versionType)
                .withStatus(AgreementVersion.Status.DRAFT)
                .withModifiedDate(DateTime.now());
        agreement.getVersions().add(version);

        this.storage.agreements().updateObject(agreement.getId(), agreement);
        return version;
    }

    public static class AgreementContentRequestBody {
        public String url;
        public String title;
        public String mime_type;
    }

    @GetMapping(value = "/versions/{versionId}")
    @Operation(
summary = "Retrieve a specific version of an agreement.",
            operationId = "getAgreementVersion",
            description = """
This endpoint allows users to retrieve a specific version of an agreement using its ID. It is crucial for tracking changes and managing agreements effectively. Ensure you have the necessary permissions to access this version.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion getVersion(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }
        return match.get();
    }

    @GetMapping(value = "/versions/{versionId}/errors")
    @Operation(
summary = "Retrieve validation details for an agreement version",
            operationId = "getAgreementVersionValidation",
            description = """
This endpoint provides authorized users with validation details for a specific version of an agreement. It ensures compliance and correctness in managing agreements. Use this operation to verify the integrity of agreement versions effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersionValidationResult getVersionValidation(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }
        return new AgreementVersionValidation().validate(agreement, match.get());
    }

    @PutMapping(value = "/versions/{versionId}")
    @Operation(
summary = "Update a specific version of an agreement",
            operationId = "updateAgreementVersion",
            description = """
This endpoint allows authenticated users to update a specific version of an agreement using its ID and the version ID. The request must include the updated details in the body. Proper authentication is required to ensure secure access to this operation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion updateVersion( @PathVariable("agreementId") String agreementId,
                                           @PathVariable("versionId") String versionId,
                                           @RequestBody UpdateAgreementVersionBody body,
                                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        // update scopes for the consent
        match.get().setScopes(body.scopes.stream().map(x -> {
            return new ConsentScope().withId(x.getId());
        }).toList());
        match.get().setEffectiveDate(body.effective_date);

        this.storage.agreements().updateObject(agreement.getId(), agreement);

        return match.get();
    }

    public static class UpdateAgreementVersionBody {
        public List<ConsentAttribute> scopes = Lists.newArrayList();
        public DateTime effective_date;
    }

    @DeleteMapping(value = "/versions/{versionId}")
    @Operation(
summary = "Delete a specific version of an agreement.",
            operationId = "deleteAgreementVersion",
            description = """
This endpoint enables users to remove a particular version of an agreement using the agreementId and versionId. It is essential to ensure that the user has the appropriate permissions to execute this action, thereby maintaining the integrity of agreement records.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion deleteVerison(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        if (match.get().getStatus() == AgreementVersion.Status.DRAFT) {
            agreement.getVersions().removeIf(x -> {
                return x.getId().equalsIgnoreCase(match.get().getId());
            });
        } else {
            match.get().setStatus(AgreementVersion.Status.DELETED);
        }

        storage.agreements().updateObject(agreement.getId(), agreement);
        return match.get();
    }

    @PostMapping(value = "/versions/{versionId}/publish")
    @Operation(
summary = "Publish a specific version of an agreement",
            operationId = "publishAgreementVersion",
            description = """
This endpoint enables users to publish a specific version of an agreement. It is crucial for managing the agreement lifecycle within the organization. Ensure the correct version ID is provided to successfully publish the agreement.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersionValidationResult publish(@PathVariable("agreementId") String agreementId,
                                                    @PathVariable("versionId") String versionId,
                                                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        match.get().setStatus(AgreementVersion.Status.PUBLISHED);

        AgreementVersionValidationResult result = new AgreementVersionValidation().validate(agreement, match.get());
        if (result.errors.isEmpty()) {
            this.storage.agreements().updateObject(agreement.getId(), agreement);
        }
        return result;
    }

    public static class AgreementVersionValidation {
        public AgreementVersionValidationResult validate(Agreement agreement, AgreementVersion version) {
            // validate agreement major version has an effective date, if not, error
            AgreementVersionValidationResult r = new AgreementVersionValidationResult();
            if (version.getVersionType() == AgreementVersionType.MAJOR) {
                if (version.getEffectiveDate() == null) {
                    r.errors.add("No effective date.");
                }
            }
            if (version.getContent() == null || version.getContent().isEmpty()) {
                r.errors.add("Must have at least on content.");
            }
            return r;
        }
    }

    public static class AgreementVersionValidationResult {
        public List<String> errors = Lists.newArrayList();
    }

    @PostMapping(value = "/versions/{versionId}/{language}/upload")
    @Operation(
summary = "Upload content for a specific agreement version.",
            operationId = "uploadAgreementContent",
            description = """
This endpoint enables users to upload content linked to a specific agreement version. It requires the agreement ID and version ID to ensure accurate management of the associated materials. This functionality is crucial for keeping agreement documentation current and accessible.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UploadContentResponse uploadContent(@PathVariable("agreementId") String agreementId,
                                               @PathVariable("versionId") String versionId,
                                               @PathVariable("language") String language,
                                               @RequestParam(name = "filename") String filename,
                                               @RequestBody byte[] bits,
                                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(bits);
            String mimeType = tika.detect(stream);
            IOUtils.closeQuietly(stream);

            String extension = null;
            if (mimeType.equalsIgnoreCase("text/html")) {
                extension = "html";
            } else if (mimeType.equalsIgnoreCase("text/markdown")) {
                extension = "md";
            } else if (mimeType.equalsIgnoreCase("text/plain")) {
                extension = "txt";
            }
            if (StringUtils.isBlank(extension)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Content type could not be determined or is not supportd [" + mimeType + "]"
                );
            }

            if (StringUtils.isNotBlank(filename)) {
                if (filename.matches(".*.md")) {
                    extension = ".md";
                    mimeType = "text/markdown";
                } else if (filename.matches(".*.html")) {
                    extension = ".html";
                    mimeType = "text/html";
                } else if (filename.matches(".*.txt")) {
                    extension = ".txt";
                    mimeType = "text/plain";
                }
            }

            // put into storage for versioning and legal compliance
            StorageObject storedDocument = this.agreementFileStorage.putAgreement(agreementId, language, mimeType, bits);

            String path = agreementId + "/" + versionId;

            String url = this.cdn.upload(path, language + extension, mimeType, bits).url;
            UploadContentResponse res = new UploadContentResponse();
            res.url = url;
            res.mime_type = mimeType;
            return res;
        } catch (Exception ex) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Content is invalid."
            );
        }
    }

    public static class UploadContentResponse {
        public String url;
        public String mime_type;
    }

    @PostMapping(value = "/versions/{versionId}/{language}/content")
    @Operation(
summary = "Update content for a specific agreement version",
            operationId = "updateAgreementVersionContent",
            description = """
This endpoint allows users to update the content for a specific version of an agreement. Ensure that the correct agreementId and versionId are provided for accurate updates. This operation is essential for maintaining the integrity and relevance of agreement details.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementContent createOrUpdateContent(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @PathVariable("language") String language,
            @RequestBody AgreementContentRequestBody body,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        List<AgreementContent> contents = match.get().getContent();
        Optional<AgreementContent> exists = contents.stream().filter(x -> {
            return x.getLocale().equalsIgnoreCase(language);
        }).findFirst();

        AgreementContent content = new AgreementContent()
                .withTitle(body.title)
                .withMimeType(body.mime_type)
                .withLocale(language)
                .withUrl(body.url)    // TODO get the URL for the agreement
                .withDateCreated(DateTime.now())
                .withDateModified(DateTime.now());

        match.get()
                .withModifiedDate(DateTime.now());

        if (exists.isEmpty()) {
            match.get().getContent().add(content);
        } else {
            content = exists.get()
                    .withTitle(body.title)
                    .withDateModified(DateTime.now())
                    .withMimeType(content.getMimeType())
                    .withLocale(content.getLocale())
                    .withUrl(body.url);
        }

        this.storage.agreements().updateObject(agreement.getId(), agreement);

        return content;
    }

    @DeleteMapping(value = "/versions/{versionId}/{language}/content")
    @Operation(
summary = "Delete content for a specific agreement version",
            operationId = "deleteAgreementVersionContent",
            description = """
This endpoint allows users to delete content for a specified version of an agreement. It is essential to provide the correct version and language to prevent accidental deletions. This operation helps maintain the integrity of agreement data.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion deleteUpdateContent(            @PathVariable("agreementId") String agreementId,
                                                            @PathVariable("versionId") String versionId,
                                                            @PathVariable("language") String language,
                                                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> version = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (version.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        Optional<AgreementContent> match = version.get().getContent().stream().filter(x -> {
            return x.getLocale().equalsIgnoreCase(language);
        }).findFirst();

        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
        }

        // remove it from the object
        version.get().getContent().remove(match.get());
        this.storage.agreements().updateObject(agreementId, agreement);

        return version.get();
    }

    @PostMapping("/versions/{versionId}/copy/{type}")
    @Operation(
summary = "Create a new major version of an agreement",
            operationId = "forkMajorVersionAgreement",
            description = """
This endpoint allows users to create a new major version of an existing agreement. It is essential for managing agreement versions within an organization. Ensure that you have the necessary permissions to access and modify agreement details.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion forkMajorVersion(            @PathVariable("agreementId") String agreementId,
                                                    @PathVariable("versionId") String versionId,
                                                         @PathVariable("type") String type,
                                                    @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {

        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> versionMatch = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (versionMatch.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }

        AgreementVersion version = U.fromJson(U.toJson(versionMatch.get()), AgreementVersion.class);
        version.setId(UUID.randomUUID().toString());
        version.setModifiedDate(DateTime.now());
        version.setStatus(AgreementVersion.Status.DRAFT);
        version.setEffectiveDate(null);

        // ensure updated version
        SemanticVersion semVersion = new SemanticVersion(versionMatch.get().getVersionNumber());
        if (type.equalsIgnoreCase("major")) {
            semVersion = semVersion.increment(SemanticVersion.Version.Major);
            version.setVersionType(AgreementVersionType.MAJOR);
        } else {
            semVersion = semVersion.increment(SemanticVersion.Version.Minor);
            version.setVersionType(AgreementVersionType.MINOR);
        }
        version.setVersionNumber(semVersion.toString());
        version.setContent(Lists.newArrayList());   // empty content
        agreement.getVersions().add(version);
        this.storage.agreements().updateObject(agreement.getId(), agreement);
        return version;
    }

    @DeleteMapping("/versions/{versionId}/{language}")
    @Operation(
summary = "Remove a specific version of an agreement",
            operationId = "removeAgreementVersion",
            description = """
This endpoint allows users to delete a specific version of an agreement. It is crucial to provide the correct version ID to prevent accidental deletions. This action helps maintain the integrity of agreement records.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementVersion removeVersion(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @PathVariable("language") String language,
            @RequestBody String markdown,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);
        if (agreement == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agreement not found");
        }
        Optional<AgreementVersion> match = agreement.getVersions().stream().filter(x -> {
            return Objects.equals(x.getId(), versionId);
        }).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version not found");
        }
        Optional<AgreementContent> v = match.get().getContent().stream().filter(x -> {
            return x.getLocale().equalsIgnoreCase(language);
        }).findFirst();

        if (v.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Version with locale not found.");
        }

        match.get().getContent().removeIf(x -> {
            return x.getLocale().equalsIgnoreCase(language);
        });

        match.get().withModifiedDate(DateTime.now());

        this.storage.agreements().updateObject(agreement.getId(), agreement);
        return match.get();
    }
}
