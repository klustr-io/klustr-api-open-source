package io.klustr.consent.prompts.impl.onboarding.services;

import com.google.common.collect.Lists;
import io.klustr.consent.prompts.PromptResponse;
import io.klustr.consent.prompts.impl.onboarding.*;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.integrations.KratosIdentityMap;
import io.klustr.schemas.console.Gender;
import io.klustr.schemas.console.identity.*;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.integrations.ory.IdentityRecoveryAddress;
import io.klustr.schemas.persons.ExternalReference;
import io.klustr.schemas.persons.Name;
import io.klustr.schemas.persons.Person;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/consent/admin/{userId}/prompts")
@Tag(name = "Consent Prompt APIs", description = "Provides access to consent and privacy operations.")
public class OnboardingPromptService {

    private PersonStorage persons;
    private KratosApi kratos;

    public OnboardingPromptService(PersonStorage persons, KratosApi kratos) {
        this.persons = persons;
        this.kratos = kratos;
    }

    public static class OnboardingPromptResponse extends PromptResponse<OnboardingPrompt> { }

    @PostMapping(OnboardingPrompt.BASE_PATH)
    @Operation(
operationId = "adminSubmitOnboardingResponse",
            summary = "Admin submits onboarding response for user prompts.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Handles the logic for when onboarding prompts are delivered.")
            },
            description = """
This endpoint allows administrators to process onboarding responses related to user prompts. It ensures that the necessary onboarding information is handled efficiently. Use this API to manage consent and privacy operations effectively within the admin context.
""",
            security = {
                    @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
            }
)
    @PreAuthorize("hasAuthority('user.consent.update')")
    public void onboarding(@RequestBody OnboardingPromptResponse response,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // basic information for name
        if (response.prompt instanceof OnboardingBasicInfoPrompt) {
            OnboardingBasicInfoPrompt data = (OnboardingBasicInfoPrompt) response.prompt;
            Person person = this.persons.persons().getObject(response.subject_id);

            if (person == null) {
                // person is enrolling now, we should get the identity
                // from identity server if it exists
                Identity id = this.kratos.getIdentity(response.subject_id);
                person = new KratosIdentityMap().fromIdentity(id);
                this.persons.persons().insertObject(person.getId(), person);
            }

            if (person.getTraits() == null) {
                person.setTraits(new IdentityTraits());
            }
            person.getTraits().setName(new IdentityName()
                    .withGivenName(data.given_name)
                    .withFamilyName(data.family_name)
            );
            // see if the person has a sourced name from themselves
            Optional<Name> match = person.getName().stream().filter(x -> x.getSource() == Name.Source.USER).findFirst();
            if (match.isEmpty()) {
                person.getName().add(new Name()
                        .withFamilyName(data.family_name)
                        .withGivenName(data.given_name)
                        .withSource(Name.Source.USER)
                );
            } else {
                match.get().withFamilyName(data.family_name).withGivenName(data.given_name);
            }
            this.persons.persons().updateObject(person.getId(), person);

            Identity identity = this.kratos.getIdentity(person.getId());
            identity.setTraits(person.getTraits());
            this.kratos.update(identity);
        }

        // date of birth and other options
        if (response.prompt instanceof OnboardingBasicInfoPrompt) {

            OnboardingBasicInfoPrompt data = (OnboardingBasicInfoPrompt) response.prompt;
            Person person = this.persons.persons().getObject(response.subject_id);
            org.joda.time.LocalDate localDate = new LocalDate(data.year, data.month, data.day);
            person.getTraits().setBirthdate(localDate);
            person.getTraits().setGender(data.gender != null && data.gender.equalsIgnoreCase("male") ? Gender.male : Gender.female);
            this.persons.persons().updateObject(person.getId(), person);

            Identity identity = this.kratos.getIdentity(person.getId());
            identity.setTraits(person.getTraits());
            this.kratos.update(identity);
        }

        if (response.prompt instanceof OnboardingEmailPrompt) {
            OnboardingEmailPrompt data = (OnboardingEmailPrompt) response.prompt;
            Person person = this.persons.persons().getObject(response.subject_id);
            IdentityTraits traits = person.getTraits();
            if (traits == null) {
                traits = new IdentityTraits()
                                .withEmail(data.email);
                person.setTraits(traits);
                this.persons.persons().updateObject(person.getId(), person);
            } else {
                traits.setEmail(data.email);
                this.persons.persons().updateObject(person.getId(), person);
            }
        }

        if (response.prompt instanceof OnboardingUserIdPrompt) {
            // TODO need to verify user id doesn't already exist
            OnboardingUserIdPrompt data = (OnboardingUserIdPrompt) response.prompt;
            Person person = this.persons.persons().getObject(response.subject_id);
            IdentityCredentials cred = person.getCredentials();
            cred = cred == null ? new IdentityCredentials() : cred;

            IdentityPasswordCredential password = cred.getPassword();
            password = password == null ? new IdentityPasswordCredential() : password;
            cred.setPassword(password);

            if (person.getTraits() == null) {
                person.setTraits(new IdentityTraits());
            }
            if (person.getTraits().getUsername() == null || !person.getTraits().getUsername().equalsIgnoreCase(data.user_id)) {
                person.getTraits().setUsername(data.user_id);
            }

            person.setCredentials(cred);
            this.persons.persons().updateObject(person.getId(), person);
        }

        if (response.prompt instanceof OnboardingPasswordPrompt) {

            OnboardingPasswordPrompt data = (OnboardingPasswordPrompt) response.prompt;
            Person person = this.persons.persons().getObject(response.subject_id);
            IdentityCredentials cred = person.getCredentials();
            cred = cred == null ? new IdentityCredentials() : cred;
            IdentityPasswordCredential pwd = cred.getPassword();

            IdentityPasswordCredentialConfig md5Password = new IdentityPasswordCredentialConfig()
                    .withPassword(U.md5(data.password));
            if (pwd == null) {
                pwd = new IdentityPasswordCredential()
                        .withConfig(md5Password);
            } else {
                pwd.withConfig(md5Password);
            }
            this.persons.persons().updateObject(person.getId(), person);

            // now set actual password
            this.kratos.setCredentials(person.getId(), person.getTraits().getUsername(), data.password);
        }
    }
}
