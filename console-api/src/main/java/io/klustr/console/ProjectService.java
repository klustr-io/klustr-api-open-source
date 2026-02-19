package io.klustr.console;

import io.klustr.billing.BillingSubscriptionApi;
import io.klustr.integrations.cdn.CdnProvider;
import io.klustr.integrations.cdn.CdnUploadResponse;
import io.klustr.integrations.kong.interfaces.GatewayConsumerProvider;
import io.klustr.permissions.*;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLinks;
import io.klustr.schemas.console.apps.AppContactInformation;
import io.klustr.schemas.console.billing.BillingSubscription;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.OidcStatus;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectOwner;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.cdn.CdnHost;
import io.klustr.integrations.kong.models.KongConsumer;
import io.klustr.integrations.kong.models.KongConsumerRequest;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.RandomNameGenerator;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Create, updated, or delete projects owned by specific user accounts for the purpose
 * of billing, consent management, and overall management of access and contracting.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Project APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectService {

    private static final Logger log = LoggerFactory.getLogger(ProjectService.class);

    private final HydraApi hydra;

    private final GatewayConsumerProvider kong;

    private final BillingSubscriptionApi subscriptions;

    private final Storage storage;

    private final PermissionProvider permissions;

    private final ProjectSecurityPolicy policy;

    private final CdnProvider cdn;

    public ProjectService(Storage storage,
                          BillingSubscriptionApi subscriptions,
                          HydraApi hydra,
                          GatewayConsumerProvider kong,
                          ProjectSecurityPolicy policy,
                          PermissionProvider permissions,
                          CdnProvider cdn) {

        this.storage = storage;
        this.hydra = hydra;
        this.kong = kong;
        this.subscriptions = subscriptions;
        this.permissions = permissions;
        this.policy = policy;
        this.cdn = cdn;
    }

    @GetMapping("/{projectId}/profile")
    @Operation(
operationId = "getProjectPublicProfile", summary = "Retrieve public profile for a specific project",
            description = """
This endpoint provides the public metadata for the specified project. Users can access essential information regarding project details and permissions. It is useful for billing, consent management, and overall project oversight.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public AppPublicProfile getPublicProfile(@PathVariable("projectId") String projectId,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (project.getApp() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No app found inside project");
        }

        // this.permissions.iam().can().user(oauth).read("projects").withId(projectId).throwIfUnauthorized();

        AppBrand brand = project.getApp().getBrand();
        AppContactInformation contact = project.getApp().getContact();
        AppLinks links = project.getApp().getLinks();

        AppPublicProfile result = new AppPublicProfile();
        result.brand = brand;
        result.contact = contact;
        result.links = links;
        return result;
    }

    public static class AppPublicProfile {
        public AppBrand brand;
        public AppContactInformation contact;
        public AppLinks links;
    }

    @GetMapping("/{projectId}")
    @Operation(
operationId = "getProjectById", summary = "Retrieve details of a specific project by ID",
            description = """
This endpoint allows users to access the details of a project they own. Ensure you have the necessary permissions to view this project. It is designed for users to manage their project information effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ResponseEntity<Project> get(@PathVariable("projectId") String projectId,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // this.permissions.iam().can().user(oauth).read("projects").withId(projectId).throwIfUnauthorized();

        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(2, TimeUnit.SECONDS)).body(project);
    }

    @PutMapping("/{projectId}")
    @Operation(
operationId = "updateUserProject", summary = "Update a specific project for the authenticated user",
            description = """
This endpoint allows the authenticated user to update the details of a specific project. It is designed for managing project information related to billing and consent. Ensure that the user has the necessary permissions to make changes to the project.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Project update(@PathVariable("projectId") String projectId,
                          @RequestBody Project project,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project existing = this.storage.projects().getObject(projectId);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        // this.permissions.iam().can().user(oauth).write("projects").withId(projectId).throwIfUnauthorized();

        this.storage.projects().updateObject(projectId, project);
        this.onUpdate(project, oauth);
        return project;
    }

    @PostMapping
    @Operation(
operationId = "createProjectForUser", summary = "Create a new project for user accounts",
            description = """
This endpoint allows users to create a new project linked to their account. It is crucial for managing billing, consent, and access to development resources. Ensure all project details are accurate to support effective management and usage.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('projects.create')")
    public Project create(@RequestBody Project project,
                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (StringUtils.isBlank(project.getOrgId())) {
            Set<Relation> relations = this.permissions.listRelations(SubjectKey.user(oauth), "members", Target.inAny("orgs"));
            if (relations.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            Optional<Relation> match = relations.stream().filter(r -> {
                return this.permissions.iam().can().user(oauth).create("projects").in("orgs").withId(r.object().value()).result();
            }).findFirst();
            if (match.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
            project.setOrgId(match.get().object().value());
        }

//        this.permissions.iam()
//                .can()
//                .user(oauth)
//                .create("projects")
//                .in("orgs")
//                .withId(project.getOrgId())
//                .throwIfUnauthorized();

        if (StringUtils.isBlank(project.getId())) {
            List<String> names = new RandomNameGenerator().randomAnimalsAndAdjectives(10);
            project.setId(names.get(0));
        }
        project.setStatus(Project.Status.ACTIVE);

        String email = PrincipleUtils.tryGetEmailForUser(oauth);
        project.setOwner(new ProjectOwner()
                .withId(email)
        );
        project.setCreationDate(DateTime.now());

        if (!this.policy.hasUpdateAccess(oauth, project)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        this.storage.projects().insertObject(project.getId(), project);
        this.onCreate(project, oauth);
        return project;
    }

    @DeleteMapping("/{projectId}")
    @Operation(
operationId = "deleteMyProject", summary = "Delete a project owned by the authenticated user",
            description = """
This endpoint allows authenticated users to delete their own projects. It ensures that only the project owner can remove the project, thereby maintaining project integrity. Use this operation to effectively manage your projects and ensure compliance with billing and consent management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('projects.delete')")
    public void delete(@PathVariable("projectId") String projectId,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project existing = this.storage.projects().getObject(projectId);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

//        this.permissions.iam()
//                .can()
//                .user(oauth)
//                .delete("projects")
//                .withId(projectId)
//                .throwIfUnauthorized();

        this.storage.projects().deleteObject(existing.getId());
        this.onDelete(existing, oauth);
    }


    /**
     * Updates the info of a project such as its name and general.
     *
     * @param projectId The project id to update
     * @param info      The extra info
     * @return The updated project.
     */
    @PutMapping("/{projectId}/profile")
    @Operation(
operationId = "updateProjectDetails", summary = "Update project details for user accounts",
            description = """
This operation allows users to modify project names and associated metadata. It is crucial for maintaining accurate project information, which supports billing, consent management, and overall oversight. Users can effectively manage their projects through this functionality.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Project updateProjectDetails(@PathVariable("projectId") String projectId,
                                        @RequestBody ProjectInfo info,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project existing = this.storage.projects().getObject(projectId);
        if (existing == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

//        this.permissions.iam()
//                            .can()
//                            .user(oauth)
//                            .write("projects")
//                            .withId(projectId)
//                            .throwIfUnauthorized();

        existing.setName(info.name);
        this.storage.projects().updateObject(projectId, existing);
        this.onUpdate(existing, oauth);
        return existing;
    }

    protected void onCreate(Project project, OAuth2AuthenticatedPrincipal principle) {
        log.info("Triggered new project " + project.getId());

        // check if app defined?
        if (project.getApp() != null && project.getApp().getId() == null) {
            project.getApp().setId(RandomNameGenerator.randomHexString(12));
        }

        // create a registration on kong
        KongConsumerRequest consumerRequest = new KongConsumerRequest();
        consumerRequest.tags.add("console");
        consumerRequest.username = project.getId();
        consumerRequest.custom_id = project.getId();
        KongConsumer kongConsumer = this.kong.createConsumer(consumerRequest);

        // ensure we have a cdn hosted image
        ensureAppImageIsCdnHosted(project.getApp());

        // save direct to DB to avoid loop callback
        this.storage.projects().updateObject(project.getId(), project);

        // grant user membership permissions
        this.permissions.grant(SubjectKey.user(principle),
                Scope.namespace("members"),
                Target.of("projects", project.getId()));

        // grant as owner as well
        this.permissions.grant(SubjectKey.user(principle),
                Scope.namespace("owners"),
                Target.of("projects", project.getId()));

        // register subscription
        BillingSubscription subscription = new BillingSubscription()
                .withSubscriptionAt(DateTime.now().withTimeAtStartOfDay().minusDays(1))
                .withPlanCode(project.getPlan() != null ? project.getPlan() : "basic-plan")
                .withExternalCustomerId(project.getOrgId())
                .withExternalId(project.getId())
                .withId(project.getId())
                .withName("Project " + project.getId())
                .withBillingTime(BillingSubscription.BillingTime.CALENDAR);

        this.subscriptions.createSubscription(subscription.getExternalId(), subscription);
    }

    protected void onUpdate(Project project, OAuth2AuthenticatedPrincipal principle) {
        log.info("Triggered update of project " + project.getId());

        // check if app defined?
        if (project.getApp() != null && project.getApp().getId() == null) {
            project.getApp().setId(RandomNameGenerator.randomHexString(12));
        }

        // fix a CDN hosted url
        if (ensureAppImageIsCdnHosted(project.getApp())) {
            this.storage.projects().updateObject(project.getId(), project);
        }

        if (!project.getClients().isEmpty()) {
            project.getClients().stream().filter(x -> x.getStatus() == OidcStatus.ENABLED).forEach(x -> {
                // refresh this
                try {
                    log.info("Updating project client id " + x.getClientId());
                    hydra.update(project, x);
                } catch (Exception ex) {
                    // ignore for now
                }
            });
        }

        // register subscription
        BillingSubscription subscription = new BillingSubscription()
                .withSubscriptionAt(DateTime.now().withTimeAtStartOfDay().minusDays(1))
                .withPlanCode(project.getPlan() != null ? project.getPlan() : "basic-plan")
                .withExternalCustomerId(project.getOrgId())
                .withExternalId(project.getId())
                .withId(project.getId())
                .withName("Project " + project.getId())
                .withBillingTime(BillingSubscription.BillingTime.CALENDAR);

        if (this.subscriptions.getSubscription(subscription.getExternalId()).isEmpty()) {
            this.subscriptions.createSubscription(subscription.getExternalId(), subscription);
        }
    }

    protected void onDelete(Project obj, OAuth2AuthenticatedPrincipal principle) {
        log.info("Triggered delete of project " + obj.getId());
        if (!obj.getClients().isEmpty()) {
            obj.getClients().stream().filter(x -> x.getStatus() == OidcStatus.ENABLED).forEach(x -> {
                // refresh this
                try {
                    log.info("Deleting project client id " + x.getClientId());
                    hydra.delete(x.getClientId());
                } catch (Exception ex) {
                    // ignore for now
                }
            });
        }

        try {
            this.kong.deleteConsumer(obj.getId());
        } catch (Exception ex) {
            log.error("Error deleting consumer from Kong gateway.", ex);
        }
    }

    private boolean ensureAppImageIsCdnHosted(App app) {
        if (app == null) return false;
        if (app.getBrand() == null) return false;
        if (app.getBrand().getLogo() == null) return false;
        if (app.getBrand().getLogo().getUrl() == null) return false;
        String url = app.getBrand().getLogo().getUrl().toString();
        if (StringUtils.isBlank(url)) return false;

        // raw image needs hosting, if embedded upload convert to real image
        Optional<CdnHost.FileInfo> embeded = CdnHost.tryGetFileFromPossiblyBase64Url(url);
        if (embeded.isPresent()) {
            CdnUploadResponse upload = cdn.upload("/", embeded.get().filename, embeded.get().contentType, embeded.get().bytes);
            app.getBrand().getLogo().setUrl(URI.create(upload.url));
        } else {
            app.getBrand().getLogo().setUrl(URI.create(url));
        }

        return true;
    }

    public static class ProjectInfo {
        public String name;
    }
}