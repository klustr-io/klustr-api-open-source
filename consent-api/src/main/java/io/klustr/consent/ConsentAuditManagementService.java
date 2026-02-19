package io.klustr.consent;

import com.google.common.collect.Sets;
import io.klustr.consent.prompts.ScopeRequest;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.consent.ConsentAudit;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.projects.Project;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

/**
 * Will audit and track the consent grants given by users and clients as part of OIDC flows.
 */
@RestController
@Component
@RequestMapping("/consent/admin")
@Tag(name = "Consent Admin APIs", description = "Provides access to consent and privacy operations.")
public class ConsentAuditManagementService {

    private final ConsentStorage consent_storage;

    private final Storage storage;

    private final UserConsentRepository userConsent;

    public ConsentAuditManagementService(
            ConsentStorage consent_storage,
            Storage storage,
            UserConsentRepository userConsent) {
        this.consent_storage = consent_storage;
        this.storage = storage;
        this.userConsent = userConsent;
    }

    public static class ConsentResponse {
        public Set<String> scopes = Sets.newHashSet();
        public Set<String> experiments = Sets.newHashSet();
    }

    @PostMapping("/audit")
    @Operation(
operationId = "adminAuditUserConsent",
            summary = "Admin audit of user consent grants",
            description = """
This endpoint enables administrators to audit user consent grants effectively. It consolidates consent statuses and related agreements, ensuring compliance and providing insights into user preferences. This functionality is crucial for maintaining oversight and understanding consent dynamics in real-time.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public void audit(@RequestBody ConsentAudit audit) {
        if (StringUtils.isBlank(audit.getId())) {
            audit.setId(UUID.randomUUID().toString());
        }

        DbQuery fq = Db.query("clients").contains("client_id").eq(audit.getClientId());
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client ID '" + audit.getClientId() + "' is not mapped to a project");
        }

        audit.setProjectId(project.get().getId());
        audit.setOrgId(project.get().getOrgId());
        audit.setAppId(project.get().getApp().getId());
        audit.setTimestamp(new DateTime());
        this.consent_storage.consent_audit().insertObject(audit.getId(), audit);
    }

    @PostMapping("/scopes")
    @Operation(
operationId = "adminGetActiveConsentScopes",
            summary = "Retrieve active consent scopes for users by admin.",
            description = """
This endpoint enables administrators to retrieve the currently active consent scopes granted by users. It provides a detailed overview of user permissions, which is crucial for auditing and compliance. This functionality ensures that admins have the necessary visibility into user consent for effective management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('user.consent.get')")
    public ConsentResponse getActiveScopes(@RequestBody ScopeRequest request) {
        // find any prior scopes assigned to the user and update them
        ConsentResponse res = new ConsentResponse();
        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);
        Optional<Project> match = this.storage.projects().findFirst(fq);

        if (match.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client ID is not mapped to a project");
        }

        Project project = match.get();
        Set<String> existingScopes = this.userConsent.getExistingScopes(request.subject_id,
                project.getId(),
                request.client_id,
                project.getOrgId());
        res.scopes.addAll(existingScopes);
        // always include the openid and profile options
        res.scopes.add("openid");

        // only get live experiments that are available for the user
        List<UserExperimentConsent> projectExperiments = this.userConsent.getExperimentConsentByProject(request.subject_id, project.getId());
        List<UserExperimentConsent> list = projectExperiments.stream().filter(x -> {
            return x.getStatus() == UserExperimentConsent.Status.ACTIVE;
        }).filter(x -> {
            Experiment object = this.storage.experiments().getObject(x.getExperimentId());
            return object != null
                    && object.getStopDate().isAfterNow()
                    && object.getStartDate().isBeforeNow();
        }).filter(x -> {
            Experiment object = this.storage.experiments().getObject(x.getExperimentId());
            return object.getStatus() == Experiment.Status.PUBLISHED ||
                    object.getStatus() == Experiment.Status.RUNNING;
        }).toList();

        list.forEach(x -> {
            res.experiments.add(x.getExperimentId());
        });

        return res;
    }
}
