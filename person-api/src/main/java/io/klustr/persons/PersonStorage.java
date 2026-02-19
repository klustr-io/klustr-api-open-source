package io.klustr.persons;

import io.klustr.schemas.console.consent.ConsentDelegationRequest;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.households.Household;
import io.klustr.storage.docs.DocumentDatabaseFactory;
import io.klustr.storage.DbAdapter;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PersonStorage {

    private final DbAdapter<Person> persons;

    private final DbAdapter<Household> households;

    private final DbAdapter<ConsentDelegationRequest> consent_delegation;

    public PersonStorage(DocumentDatabaseFactory pool, DbAdapterBroadcasterFactory broadcaster) {
        this.persons = new DbAdapter<>("identity","persons", pool, Person.class, broadcaster);
        this.households = new DbAdapter<>("identity","households", pool, Household.class, broadcaster);

        this.consent_delegation = new DbAdapter<>("identity","consent_delegation", pool, ConsentDelegationRequest.class, broadcaster)
                .ensureIndex("subject_id")
                .ensureIndex("household_id")
                .ensureIndex("client_id")
                .ensureIndex("creation_date")
                .ensureIndex("org_id");
    }

    public DbAdapter<Person> persons() {
        return this.persons;
    }

    public DbAdapter<Household> households() {
        return this.households;
    }

    public DbAdapter<ConsentDelegationRequest> consent_delegation() {
        return this.consent_delegation;
    }


}
