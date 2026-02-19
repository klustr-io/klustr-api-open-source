package io.klustr.persons.demographics;

import io.klustr.persons.PersonStorage;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.demographics.Demographic;
import io.klustr.schemas.persons.households.Household;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.joda.time.DateTime;
import org.joda.time.Years;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RethinkDbDemographicProvider implements DemographicProvider {

    private final PersonStorage storage;

    public RethinkDbDemographicProvider(PersonStorage storage) {
        this.storage = storage;
    }

    @Override
    public Demographic getDemographics(String personId) {

        Person p = this.storage.persons().getObject(personId);

        if (p == null) return new Demographic();

        Demographic d = new Demographic();

        if (p.getTraits().getBirthdate() != null) {
            int years = Years.yearsBetween(new DateTime(p.getTraits().getBirthdate()), DateTime.now()).getYears();
            if (years <= 14) {
                d.setAge(Demographic.Age._0_TO_14);
            } else if (years <= 19) {
                d.setAge(Demographic.Age._15_TO_19);
            } else if (years <= 26) {
                d.setAge(Demographic.Age._20_TO_26);
            } else if (years <= 34) {
                d.setAge(Demographic.Age._27_TO_34);
            } else if (years <= 40) {
                d.setAge(Demographic.Age._35_TO_40);
            } else if (years <= 60) {
                d.setAge(Demographic.Age._41_TO_60);
            } else {
                d.setAge(Demographic.Age._61_PLUS);
            }
        }

        if (p.getTraits().getGender() != null) {
            d.setGender(p.getTraits().getGender());
        }

        if (p.getStatus() != null) {
            if (p.getStatus().getRelationshipStatus() != null) {
                d.setRelationshipStatus(p.getStatus().getRelationshipStatus());
            }
        }

        if (p.getStatus() != null ) {
            if (p.getStatus().getParentalStatus() != null) {
                d.setParentalStatus(p.getStatus().getParentalStatus());
            }
        }

        // household demographic
        DbQuery fq = Db.query("members").with("person_id").eq(p.getId());
        Optional<Household> household = this.storage.households().findFirst(fq);
        if (household.isEmpty()) {
            return d;
        }

        if (household.get().getMembers() != null) {
            d.setHousehold(!household.get().getMembers().isEmpty() && household.get().getMembers().size() >= 2);
        }

        return d;
    }
}
