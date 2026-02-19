package io.klustr.console;

import io.klustr.console.security.BucketSecurityPolicy;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.minio.StorageInfo;
import io.klustr.integrations.minio.StorageProvider;
import io.klustr.integrations.minio.StorageProviderResolver;
import io.klustr.permissions.exceptions.MissingPermissionsException;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.storage.ProjectStorageBucket;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.utils.RandomNameGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Project Storage APIs", description = "Creating and managing storage for your apps and services to use.")
public class ProjectStorageBucketService {

    private final ProjectSecurityPolicy projectPolicy;

    private final Storage storage;

    private final StorageProviderResolver storageResolver;

    private final PermissionProvider permissions;

    private final OrganizationSecurityPolicy organizationPolicy;

    private final BucketSecurityPolicy bucketPolicy;

    public ProjectStorageBucketService(Storage storage,
                                       StorageProviderResolver storageResolver,
                                       BucketSecurityPolicy bucketPolicy,
                                       OrganizationSecurityPolicy organizationPolicy,
                                       ProjectSecurityPolicy projectPolicy,
                                       PermissionProvider permissions) {
        this.storage = storage;
        this.storageResolver = storageResolver;
        this.projectPolicy = projectPolicy;
        this.permissions = permissions;
        this.bucketPolicy = bucketPolicy;
        this.organizationPolicy = organizationPolicy;
    }

    @GetMapping("/{projectId}/storage")
    @Operation(
description = """
This endpoint provides detailed storage metrics for the specified project. It helps users understand their storage allocation and manage resources effectively. Ideal for developers and administrators monitoring project storage usage.
""",
            operationId = "getProjectStorageUsage",
            summary = "Retrieve storage usage for a specific project.",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public DocumentResult<ProjectStorageBucket> getStorageUsage(@PathVariable("projectId") String projectId,
                                                          @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);

        // is owner of project
        if (!this.projectPolicy.isOwner(oauth, project)) {
            boolean hasAccess = this.projectPolicy.hasAnyOfThesePermissions(oauth, projectId, "projects.storage.buckets.find",
                    "projects.storage.buckets.list", "projects.storage.buckets.get");
            if (!hasAccess) {
                throw new MissingPermissionsException("projects.storage.buckets.get");
            }
        }

        DocumentResult<ProjectStorageBucket> result = this.storage.project_storage_buckets().insecureQuery(Db.query("project_id").eq(projectId),
                Pagination.all());

        // get latest info and ensure status is correct
        result.docs.forEach(doc -> {
            Optional<StorageInfo> info = Optional.empty();
            if (doc.getType() == ProjectStorageBucket.Type.PRIVATE) {
                info = this.storageResolver.getStorageProvider(project.getId(), doc.getId(), oauth).getStorageInfo();
            } else if (doc.getType() == ProjectStorageBucket.Type.PUBLIC) {
                info = this.storageResolver.getStorageProvider(project.getId(), doc.getId(), oauth).getStorageInfo();
            }
            if (info.isEmpty()) {
                doc.setStatus(ProjectStorageBucket.Status.DELETED);
                this.storage.project_storage_buckets().updateObject(doc.getId(), doc);
                return;
            } else {
                doc.setStatus(ProjectStorageBucket.Status.ACTIVE);
            }

            // refresh data
            doc.setLimitMb(info.get().quota);
            doc.setUsageMb(info.get().usage);
            doc.setObjects(info.get().count);
            this.storage.project_storage_buckets().updateObject(doc.getId(), doc);
        });

        return  result;
    }

    @DeleteMapping("/{projectId}/storage/{bucketId}")
    @Operation(
description = """
This operation permanently removes the storage bucket identified by bucketId from the project specified by projectId. All data within the bucket will be irreversibly deleted. Ensure you have the necessary permissions before executing this action.
""",
            operationId = "deleteProjectStorageBucket",
            summary = "Delete a specific storage bucket from a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deleteStorage(@PathVariable("projectId") String projectId,
                              @PathVariable("bucketId") String bucketId,
                              @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // is owner of project
        Project project = this.storage.projects().getObject(projectId);
        if (!this.projectPolicy.isOwner(oauth, project)) {
            boolean hasAccess = this.projectPolicy.hasAnyOfThesePermissions(oauth, projectId, "projects.storage.buckets.delete");
            if (!hasAccess) {
                throw new MissingPermissionsException("projects.storage.buckets.delete");
            }
        }

        ProjectStorageBucket bucket = this.storage.project_storage_buckets().getObject(bucketId);
        if (bucket == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Bucket not found."
            );
        }
        StorageProvider storageProvider;
        if (bucket.getType() == ProjectStorageBucket.Type.PRIVATE) {
            storageProvider = this.storageResolver.getStorageProvider(project.getId(), bucketId, oauth);
        } else {
            storageProvider = this.storageResolver.getPublicStorageProvider(project.getId(), bucketId, oauth);
        }
        storageProvider.deleteStorage();

        // now safe to actually delete
        this.storage.project_storage_buckets().deleteObject(bucketId);
    }

    @PostMapping("/{projectId}/storage/{tier}/{visibility}")
    @Operation(
description = """
This endpoint allows users to create a storage bucket for a specific project. Users can define the storage tier and visibility type according to their project's requirements. Ensure that the user has the necessary permissions to create the storage bucket.
""",
            operationId = "createProjectStorageBucket",
            summary = "Create a storage bucket for the specified project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ProjectStorageBucket createStorage(@PathVariable("projectId") String projectId,
                                     @PathVariable("tier") String tier,
                                     @PathVariable("visibility") String type,
                                  @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // is owner of project
        Project project = this.storage.projects().getObject(projectId);
        if (!this.projectPolicy.isOwner(oauth, project)) {
            boolean hasAccess = this.projectPolicy.hasAnyOfThesePermissions(oauth, projectId, "projects.storage.buckets.create");
            if (!hasAccess) {
                throw new MissingPermissionsException("projects.storage.buckets.create");
            }
        }

        String id = ("proj-" + projectId + "-" + type + "-" + RandomNameGenerator.randomHexString(6)).toLowerCase();

        ProjectStorageBucket bucket = new ProjectStorageBucket()
                .withId(id)
                .withProjectId(projectId)
                .withLimitMb(1_048_576L * 10L)
                .withUsageMb(0L)
                .withObjects(0L)
                .withTier(ProjectStorageBucket.Tier.fromValue(tier))
                .withType(ProjectStorageBucket.Type.fromValue(type))
                .withStatus(ProjectStorageBucket.Status.CREATING)
                .withDateCreated(DateTime.now());

        this.storage.project_storage_buckets().insertObject(bucket.getId(), bucket);

        try {
            provision(bucket, oauth);
            this.storage.project_storage_buckets().updateObject(bucket.getId(), bucket.withStatus(ProjectStorageBucket.Status.ACTIVE));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return bucket;
    }

    private Optional<StorageInfo> provision(ProjectStorageBucket bucket, @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(bucket.getProjectId());

        StorageProvider storageProvider;
        if (bucket.getType() == ProjectStorageBucket.Type.PRIVATE) {
            storageProvider = this.storageResolver.getStorageProvider(
                    project.getId(),
                    bucket.getId(),
                    oauth);
        } else {
            storageProvider = this.storageResolver.getPublicStorageProvider(
                    project.getId(),
                    bucket.getId(),
                    oauth);
        }
        Optional<StorageInfo> storageInfo = storageProvider.getStorageInfo();
        if (storageInfo.isEmpty()) {
            storageProvider.provisionStorage();;
        }
        storageProvider.grantPermission(oauth);
        return storageProvider.getStorageInfo();
    }
}
