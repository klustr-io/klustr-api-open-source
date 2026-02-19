package io.klustr.persons.integrations;

import com.google.common.collect.Lists;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.persons.ContactInformation;
import io.klustr.schemas.persons.ExternalReference;
import io.klustr.schemas.persons.Person;
import org.joda.time.DateTime;

public class KratosIdentityMap {

    public Person fromIdentity(Identity identity) {
        return new Person()
                .withId(identity.getId())
                .withTraits(identity.getTraits())
                .withMetadataPublic(identity.getMetadataPublic())
                .withIds(Lists.newArrayList(
                        new ExternalReference()
                                .withCreationDate(DateTime.now())
                                .withSource(identity.getSource())
                                .withExternalId(identity.getId())
                ))
                .withContact(new ContactInformation()
                        .withEmail(identity.getTraits().getEmail())
                );
    }
}
