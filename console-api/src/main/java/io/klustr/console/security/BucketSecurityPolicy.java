package io.klustr.console.security;

import com.google.common.collect.Sets;
import io.klustr.AbstractApiSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.storage.ProjectStorageBucket;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class BucketSecurityPolicy  extends AbstractApiSecurityPolicy<ProjectStorageBucket> {

    private static final Logger log = LoggerFactory.getLogger(BucketSecurityPolicy.class);

    private final PermissionProvider permissions;

    private final OrganizationSecurityPolicy organizationPolicy;

    private final Storage storage;

    private final ProjectSecurityPolicy projectPolicy;

    public BucketSecurityPolicy(Storage storage,
                                 OrganizationSecurityPolicy organizationPolicy,
                                 ProjectSecurityPolicy projectPolicy,
                                 PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
        this.projectPolicy = projectPolicy;
        this.organizationPolicy = organizationPolicy;
    }

    @Override
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, ProjectStorageBucket obj) {
        Project project = this.storage.projects().getObject(obj.getId());
        if (!projectPolicy.hasReadAccess(user, project)) {
            return false;
        }
        return hasAnyOfThesePermissions(user, obj.getProjectId(), "projects.storage.buckets.get",
                "projects.storage.buckets.list", "projects.storage.buckets.find");
    }

    @Override
    public boolean hasDeleteAccess(OAuth2AuthenticatedPrincipal user, ProjectStorageBucket obj) {
        if (!hasReadAccess(user, obj)) return false;
        return hasAnyOfThesePermissions(user, obj.getProjectId(), "projects.storage.buckets.delete");
    }

    @Override
    public boolean hasCreateAccess(OAuth2AuthenticatedPrincipal user, ProjectStorageBucket obj) {
        if (!hasReadAccess(user, obj)) return false;
        return hasAnyOfThesePermissions(user, obj.getProjectId(), "projects.storage.buckets.create");
    }

    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user,
                                             String projectId, String ... permissions) {
        // TODO fix this
        return true;
    }
}
