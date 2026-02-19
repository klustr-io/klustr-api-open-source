package io.klustr.consent.prompts;

import com.google.common.collect.Lists;
import io.klustr.consent.ConsentStorage;
import io.klustr.consent.prompts.api.ConsentPromptClientMetadata;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.api.ConsentPromptResponse;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.AppConsentPrompt;
import io.klustr.consent.prompts.impl.consent.ConsentPrompt;
import io.klustr.consent.prompts.impl.consent.DelegatedConsentPrompt;
import io.klustr.exceptions.StandardResponseException;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLinks;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.apps.AppVerification;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.schemas.console.consent.ConsentDelegationRequest;
import io.klustr.schemas.console.experiments.Experiment;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.schemas.console.EntityReference;
import io.klustr.console.storage.Storage;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Will audit and track the consent grants given by users and clients as part of OIDC flows.
 */
@RestController
@Component
@RequestMapping("/consent/admin/{userId}/prompts")
@Tag(name = "Consent Prompt APIs", description = "Provides access to consent and privacy operations.")
public class PromptService {

    private final Storage storage;
    private final PromptFactory prompts;
    private final UserConsentRepository consent;
    private final KratosApi kratos;
    private final PersonStorage persons;
    private final ConsentStorage consentStorage;

    public PromptService(Storage storage,
                         PersonStorage persons,
                         ConsentStorage consentStorage,
                         UserConsentRepository consent,
                         PromptFactory prompts,
                         KratosApi kratos) {
        this.storage = storage;
        this.prompts = prompts;
        this.consent = consent;
        this.kratos = kratos;
        this.persons = persons;
        this.consentStorage = consentStorage;
    }

