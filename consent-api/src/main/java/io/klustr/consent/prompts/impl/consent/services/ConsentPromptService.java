package io.klustr.consent.prompts.impl.consent.services;

import com.google.common.collect.Lists;
import io.klustr.consent.ConsentScopeMergeStrategy;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.impl.consent.AgreementConsentPrompt;
import io.klustr.consent.prompts.impl.consent.AppConsentPrompt;
import io.klustr.consent.prompts.impl.consent.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.consent.ExperimentConsentPrompt;
import io.klustr.consent.prompts.impl.consent.SupervisedAccountConsentPrompt;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.console.storage.Storage;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementReference;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.apps.ConsentGroup;
import io.klustr.schemas.console.consent.ConsentDelegationRequest;
import io.klustr.schemas.console.consent.UserConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import io.klustr.schemas.console.consent.types.UserExperimentConsent;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.experiments.ExperimentMembership;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.klustr.storage.query.DbQueryAndCondition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/consent/admin/{userId}/prompts")
@Tag(name = "Consent Prompt APIs", description = "Provides access to consent and privacy operations.")
public class ConsentPromptService {

    private PersonStorage persons;
    private Storage storage;
    private ConsentStorage consent;
    private UserConsentRepository repo;

    public ConsentPromptService(Storage storage, ConsentStorage consent, UserConsentRepository repo, PersonStorage persons) {
        this.persons = persons;
        this.storage = storage;
        this.consent = consent;
        this.repo = repo;
    }



    public static class SupervisedAccountPromptResponse extends ConsentPromptResponse<SupervisedAccountConsentPrompt> {
        /**
         * The challenge associated with this supervised account.
         */
        public String challenge_id;
    }
    @PostMapping(SupervisedAccountConsentPrompt.BASE_PATH)
    @Operation(
operationId = "adminSubmitSupervisedAccountPrompt",
            summary = "Admin submits supervised account prompt for approval",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Will request to the supervised account owner of this account to approve the login or access. This is for age restricted accounts.")
            },
            description = """
This operation allows an admin to submit a request for a supervised account to seek approval from the account owner. It is specifically designed for age-restricted accounts, ensuring that parental consent is obtained before granting access. This enhances the safety and compliance of child accounts in the system.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('user.consent.update')")
    public void supervised(@RequestBody SupervisedAccountPromptResponse response,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // lookup household and supervisor
        // trigger workflow for supervisor approval (camunda)
        // send email, or timeout the request

        DbQuery fq = Db.query("clients").contains("client_id").eq(response.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No project found."
            );
        }

        fq = Db.query("members").contains("person_id").eq(response.subject_id);
        Optional<Household> household = this.persons.households().findFirst(fq);
        if (household.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "No household found for delegation."
            );
        }

        ConsentDelegationRequest req = new ConsentDelegationRequest()
                    .withAppId(project.get().getApp().getId())
                    .withChallengeId(response.challenge_id)
                    .withId(UUID.randomUUID().toString())
                    .withOrgId(project.get().getOrgId())
                    .withClientId(response.client_id)
                    .withStatus(ConsentDelegationRequest.Status.PENDING)
                    .withCreationDate(DateTime.now())
                    .withProjectId(project.get().getId())
                    .withSubjectId(response.subject_id)
                    .withScopes(response.scopes)
                    .withHouseholdId(household.get().getId());

        // is there already an existing request?
        fq = Db.and(
                Db.query("household_id").eq(household.get().getId()),
                Db.query("app_id").eq(project.get().getApp().getId()),
                Db.query("subject_id").eq(response.subject_id)
        );
        Optional<ConsentDelegationRequest> existing_request = this.persons.consent_delegation().findFirst(fq);
        if (existing_request.isEmpty()) {
            this.persons.consent_delegation().insertObject(req.getId(), req);
        } else {
            req.setId(existing_request.get().getId());
            req.setCreationDate(DateTime.now());
            req.setStatus(ConsentDelegationRequest.Status.PENDING);
            req.setApprovedDate(null);
            this.persons.consent_delegation().updateObject(existing_request.get().getId(), req);
        }
    }

    public static class AgreementConsentPromptResponse extends ConsentPromptResponse<AgreementConsentPrompt> {
    }
    @PostMapping(AgreementConsentPrompt.BASE_PATH)
    @Operation(
operationId = "adminSubmitAgreementPrompt",
            summary = "Admin submits onboarding agreement prompt for user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Handles the logic for when onboarding agreements are delivered.")
            },
            description = """
This endpoint allows administrators to submit onboarding agreement prompts for users. It handles the necessary logic for delivering agreements and ensures compliance with privacy and consent regulations. Use this API to manage user consent effectively and maintain clear communication regarding onboarding processes.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('user.consent.update')")
    public void agreements(@RequestBody AgreementConsentPromptResponse response,
                           @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (response.prompt == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the original prompt."
            );
        }

