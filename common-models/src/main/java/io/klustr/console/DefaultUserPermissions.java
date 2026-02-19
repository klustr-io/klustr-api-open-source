package io.klustr.permissions;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultUserPermissions {

    private final PermissionProvider permissions;
    private final Storage storage;



    public DefaultUserPermissions(PermissionProvider permissions, Storage storage) {
        this.permissions = permissions;
        this.storage = storage;


    }

    private Set<String> getUserProjects(String subjectId, String email) {
        DbQuery q = Db.query("owner").with("id").eq(email);
        Optional<Project> project = storage.projects().findFirst(q);
        return project.<Set<String>>map(value -> Sets.newHashSet(value.getId())).orElseGet(Sets::newHashSet);
    }

    private Set<String> getProjectsInOrganizations(Set<String> orgIds) {
        Set<String> ids = Sets.newHashSet();
        orgIds.forEach(orgId -> {
            DbQuery findProjectsByOrg = Db.query("org_id").eq(orgId);
            DocumentResult<Project> result = storage.projects().insecureQuery(findProjectsByOrg, Pagination.all());
            List<String> projects = result.docs.stream().map(Project::getId).toList();
            ids.addAll(projects);
        });
        return ids;
    }

    public static interface OrgProjectsResolver {
        Set<String> getProjectsInOrganizations(Set<String> orgIds);
    }

    public static interface UserSpecificProjectProvider {
        Set<String> getUserProjects(String subjectId);
    }

    public LinkedObjects resolve(String subjectId) {
        Set<String> orgs = this.getOrgs(subjectId);
        Set<String> projects = Sets.newHashSet();
        projects.addAll(this.getProjectsInOrganizations(orgs));
        projects.addAll(this.getProjects(subjectId));

        LinkedObjects r = new LinkedObjects();
        r.orgIds = orgs;
        r.projectIds =projects;
        return r;
    }

    public static class LinkedObjects {
        public Set<String> orgIds;
        public Set<String> projectIds;
    }

    private Set<String> getProjects(String subjectId) {
        // does user have their own project?
        Set<Relation> project_membership = this.permissions.listRelations(SubjectKey.userId(subjectId),
                "members",
                Target.inAny("projects"));

        Set<String> ids = project_membership.stream().map(rel -> {
            return rel.object().value();
        }).collect(Collectors.toSet());

        return ids;
    }

    private Set<String> getOrgs(String subjectId) {
        Set<Relation> orgMemberships = this.permissions.listRelations(SubjectKey.userId(subjectId), "members", Target.inAny("orgs"));

        Set<String> ids = orgMemberships.stream().map(rel -> {
            return rel.object().value();
        }).collect(Collectors.toSet());

        return ids;
    }
}