    @PostMapping
    @Operation(
operationId = "adminRetrieveUserConsentPrompts",
            summary = "Retrieve consent prompts for an admin user.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The consent prompt information to collect from the user.")
            },
            description = """
This endpoint allows administrators to access the consent prompts specific to a user. It provides insights into the user's consent history and the prompts they need to address. The response may include multiple prompts based on business rules and user interactions.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    public ConsentPromptResponse prompt(@RequestBody ConsentPromptRequest request,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (StringUtils.isBlank(request.client_id)) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "Client ID not provided.", "invalid_client"
            );
        }
        if (StringUtils.isBlank(request.subject_id)) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "User ID not provided.", "invalid_request"
            );
        }

        ConsentPromptResponse res = new ConsentPromptResponse();
        List<AbstractPrompt> prompts = new ArrayList<>();

        // an actor is acting on behalf of the specified subject
        // https://datatracker.ietf.org/doc/html/rfc8693#section-4.1
        // this is for when a delegated account is working through the
        // system.
        if (request.delegate != null && StringUtils.isNotBlank(request.delegate.subject_id) && StringUtils.isNotBlank(request.delegate.client_id)) {

            // todo, can this client actually issue a client impersonation
            // this is a protected action and only trusted clients should
            // be able to do this with a specific permission

            // find the household member that is being targeted
            DbQuery fq = Db.query("members").contains("person_id").eq(request.subject_id);
            Optional<Household> household = this.persons.households().findFirst(fq);
            if (household.isEmpty()) {
                throw new StandardResponseException(
                        HttpStatus.BAD_REQUEST, "Delegated supervised account not enabled, person not in household.", "invalid_request"
                );
            }

            Optional<HouseholdMember> supervisor = household.get()
                    .getMembers().stream()
                    .filter(x -> x.getPersonId().equalsIgnoreCase(request.delegate.subject_id) && x.getPrimary())
                    .findFirst();

            if (supervisor.isEmpty()) {
                throw new StandardResponseException(
                        HttpStatus.BAD_REQUEST, "Requesting party is not the supervisor of the household and can not be delegated consent rights.", "invalid_request"
                );
            }

            // any client or request for this combination in process
            fq = Db.and(
                    Db.query("household_id").eq(household.get().getId()),
                    Db.query("client_id").eq(request.delegate.client_id),
                    Db.query("subject_id").eq(request.subject_id)
            );
            Optional<ConsentDelegationRequest> delegation = this.persons.consent_delegation().findFirst(fq);
            if (delegation.isPresent()) {
                // check if the current application has a delegate consent
                DelegatedConsentPrompt delegated = new DelegatedConsentPrompt();
                delegated.supervisor = this.persons.persons().getObject(supervisor.get().getPersonId()).getTraits();
                res.supervisor = this.kratos.getPublicIdentity(supervisor.get().getPersonId());
                prompts.add(0, delegated);

                // reset the client
                request.client_id = request.delegate.client_id;
                request.experiment_id = null;
                request.agreements = Lists.newArrayList();
            }
        }

        // get organization information given client_id
        DbQuery fq = Db.query("clients").contains("client_id").eq(request.client_id);
        Optional<Project> project = this.storage.projects().findFirst(fq);
        if (project.isEmpty()) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "Client '" + request.client_id + "' does not have a linked project", "invalid_client"
            ).withMeta("sub", request.subject_id);
        }
        if (project.get().getStatus() == Project.Status.INACTIVE) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "The project '" + project.get().getId() + "' is inactive for client '" + request.client_id + "'", "invalid_client"
            ).withMeta("sub", request.subject_id);
        }

        Org org = this.storage.organizations().getObject(project.get().getOrgId());
        if (org == null) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "The organization '" + project.get().getOrgId() + "' is not found.", "invalid_client"
            ).withMeta("sub", request.subject_id);
        }
        if (org.getStatus() == Org.Status.INACTIVE) {
            throw new StandardResponseException(
                    HttpStatus.BAD_REQUEST, "The organization '" + project.get().getOrgId() + "' has been disabled.", "invalid_client"
            ).withMeta("sub", request.subject_id);
        }

        // skip or go
        prompts.addAll(this.prompts.create(request));

        res.prompts = prompts.stream().filter(x -> !x.skip).toList();
        res.organization  = new EntityReference().withId(org.getId()).withName(org.getName());
        res.existing_scopes = this.consent.getExistingScopes(request.subject_id, project.get().getId(), request.client_id, org.getId());

        // fill in all existing scopes from all prompts
        // so that we can apply them.
        res.prompts.forEach(p -> {
            if (p instanceof ConsentPrompt) {
                ((ConsentPrompt) p).addExistingScopes(res);
            }
        });

        Person person = this.persons.persons().getObject(request.subject_id);

        // no need to add filter this is the supervisor
        if (res.supervisor == null || res.supervisor.getId() == null) {
            res.prompts = new SupervisedAccountFilter(this.persons).filter(person, res.prompts);
            res.supervisor = null;
        }
        res.identity = this.kratos.getPublicIdentity(request.subject_id);

        // all experiments the user is currently in
        res.existing_experiments = this.consent.getExperimentConsentByProject(request.subject_id, project.get().getId()).stream().map(x -> {
            return this.storage.experiments().getObject(x.getExperimentId());
        }).filter(x -> {
            if (x == null) return false;
            if (x.getStatus() == Experiment.Status.PUBLISHED) {
                if (x.getStartDate() != null && x.getStartDate().isBeforeNow()) {
                    if (x.getStopDate() != null && x.getStopDate().isAfterNow()) {
                        return true;
                    }
                }
            }
            return false;
        }).map(Experiment::getId).collect(Collectors.toSet());

        // patch as client could be different given impersonation
        res.client = new ConsentPromptClientMetadata();
        App app = project.get().getApp();
        AppBrand brand = app.getBrand();
        res.client.client_name = brand.getName();
        res.client.logo_uri = brand.getLogo() != null && brand.getLogo().getUrl() != null ? brand.getLogo().getUrl().toString() : null;
        AppLinks links = app.getLinks();
        if (links != null) {
            res.client.client_uri = links.getHome() != null ? links.getHome().toString() : null;
            res.client.tos_uri = links.getTos() != null ? links.getTos().toString() : null;
            res.client.policy_uri = links.getPrivacy() != null ? links.getPrivacy().toString() : null;
        }
        res.client.metadata = new HashMap<>();
        if (app.getVerification() != null && app.getVerification().getApproval() != null) {
            res.client.metadata.put("verified", app.getVerification().getApproval().value());
        }
        if (app.getTrust() != null && app.getTrust().getApproval() != null && app.getTrust().getApproval() == AppVerification.Approval.VERIFIED) {
            res.client.metadata.put("trusted", app.getTrust().getApproval().value());
            // verified so we can skip all consent scopes
            Optional<AbstractPrompt> appConsent = res.prompts.stream().filter(x -> x instanceof AppConsentPrompt).findFirst();
            if (appConsent.isPresent()) {
                appConsent.get().skip = true;
                AppConsentPrompt appConsentPrompt = (AppConsentPrompt)appConsent.get();
                res.existing_scopes.addAll(appConsentPrompt.existing_scopes != null ? appConsentPrompt.existing_scopes.stream().map(ConsentAttribute::getId).toList() : Lists.newArrayList());
                res.existing_scopes.addAll(appConsentPrompt.requested_scopes != null ? appConsentPrompt.requested_scopes.stream().map(ConsentAttribute::getId).toList() : Lists.newArrayList());
                res.prompts = res.prompts.stream().filter(x -> !(x instanceof AppConsentPrompt)).collect(Collectors.toList());
            }
        }

        return res;
    }

}
