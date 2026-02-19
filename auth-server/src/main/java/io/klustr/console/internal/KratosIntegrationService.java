package io.klustr.console.internal;

import com.google.common.collect.Lists;
import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.persons.integrations.KratosIdentityMap;
import io.klustr.schemas.console.identity.IdentityMetadataPublic;
import io.klustr.schemas.integrations.ory.IdentityMetadataAdmin;
import io.klustr.schemas.persons.ContactInformation;
import io.klustr.schemas.persons.ExternalReference;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Name;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.integrations.ory.KratosWebhookLoginPayload;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Enables a webhook to be called when kratos gets login and or registrations
 * so that we can synchronize the kratos identity information with our
 * person information.
 */
@RestController
@Component
@RequestMapping("/ory/kratos")
@ResourceGroup("Ory Integration")
@Tag(name = "Ory Integration")
public class KratosIntegrationService extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(KratosIntegrationService.class);

    private final KratosApi kratos;

    private final RethinkDbPersonRepository persons;

    private final KafkaTemplate<KratosWebhookLoginPayload> kafka;

    private final InstrumentationFactory metrics;

    private final Counter loginCounter;

    public KratosIntegrationService(KratosApi kratos,
                                    RethinkDbPersonRepository persons,
                                    KafkaTemplate<KratosWebhookLoginPayload> kafka,
                                    MeterRegistry registry) {
        this.kratos = kratos;
        this.persons = persons;
        this.kafka = kafka;
        this.metrics = new InstrumentationFactory(registry);
        this.loginCounter = registry.counter("hydra.login");
    }

    /**
     * captures the login event for a user.
     *
     * https://www.ory.sh/docs/guides/integrate-with-ory-cloud-through-webhooks#flow-interrupting-webhooks
     *
     * @return The identity of the user.
     */
    @PostMapping("/login")
    @Operation(
description = """
This endpoint captures the login event for a user. It allows for the synchronization of identity information with personal data. The visibility level is user-specific, ensuring that only relevant login events are processed.
""",
            operationId = "trackUserLogin",
            summary = "Capture user login events for synchronization."
)
    public void login(@RequestBody String json,
                        @RequestParam("source") String source) {

        KratosWebhookLoginPayload payload = U.fromJson(json, KratosWebhookLoginPayload.class);

        this.kafka.send(UUID.randomUUID().toString(), payload);

        this.loginCounter.increment();

        // grab latest
        Identity identity = this.kratos.getIdentity(payload.getIdentity().getId());

        try {
            // find out if this person exists under this identifier
            Optional<Person> person = this.persons.tryGetPersonByExternalIdentity(source, payload.getIdentity().getId());
            if (person.isEmpty()) {
                Person p = new KratosIdentityMap().fromIdentity(identity);
                this.persons.createPerson(p);
                person = Optional.of(p);
            }


            // check for any oidc references and matches!
            if (identity.getCredentials() != null && identity.getCredentials().getOidc() != null) {
                List<String> identifiers = identity.getCredentials().getOidc().getIdentifiers();
                Set<ExternalReference> refs = identifiers.stream().map(x -> new ExternalReference().withSource(x.split(":")[0]).withExternalId(x.split(":")[1])).collect(Collectors.toSet());
                for (ExternalReference ref: refs) {
                    Optional<ExternalReference> match = person.get().getIds().stream().filter(x -> {
                        return x.getSource().equalsIgnoreCase(ref.getSource())
                                && x.getExternalId().equalsIgnoreCase(ref.getExternalId());
                    }).findFirst();
                    if (match.isEmpty()) {
                        person.get().getIds().add(ref.withCreationDate(DateTime.now()).withUpdateDate(DateTime.now()));
                    } else {
                        // update reference and reset expiry
                        match.get().withExpirationDate(null).withUpdateDate(DateTime.now());
                    }
                }
                this.persons.updatePerson(person.get());
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    public static class KratosIdentityWebhookResponse {
        public KratosIdentityWebhookBody identity;
    }

    public static class KratosIdentityWebhookBody {
        public IdentityMetadataAdmin metadata_admin;
        public IdentityMetadataPublic metadata_public;
    }
}