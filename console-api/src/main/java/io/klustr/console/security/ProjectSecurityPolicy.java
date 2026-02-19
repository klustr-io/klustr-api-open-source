package io.klustr.console.security;

import io.klustr.AbstractApiSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PermissionProvider;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.projects.Project;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements the security policy for accessing a project.
 */
@Component
public class ProjectSecurityPolicy extends AbstractApiSecurityPolicy<Project> {

    private static final Logger log = LoggerFactory.getLogger(ProjectSecurityPolicy.class);

    private final PermissionProvider permissions;

    private final OrganizationSecurityPolicy organizationPolicy;

    private final Storage storage;

    public ProjectSecurityPolicy(Storage storage, OrganizationSecurityPolicy organizationPolicy, PermissionProvider permissions) {
        this.storage = storage;
        this.permissions = permissions;
        this.organizationPolicy = organizationPolicy;
    }

    @Override
    public boolean hasReadAccess(OAuth2AuthenticatedPrincipal user, Project project) {
        return true;
    }

    @Override
    public boolean hasDeleteAccess(OAuth2AuthenticatedPrincipal user, Project project) {
        return true;
    }

    @Override
    public boolean hasCreateAccess(OAuth2AuthenticatedPrincipal user, Project project) {
        return true;
    }

    @Override
    public boolean hasAnyOfThesePermissions(OAuth2AuthenticatedPrincipal user,
                                            String projectId, String ... permissions) {
        return true;
    }

    @Override
    public boolean isOwner(OAuth2AuthenticatedPrincipal user, Project obj) {
        if (user == null) {
            return false;
        }
        if (obj == null) return false;
        String email = PrincipleUtils.tryGetEmailForUser(user);
        if (StringUtils.isBlank(email)) {
            return false;
        }
        String md5_email = U.md5(email);
        if (obj.getOwner() != null && obj.getOwner().getId() != null) {
            if (obj.getOwner().getId().equalsIgnoreCase(email)) {
                return true;
            }
            if (obj.getOwner().getId().equalsIgnoreCase(md5_email)) {
                return true;
            }
            if (obj.getOwner().getId().equalsIgnoreCase(user.getName())) {
                return true;
            }
        }

        return false;
    }

    public Project checkPermissionAndGetProject(String projectId, String appId, OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        if (project == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found."
            );
        }

        if (!this.hasUpdateAccess(oauth, project)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Does not have update access for application."
            );
        }

        if (project.getApp() == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project does not contain the app."
            );
        }

        if (project.getApp().getId().equalsIgnoreCase(appId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project does not contain this application."
            );
        }
        return project;
    }

}
