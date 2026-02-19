package io.klustr.console.storage;

import com.fasterxml.jackson.databind.JsonNode;
import io.klustr.schemas.console.accounts.Account;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.experiments.ExperimentMembership;
import io.klustr.schemas.console.oidc.UserPairwiseIds;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgAssignment;
import io.klustr.schemas.console.orgs.OrgRoles;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.stats.ProjectUsers;
import io.klustr.schemas.console.storage.ProjectStorageBucket;
import io.klustr.schemas.console.users.UserAccount;
import io.klustr.schemas.console.users.UserAccountType;
import io.klustr.schemas.events.EventInviteUserRequest;
import io.klustr.schemas.services.ApiServiceSpecification;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Storage {

    private static final Logger log = LoggerFactory.getLogger(Storage.class);

    private final DbAdapter<UserPairwiseIds> user_pairwise_ids;

    private final DbAdapter<Project> projects;
    private final DbAdapter<Org> orgs;
    private final DbAdapter<OrgRoles> org_roles;
    private final DbAdapter<Agreement> agreements;

    private final DbAdapter<ExperimentMembership> experiment_membership;
    private final DbAdapter<OrgAssignment> org_assignments;
    private final DbAdapter<ApiServiceSpecification> api_catalog;

    private final DbAdapter<EventInviteUserRequest> invitesTable;

    private final DbAdapter<ProjectUsers> project_users;

    private final DbAdapter<Account> accounts;

    private final DbAdapter<Experiment> experiments;

    private final DbAdapter<ProjectStorageBucket> project_storage_buckets;

    private final DbAdapter<UserAccountType> user_account_types;

    private final DbAdapter<UserAccount> user_accounts;


    private final DbAdapter<JsonNode> cert_keys;


    @Autowired
    public Storage(DocumentDatabaseFactory pool,
                   DbAdapterBroadcasterFactory factory,
                   MeterRegistry registry) {

        this.agreements = new DbAdapter<Agreement>("orgs", "agreements", pool, Agreement.class, factory)
                .ensureIndex("org_id")
                .ensureIndex("namespace")
                .ensureIndex("status")
                .ensureIndex("object_id");

        this.orgs = new DbAdapter<Org>("orgs", "orgs", pool, Org.class, factory);

        this.org_roles = new DbAdapter<OrgRoles>("orgs", "org_roles", pool, OrgRoles.class, factory);

        this.org_assignments = new DbAdapter<OrgAssignment>("orgs", "org_assignments", pool, OrgAssignment.class, factory)
                .ensureIndex("org_id")
                .ensureIndex("assigned_to")
                .ensureIndex("assignee_type")
                .ensureIndex("customer_id");

        this.invitesTable = new DbAdapter<EventInviteUserRequest>("orgs", "invites", pool, EventInviteUserRequest.class, factory)
                .ensureIndex("entity.id")
                .ensureIndex("email");

        this.user_account_types = new DbAdapter<>("orgs", "user_account_types", pool,
                UserAccountType.class, factory).ensureIndex("org_id");

        this.user_accounts = new DbAdapter<>("orgs", "user_accounts", pool,
                UserAccount.class, factory).ensureIndex("subject_id");

        this.project_users = new DbAdapter<>("console", "project_metadata_users", pool, ProjectUsers.class, factory);

        this.user_pairwise_ids = new DbAdapter<>("identity", "ppids", pool, UserPairwiseIds.class, factory);

        this.projects = new DbAdapter<Project>("console", "projects", pool, Project.class, factory)
                .ensureIndex("org_id");
        this.api_catalog = new DbAdapter<ApiServiceSpecification>("console", "service_catalog", pool, ApiServiceSpecification.class, factory);
        this.accounts = new DbAdapter<>("console", "accounts", pool, Account.class, factory);
        this.experiments = new DbAdapter<>("console", "experiments", pool, Experiment.class, factory);
        this.experiment_membership = new DbAdapter<>("console", "experiments_membership", pool, ExperimentMembership.class, factory)
                .ensureIndex("subject_id")
                .ensureIndex("experiment_id");

        this.project_storage_buckets = new DbAdapter<>("console", "project_storage_buckets", pool,
                ProjectStorageBucket.class, factory)
                .ensureIndex("project_id");

        this.cert_keys = new DbAdapter<>("console", "cert_keys", pool,
                JsonNode.class, factory)
                .ensureIndex("project_id");
    }

    public DbAdapter<UserPairwiseIds> user_pairwise_ids() {
        return user_pairwise_ids;
    }

    public DbAdapter<Org> organizations() {
        return orgs;
    }

    public DbAdapter<OrgRoles> organization_roles() {
        return org_roles;
    }

    public DbAdapter<EventInviteUserRequest> invites() {
        return invitesTable;
    }

    public DbAdapter<Project> projects() {
        return projects;
    }

    public DbAdapter<ProjectUsers> project_users() {
        return project_users;
    }

    public DbAdapter<Agreement> agreements() {
        return agreements;
    }

    public DbAdapter<OrgAssignment> org_assignments() {
        return org_assignments;
    }

    public DbAdapter<ApiServiceSpecification> api_catalog() {
        return api_catalog;
    }

    public DbAdapter<Experiment> experiments() {
        return experiments;
    }

    public DbAdapter<ExperimentMembership> experiment_membership() {
        return experiment_membership;
    }

    public DbAdapter<Account> accounts() {
        return accounts;
    }

    public DbAdapter<ProjectStorageBucket> project_storage_buckets() {
        return project_storage_buckets;
    }

    public DbAdapter<UserAccountType> user_account_types() {
        return user_account_types;
    }

    public DbAdapter<UserAccount> user_accounts() {
        return user_accounts;
    }

    public DbAdapter<JsonNode> cert_keys() {
        return cert_keys;
    }
}
