package io.klustr.consent.cms;


import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementContent;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.Optional;

@RestController
@Component
@RequestMapping("/directory/orgs/{organizationId}/agreements/{agreementId}")
@Tag(name = "Agreements API", description = "Provides the functionality to manage, record, and track agreements for orgs, apps, and experiments.")
public class AgreementService {

    private final AgreementStorageProvider agreementStorage;
    private final Storage storage;

    public AgreementService(Storage storage, AgreementStorageProvider agreementStorage) {
        this.agreementStorage = agreementStorage;
        this.storage = storage;
    }

    @GetMapping("/versions/{versionId}/{language}")
    @Operation(
summary = "Retrieve the latest version of an agreement.",
            operationId = "getAgreementVersion",
            description = """
Fetches the content of a specific agreement version based on the provided agreement ID and version ID. This endpoint is intended for users to access the most recent details of agreements in their organization, ensuring clarity and transparency in agreement management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AgreementContent getVersion(
            @PathVariable("agreementId") String agreementId,
            @PathVariable("versionId") String versionId,
            @PathVariable("language") String language,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Agreement agreement = this.storage.agreements().getObject(agreementId);

        Optional<AgreementVersion> version = agreement.getVersions().stream().filter(x -> {
            return x.getId() == versionId;
        }).findFirst();
        if (version.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Agreement not found"
            );
        }

        Optional<AgreementContent> content = version.get().getContent().stream().filter(x -> {
            return x.getLocale().substring(0, 2).equalsIgnoreCase(language) || x.getLocale().equalsIgnoreCase(language);
        }).findFirst();
        if (content.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Agreement content not found"
            );
        }

        return content.get();
    }
}