        if (StringUtils.isBlank(response.subject_id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the subject id."
            );
        }

        if (response.prompt == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the original prompt."
            );
        }

        AgreementReference agreement = response.prompt.agreement;

        // get the agreement reference
        Agreement doc = this.storage.agreements().getObject(agreement.getId());

        // subject id and agreement id
        DbQueryAndCondition filter = Db.and(
                Db.query("subject_id").eq(response.subject_id),
                Db.query("agreement_id").eq(agreement.getId())
        );

        Optional<UserAgreementConsent> audit = this.consent.user_agreement_consent().findFirst(filter);

        // TODO get the major version/latest version?
        AgreementVersion version = doc.getVersions().stream().reduce((x, y) -> y).get();

        List<UserConsentScope> agreementScopes = Lists.newArrayList();

        if (version.getScopes() != null) {
            agreementScopes = version.getScopes().stream().map(x -> {
                return new UserConsentScope()
                        .withStatus(UserConsentScope.Status.ACTIVE)
                        .withId(x.getId())
                        .withConsentMethod(UserConsentScope.ConsentMethod.EXPLICIT)
                        .withDateModified(DateTime.now())
                        .withDateCreated(DateTime.now());
            }).toList();
        }

        if (audit.isEmpty()) {
            UserAgreementConsent userAgreement = new UserAgreementConsent();

            userAgreement
                    .withAgreementId(agreement.getId())
                    .withFirstSeenDate(DateTime.now())
                    .withLastSeenDate(DateTime.now())
                    .withStatus(UserAgreementConsent.Status.ACTIVE)
                    .withOrgId(doc.getOrgId())
                    .withVersionNumber(version.getVersionNumber())
                    .withScopes(agreementScopes)
                    .withSubjectId(response.subject_id)
                    .withId(UUID.randomUUID().toString());

            this.consent.user_agreement_consent().insertObject(userAgreement.getId(), userAgreement);
        } else {
            audit.get().withVersionNumber(version.getVersionNumber())
                    .withLastSeenDate(DateTime.now())
                    .withScopes(ConsentScopeMergeStrategy.merge(agreementScopes, audit.get().getScopes()));
            this.consent.user_agreement_consent().updateObject(audit.get().getId(), audit.get());
        }
    }

    public static class AppConsentPromptResponse extends ConsentPromptResponse<AppConsentPrompt> {
    }

    @PostMapping(AppConsentPrompt.BASE_PATH)
    @Operation(
operationId = "adminSubmitAppPrompt",
            summary = "Admin submits application consent prompt.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Handles the logic for when consenting to an applications consent.")
            },
            description = """
This endpoint allows an admin to submit a consent prompt for an application. It processes the necessary onboarding information related to user consent. Ensure that the appropriate permissions are in place to access this functionality.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('user.consent.update')")
    public void apps(@RequestBody AppConsentPromptResponse response,
                     @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {


        Optional<UserApplicationConsent> match = repo.getUserConsentByClientId(response.subject_id, response.client_id);

        DbQuery fq = Db.query("clients").contains("client_id").eq(response.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Project not found for client."
            );
        }

        List<UserConsentScope> applicationScopes = new ArrayList<>(response.scopes.stream().map(x -> {
            return new UserConsentScope()
                    .withId(x)
                    .withDateModified(DateTime.now())
                    .withDateCreated(DateTime.now())
                    .withConsentMethod(UserConsentScope.ConsentMethod.EXPLICIT)
                    .withStatus(UserConsentScope.Status.ACTIVE);
        }).toList());

        // remove any scopes not defined at application level
        applicationScopes.removeIf(scope -> {
            ConsentGroup consent = project.get().getApp().getConsent();
            if (consent == null) {
                consent = new ConsentGroup();
            }
            return consent.getScopes().stream().filter(x -> x.getId().equalsIgnoreCase(scope.getId())).findAny().isEmpty();
        });

        // match already there, lets update it
        DateTime now = new DateTime();
        UserApplicationConsent record = match.orElseGet(() -> {
            return new UserApplicationConsent()
                    .withClientId(response.client_id)
                    .withTimestamp(now)
                    .withAppId(project.get().getApp().getId())
                    .withSubjectId(response.subject_id)
                    .withScopes(applicationScopes)
                    .withOrgId(project.get().getOrgId())
                    .withAgent(response.agent)
                    .withIp(response.ip)
                    .withSessionId(response.session_id)
                    .withFirstSeenDate(now)
                    .withLastSeenDate(now)
                    .withImpressions(0)
                    .withProjectId(project.get().getId());
        });

        // latest approved scopes merged
        record.setScopes(ConsentScopeMergeStrategy.merge(applicationScopes, record.getScopes()));
        // ensure timestamp
        record.setTimestamp(now);
        record.withLastSeenDate(now);
        record.withImpressions(record.getImpressions() != null ? record.getImpressions() + 1 : 1);

        // new request
        if (match.isEmpty()) {
            this.repo.insertApplicationConsent(record);
        } else {
            this.repo.updateAppConsent(record);
        }

        // delegate?
        if (StringUtils.isNotBlank(response.act) && !response.act.equalsIgnoreCase(record.getSubjectId())) {
            fq = Db.query("members").contains("person_id").eq(response.subject_id);
            Optional<Household> household = this.persons.households().findFirst(fq);
            if (household.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Delegated consent, but assigned user not in household."
                );
            }
            Optional<HouseholdMember> matchPrimary = household.get().getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(response.act) &&
                    x.getPrimary()
            ).findFirst();
            if (matchPrimary.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Delegated consent, but actor is not a primary household owner."
                );
            }
            fq = Db.and(
                    Db.query("household_id").eq(household.get().getId()),
                    Db.query("client_id").eq(response.client_id),
                    Db.query("subject_id").eq(response.subject_id)
            );
            Optional<ConsentDelegationRequest> delegationRequest = this.persons.consent_delegation().findFirst(fq);
            if (delegationRequest.isPresent()) {
                // update the request
                delegationRequest.get()
                        .withStatus(ConsentDelegationRequest.Status.COMPLETED)
                        .withApprovedDate(DateTime.now())
                        .withAct(response.act);
                this.persons.consent_delegation().updateObject(delegationRequest.get().getId(), delegationRequest.get());
            }
        }
    }

    public static class ExperimentConsentPromptResponse extends ConsentPromptResponse<ExperimentConsentPrompt> {
    }

    @PostMapping(ExperimentConsentPrompt.BASE_PATH)
    @Operation(
operationId = "adminSubmitExperimentPrompt",
            summary = "Submit consent for experiment prompts as an admin.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Handles the logic for when consenting to experiments.")
            },
            description = """
