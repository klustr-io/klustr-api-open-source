package io.klustr.persons;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.Gender;
import io.klustr.schemas.console.identity.*;
import io.klustr.schemas.integrations.ory.*;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.klustr.schemas.persons.ContactInformation;
import io.klustr.schemas.persons.Name;
import io.klustr.schemas.persons.Person;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class LoadUsersTest {

    private static UserService svc;

    private static final KratosApi kratos = new KratosApi("https://kratos-admin.dev.klustr.io", "kratos");

    private static PersonStorage storage;

    @BeforeAll
    public static void init() {
        RethinkDbConnectionPool pool = new RethinkDbConnectionPool("rethinkdb.dev.klustr.io", 28015, new CompositeMeterRegistry());
        storage = new PersonStorage(new RethinkDbDocumentDatabaseFactory(pool), mock());
        svc = new UserService(storage, kratos);
    }

    public static Person fromTest(Identity identity) {
        return new Person()
                .withId(identity.getId())
                .withNamespace("test")
                .withTraits(identity.getTraits())
                .withMetadataPublic(identity.getMetadataPublic())
                .withContact(new ContactInformation()
                        .withEmail(identity.getTraits().getEmail())
                )
                .withName(
                        Lists.newArrayList(
                                new Name()
                                        .withGivenName(identity.getTraits().getName().getGivenName())
                                        .withFamilyName(identity.getTraits().getName().getFamilyName())
                                        .withSource(Name.Source.USER)
                        )
                );
    }

    @Test
    public void load_users() {
        new ExampleUserData().read((identity) -> {

            identity.getCredentials().getPassword();

            identity = registerInKratos(identity);
            Person person = fromTest(identity);

            storage.persons().deleteObject(person.getId());
            storage.persons().insertObject(person.getId(), person);

            System.out.println(person.getId());

            return true;
        });
    }

    @Test
    public void register_admin() {
        Identity admin = new Identity()
                .withMetadataPublic(new IdentityMetadataPublic()
                        .withPicture("https://cdn.dev.klustr.io/profiles/faces/dd0ee88c76510aeb70b8ea15a07e1141.png")
                )
                .withTraits(new IdentityTraits()
                        .withBirthdate(LocalDate.now())
                        .withEmail("admin@klustr.io")
                        .withName(new IdentityName()
                                .withFamilyName("Super")
                                .withGivenName("User")
                        )
                        .withGender(Gender.unknown)
                        .withLocale("en-us")
                )
                .withCredentials(new IdentityCredentials()
                        .withPassword(new IdentityPasswordCredential()
                                .withConfig(new IdentityPasswordCredentialConfig()
                                        .withPassword("admin")
                                )
                        )
                )
                .withVerifiableAddresses(Lists.newArrayList(
                        new IdentityVerifiableAddress()
                                .withValue("admin@klustr.io")
                                .withVerified(true)
                                .withStatus("completed")
                                .withVerifiedAt(DateTime.now())
                ));
        System.out.println( U.toJsonPrettyFormat(admin));
        kratos.create(admin);
    }

    public Identity registerInKratos(Identity identity) {
        Identity match = kratos.getByEmail(identity.getVerifiableAddresses().get(0).getValue());
        if (match != null) {
            kratos.deleteIdentity(match.getId());
        }
        return kratos.create(identity);
    }
}
