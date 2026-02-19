package io.klustr.consent.prompts;

import com.google.common.collect.Lists;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.SupervisedAccountConsentPrompt;
import io.klustr.console.storage.Storage;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.util.List;
import java.util.Optional;

/**
 * Given that prompts and consent require an age of majority
 * we must check and see if the requesting user is of age or
 * not. If they are not of age we filter any age restricted
 * prompts and leave it for the parent and inject back to the
 * UX flow a supervised request.
 */
public class SupervisedAccountFilter {

    private final PersonStorage storage;

    public SupervisedAccountFilter(PersonStorage storage) {
        this.storage = storage;
    }

    public List<AbstractPrompt> filter(Person person, List<AbstractPrompt> prompts) {
        return filter_age_restricted(person, prompts);
    }

    private List<AbstractPrompt> filter_age_restricted(Person person, List<AbstractPrompt> prompts) {
        // restricted/age accounts can not perform actions

        if (person.getTraits() == null) return prompts;
        if (person.getTraits().getBirthdate() == null) return prompts;

        // person is of age of consent
        if (person.getTraits().getBirthdate().isBefore(LocalDate.now().minusYears(16))) return prompts;

        // person is under 16
        List<AbstractPrompt> ageRestricted = prompts.stream().filter(AbstractPrompt::requires_age_of_consent).toList();
        if (ageRestricted.isEmpty()) return prompts;

        List<AbstractPrompt> results = Lists.newArrayList(prompts);
        results.removeIf(AbstractPrompt::requires_age_of_consent);

        // add supervised account prompt so child can request
        // access to this application
        SupervisedAccountConsentPrompt prompt = new SupervisedAccountConsentPrompt();
        // household demographic
        DbQuery fq = Db.query("members").contains("person_id").eq(person.getId());
        Optional<Household> household = this.storage.households().findFirst(fq);
        if (household.isPresent()) {
            // cant find the supervisor of this account!
            Optional<HouseholdMember> primary = household.get().getMembers().stream().filter(HouseholdMember::getPrimary).findFirst();
            if (primary.isPresent()) {
                Person admin = this.storage.persons().getObject(primary.get().getPersonId());
                prompt.supervisor = admin.getTraits();
            }
        }

        results.add(prompt);

        return results;
    }
}
