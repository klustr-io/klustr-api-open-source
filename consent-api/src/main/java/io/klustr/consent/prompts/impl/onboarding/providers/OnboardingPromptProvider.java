package io.klustr.consent.prompts.impl.onboarding.providers;

import com.google.common.collect.Lists;
import io.klustr.consent.prompts.PromptProvider;
import io.klustr.consent.prompts.api.ConsentPromptRequest;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.onboarding.*;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.integrations.KratosIdentityMap;
import io.klustr.schemas.console.identity.IdentityName;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.persons.Person;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OnboardingPromptProvider implements PromptProvider {

    private final PersonStorage storage;
    private final KratosApi kratos;


    public OnboardingPromptProvider(PersonStorage storage,
                                    KratosApi kratos) {
        this.storage = storage;
        this.kratos = kratos;
    }

    private List<AbstractPrompt> newUser() {
        List<AbstractPrompt> prompts = Lists.newArrayList();
        prompts.add(new OnboardingBasicInfoPrompt());
        prompts.add(new OnboardingUserIdPrompt());
        prompts.add(new OnboardingPasswordPrompt());
        prompts.add(new OnboardingEmailPrompt());
        return prompts;
    }

    @Override
    public List<AbstractPrompt> create(ConsentPromptRequest request) {
        List<AbstractPrompt> prompts = Lists.newArrayList();
        Person person = this.storage.persons().getObject(request.subject_id);

        if (person == null) {
            Identity identity = this.kratos.getIdentity(request.subject_id);
            Person p = new KratosIdentityMap().fromIdentity(identity);
            this.storage.persons().insertObject(p.getId(), p);
            person = p;
        }

        if (person.getTraits() == null || person.getTraits().getEmail() == null || StringUtils.isBlank(person.getTraits().getEmail())) {
            // identity may have our email if so apply it
            Identity identity = this.kratos.getIdentity(person.getId());
            if (identity != null && identity.getTraits() != null && identity.getTraits().getEmail() != null) {
                if (person.getTraits() == null) {
                    person.setTraits(new IdentityTraits());
                }
                if (StringUtils.isBlank(person.getTraits().getEmail())) {
                    person.getTraits().setEmail(identity.getTraits().getEmail());
                    this.storage.persons(). updateObject(person.getId(), person);
                }
            } else {
                // identity and person has no email
                prompts.add(new OnboardingEmailPrompt());
            }
        } else {
            Identity identity = this.kratos.getIdentity(person.getId());
            // if person already has email but identity doesn't lets update it
            if (identity != null && identity.getTraits() != null) {
                if (StringUtils.isBlank(identity.getTraits().getEmail())) {
                    identity.getTraits().setEmail(person.getTraits().getEmail());
                    this.kratos.update(identity);
                }
            }
        }

        OnboardingBasicInfoPrompt basicInfoPrompt = new OnboardingBasicInfoPrompt();


        IdentityName name = person.getTraits().getName();
        if (name == null ||  StringUtils.isBlank(name.getGivenName())) {
            basicInfoPrompt.family_name = name != null ? name.getFamilyName() : null;
            basicInfoPrompt.given_name = name != null ? name.getGivenName() : null;
            prompts.add(basicInfoPrompt);
        } else {
            basicInfoPrompt.family_name = name.getFamilyName();
            basicInfoPrompt.given_name = name.getGivenName();
        }

        LocalDate dob = person.getTraits().getBirthdate();
        if (dob == null) {
            if (!prompts.contains(basicInfoPrompt)) {
                prompts.add(basicInfoPrompt);
            }
        } else {
            basicInfoPrompt.day = dob.getDayOfMonth();
            basicInfoPrompt.year = dob.getYear();
            basicInfoPrompt.month = dob.getMonthOfYear();
        }
        if (person.getTraits().getGender() == null) {
            if (!prompts.contains(basicInfoPrompt)) {
                prompts.add(basicInfoPrompt);
            }
        } else {
            basicInfoPrompt.gender = person.getTraits().getGender().toString();
        }


        if (person.getCredentials() == null || person.getCredentials().getPassword() == null) {
            prompts.add(new OnboardingUserIdPrompt());
            prompts.add(new OnboardingPasswordPrompt());
        } else {
            if (StringUtils.isBlank( person.getTraits().getUsername())) {
                prompts.add(new OnboardingUserIdPrompt());
            }
            if (person.getCredentials().getPassword().getConfig() == null ||
             StringUtils.isBlank( person.getCredentials().getPassword().getConfig().getPassword())) {
                prompts.add(new OnboardingPasswordPrompt());
            }
        }

        // set them as complete or not based on if they exist
        if (prompts.stream().filter(x -> x instanceof  OnboardingBasicInfoPrompt).findFirst().isEmpty()) {
            basicInfoPrompt.done = true;
            prompts.add(basicInfoPrompt);
        }

        // set them as complete or not based on if they exist
        if (prompts.stream().filter(x -> x instanceof  OnboardingUserIdPrompt).findFirst().isEmpty()) {
            OnboardingUserIdPrompt p = new OnboardingUserIdPrompt();
            p.done = true;
            prompts.add(p);
        }

        if (prompts.stream().filter(x -> x instanceof  OnboardingPasswordPrompt).findFirst().isEmpty()) {
            OnboardingPasswordPrompt p = new OnboardingPasswordPrompt();
            p.done = true;
            prompts.add(p);
        }


        return prompts;
    }

    @Override
    public Double getRank() {
        return 0.0;
    }
}
