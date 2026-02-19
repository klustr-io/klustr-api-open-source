package io.klustr.persons.repositories;

import io.klustr.persons.PersonStorage;
import io.klustr.schemas.persons.Person;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RethinkDbPersonRepository {

    private final PersonStorage storage;

    public RethinkDbPersonRepository(PersonStorage storage) {
        this.storage = storage;
    }

    public Optional<Person> tryGetPersonById(String id) {
        Person person = this.storage.persons().getObject(id);
        if (person == null) {
            return Optional.empty();
        } else {
            return Optional.of(person);
        }
    }

    public Optional<Person> tryGetPersonByExternalIdentity(String externalSource, String externalId) {
        DbQuery fq =
                Db.and(
                    Db.query("references").contains("source").eq(externalSource),
                        Db.query("references").contains("external_id").eq(externalId));
        // find the person in the system by their external identifier
        return this.storage.persons().findFirst(fq);
    }

    public void createPerson(Person p) {
        this.storage.persons().insertObject(p.getId(), p);
    }

    public void updatePerson(Person p) {
        this.storage.persons().updateObject(p.getId(), p);
    }

    public void deletePerson(Person p) {
        p = p.withDeleted(true);
        this.storage.persons().updateObject(p.getId(), p);
    }

}