This endpoint processes consent for experiment prompts specifically for admin users. It ensures that the necessary privacy and consent protocols are followed when handling user experiment data. Use this API to manage experiment-related consent effectively.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('user.consent.update')")
    public void experiments(@RequestBody ExperimentConsentPromptResponse response,
                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        DateTime timestamp = new DateTime();

        if (response.prompt == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the original prompt."
            );
        }

        if (StringUtils.isBlank(response.subject_id)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the subject id."
            );
        }

        if (response.prompt == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Must provide the original prompt."
            );
        }

        DbQuery fq = Db.query("clients").contains("client_id").eq(response.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid client provided."
            );
        }

        Experiment exp = response.prompt.experiment;
        List<UserConsentScope> experimentScopes = exp.getScopes().stream().map(scope -> {
            return new UserConsentScope()
                    .withDateCreated(DateTime.now())
                    .withDateModified(DateTime.now())
                    .withId(scope.getId())
                    .withConsentMethod(UserConsentScope.ConsentMethod.EXPLICIT)
                    .withStatus(UserConsentScope.Status.ACTIVE);
        }).toList();
        Optional<UserExperimentConsent> experiment = this.repo.getExperimentConsent(response.subject_id, exp.getId());
        if (experiment.isEmpty()) {

            UserExperimentConsent value = new UserExperimentConsent()
                    .withExperimentId(exp.getId())
                    .withOrgId(project.get().getOrgId())
                    .withProjectId(project.get().getId())
                    .withAppId(project.get().getApp().getId())
                    .withClientId(response.client_id)
                    .withStatus(UserExperimentConsent.Status.ACTIVE)
                    .withLastSeenDate(timestamp)
                    .withFirstSeenDate(timestamp);

            value.withScopes(experimentScopes);
            value.setSubjectId(response.subject_id);

            this.repo.insertExperimentConsent(value);
        } else {
            experiment.get()
                    .withExperimentId(experiment.get().getExperimentId())
                    .withOrgId(experiment.get().getOrgId())
                    .withProjectId(experiment.get().getProjectId())
                    .withAppId(experiment.get().getAppId())
                    .withClientId(response.client_id)
                    .withStatus(UserExperimentConsent.Status.ACTIVE)
                    .withLastSeenDate(timestamp)
                    .withSubjectId(response.subject_id)
                    .withScopes(ConsentScopeMergeStrategy.merge(experimentScopes, experiment.get().getScopes()));

            this.repo.updateExperimentConsent(experiment.get());

            // record membership with the experiment
            String docId = UUID.randomUUID().toString();
            this.storage.experiment_membership().insertObject(docId,
                    new ExperimentMembership()
                            .withExperimentId(experiment.get().getExperimentId())
                            .withOrgId(experiment.get().getAppId())
                            .withProjectId(experiment.get().getProjectId())
                            .withId(docId)
                            .withSubjectId(response.subject_id)
                            .withStatus(ExperimentMembership.Status.OPT_IN)
                            .withTimestamp(timestamp)
            );
        }
    }
}
