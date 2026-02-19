package io.klustr.persons.ekyc.veriff;

import com.google.common.collect.Lists;
import io.klustr.schemas.persons.veriff.*;
import io.klustr.utils.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.ekyc.EkycUserSession;
import io.klustr.schemas.persons.Name;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.PersonIdentityDocument;
import io.klustr.schemas.persons.PersonVerification;
import io.klustr.schemas.persons.verification.VerificationEvidence;
import io.klustr.schemas.console.Gender;
import io.klustr.schemas.console.identity.IdentityName;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/ekyc/veriff")
@Tag(name = "EKYC Veriff APIs", description = "Trusted verification of people and users.")
public class EkycVeriffWebhookService extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(EkycVeriffWebhookService.class);

    private final VerificationStorage storage;

    private final PersonStorage burr;

    private final KafkaTemplate<PersonVerification> kafka;

    public EkycVeriffWebhookService(PersonStorage burr, VerificationStorage storage, KafkaTemplate<PersonVerification> kafka) {
        this.storage = storage;
        this.kafka = kafka;
        this.burr = burr;
    }

    private static Name getName(VeriffPerson veriff_person, VerificationEvidence identity_evidence) {
        return new Name()
                .withFamilyName(tryGetValue( veriff_person.getLastName()))
                .withGivenName(tryGetValue(veriff_person.getFirstName()))
                .withSource(Name.Source.EKYC)
                .withLocale("en");
    }

    @NotNull
    private static Gender getGender(VeriffPerson veriff_person) {
        String g = tryGetValue(veriff_person.getGender());
        return StringUtils.isNotBlank(g) ? Gender.unknown :
                g.equalsIgnoreCase("m") ?
                        Gender.male : Gender.female;
    }

    private static VerificationEvidence getVerificationEvidence(VeriffVerification veriff) {
        return new VerificationEvidence()
                .withVerificationId(veriff.getId())
                .withVerificationMethod(VerificationEvidence.VerificationMethod.GOVERNMENT_ISSUED_ID)
                .withVerificationStatus(VerificationEvidence.VerificationStatus.APPROVED)
                .withVendorData(veriff.getId())
                .withVerificationDate(veriff.getDecisionTime())
                .withSubmitDate(veriff.getAcceptanceTime())
                .withReferenceUrl(URI.create("https://station.veriff.com/verifications/c8c76870-b006-4c6a-9e87-0ddb2123d41d"))
                .withSourceSystem("veriff");
    }

    /**
     * Receives events from Veriff around the status and updates which
     * typically relate to pending or processing requests.
     *
     * @param json The Veriff object that was sent with information.
     */
    @PostMapping("/events")
    @Operation(
summary = "Listen for verification events from external vendors.",
            operationId = "listenVerificationEvent",
            description = """
This endpoint receives and processes verification events sent by external vendors. It ensures timely updates and notifications regarding user verification status. This functionality is crucial for maintaining accurate user verification records and enhancing the overall user experience.
"""
)
    public void events(@RequestBody String json) {
        // can get abandoned status useful for reminders
        audit(json, "events");

        VeriffEventPayload payload = U.fromJson(json, VeriffEventPayload.class);
        String userId = payload.getVendorData();

        Optional<Person> person = this.burr.persons().tryGetObject(userId);
        if (person.isEmpty()) {
            return;
        }

        // update the session
        updateSessionStatus(person.get().getId(), payload.getId(), payload.getAction().value());

        // set their verification status
        PersonVerification verification = new PersonVerification()
                .withStatus(PersonVerification.PersonVerificationStatus.fromValue(payload.getAction().toString()))
                .withTimestamp(DateTime.now());
        person.get().setVerification(
                verification
        );
        log.info("Updated person verification status: " + U.toJson(person.get().getVerification()));

        this.burr.persons().updateObject(person.get().getId(), person.get());

        this.kafka.send(person.get().getId(), person.get().getVerification());
    }

    void updateSessionStatus(String personId, String id, String status) {
        EkycUserSession user_sessions = this.storage.eky_user_sessions().getObject(personId);
        Optional<String> matchingKey = user_sessions.sessions.keySet().stream().filter((key) -> {
            return user_sessions.sessions.get(key).getId().equalsIgnoreCase(id);
        }).findFirst();
        if (matchingKey.isEmpty()) return;
        user_sessions.sessions.get(matchingKey.get()).setStatus(status);
        this.storage.eky_user_sessions().deleteObject(personId);
        this.storage.eky_user_sessions().insertObject(personId, user_sessions);
    }

    void removeSession(String personId, String id) {
        EkycUserSession user_sessions = this.storage.eky_user_sessions().getObject(personId);
        Optional<String> matchingKey = user_sessions.sessions.keySet().stream().filter((key) -> {
            return user_sessions.sessions.get(key).getId().equalsIgnoreCase(id);
        }).findFirst();
        if (matchingKey.isEmpty()) return;
        user_sessions.sessions.remove(matchingKey.get());
        EkycUserSession clone = U.fromJson(U.toJson(user_sessions), EkycUserSession.class);
        this.storage.eky_user_sessions().deleteObject(personId);
        this.storage.eky_user_sessions().insertObject(personId, clone);
    }

    void cleanUpUserSessions(VeriffNotificationPayload payload) {
        if (payload == null || payload.getData() == null || payload.getData().getVerification() == null) {
            return;
        }
        String personId = payload.getVendorData();
        VeriffVerification verification = payload.getData().getVerification();

        PersonVerification.PersonVerificationStatus status = toStatus(verification);
        if (status == PersonVerification.PersonVerificationStatus.ABANDONED) {
            // delete
            removeSession(personId, verification.getId());
        }
        if (status == PersonVerification.PersonVerificationStatus.REJECTED) {
            // delete
            removeSession(personId, verification.getId());
        }
        if (status == PersonVerification.PersonVerificationStatus.DECLINED) {
            // delete
            removeSession(personId, verification.getId());
        }
        if (status == PersonVerification.PersonVerificationStatus.APPROVED) {
            // delete
            removeSession(personId, verification.getId());
        }
        if (status == PersonVerification.PersonVerificationStatus.EXPIRED) {
            // delete
            removeSession(personId, verification.getId());
        }
    }

    /**
     * Recieves the notification callback webhooks from the Veriff system that
     * will inform of the status of verification as in approved, rejected, etc.
     *
     * @param obj
     */
    @PostMapping("/notification")
    @Operation(
summary = "Receive external verification notifications for user events.",
            operationId = "listenVerificationNotification",
            description = """
This endpoint listens for verification events from an external vendor. It processes notifications related to user verification status updates. Ensure that the payload is correctly formatted to facilitate accurate handling of the event.
"""
)
    public void notification(@RequestBody String obj) {
        audit(obj, "notification");

        // we got a notification, we can update BURR with the latest
        VeriffNotificationPayload notification = U.fromJson(obj, VeriffNotificationPayload.class);
        if (notification == null) return;

        try {
            String personId = notification.getVendorData();

            if (StringUtils.isBlank(notification.getId())) {
                notification.setId(UUID.randomUUID().toString());
            }
            this.storage.veriff_notifications().insertObject(notification.getId(), notification);

            // user was verified
            if (notification.getStatus().equalsIgnoreCase("success")) {
                createOrUpdatePerson(notification);
            }

            Optional<Person> person = this.burr.persons().tryGetObject(personId);
            if (person.isPresent()) {
                PersonVerification.PersonVerificationStatus result = PersonVerification.PersonVerificationStatus.REJECTED;
                result = PersonVerification.PersonVerificationStatus.fromValue(notification.getStatus().toLowerCase());
                person.get().setVerification(
                        new PersonVerification()
                                .withStatus(result)
                                .withReason(notification.getData().getVerification().getReason())
                                .withTimestamp(DateTime.now())
                );
                this.burr.persons().updateObject(person.get().getId(), person.get());
                this.kafka.send(person.get().getId(), person.get().getVerification());
            }
        } finally {
            cleanUpUserSessions(notification);
        }
    }

    private void createOrUpdatePerson(VeriffNotificationPayload notification) {

        String user_id = notification.getVendorData();

        Optional<Person> person = this.burr.persons().tryGetObject(user_id);
        if (person.isEmpty()) {
            createNewPerson(notification.getData().getVerification(), user_id);
        } else {
            updateExistingPerson(notification.getData().getVerification(), person.get());
        }
    }

    PersonVerification.PersonVerificationStatus toStatus(VeriffVerification veriff) {
        if (veriff.getDecision().equalsIgnoreCase("expired")) {
            return PersonVerification.PersonVerificationStatus.ABANDONED;
        } else if (veriff.getDecision().equalsIgnoreCase("approved")) {
            return PersonVerification.PersonVerificationStatus.SUCCESS;
        } else if (veriff.getDecision().equalsIgnoreCase("declined")) {
            return PersonVerification.PersonVerificationStatus.REJECTED;
        }
        log.debug("Unknown verification status of {}", veriff.getDecision());
        return PersonVerification.PersonVerificationStatus.STARTED;
    }

    private void updateExistingPerson(VeriffVerification veriff, Person existing) {
        Optional<PersonIdentityDocument> existing_id_document = existing.getDocuments().stream().filter(x -> {
            return x.getType() == mapToLocalIdType(tryGetValue(veriff.getDocument().getType()));
        }).findFirst();

        // remove and re-add if document already exists...
        // TODO edge case possible multiple ID types from different countries (two passports, etc)
        existing_id_document.ifPresent(personIdentityDocument -> existing.getDocuments().remove(personIdentityDocument));
        existing.getDocuments().add(getPersonIdentityDocument(veriff));


        // ensure we set traits
        existing.setTraits(existing.getTraits() != null ? existing.getTraits() : new IdentityTraits());
        existing
                .getTraits()
                .withBirthdate(tryGetLocalDate( veriff.getPerson().getDateOfBirth()))
                .withGender(getGender(veriff.getPerson()));
        // TODO citizen, locale, countrycode, etc.

        existing.getName().clear();
        existing.getName().add(getName(veriff.getPerson(), getVerificationEvidence(veriff)));
        existing.setVerification(new PersonVerification()
                .withTimestamp(DateTime.now())
                .withStatus(toStatus(veriff)));

        this.burr.persons().updateObject(existing.getId(), existing);
    }

    private static String tryGetValue(VeriffValue v) {
        if (v == null) return null;
        if (v.getValue() == null) return null;
        return v.getValue();
    }

    private static java.time.LocalDate tryGetDate(VeriffValue v) {
        String x = tryGetValue(v);
        if (StringUtils.isBlank(x)) return  null;
        return java.time.LocalDate.parse(v.getValue());
    }

    private static LocalDate tryGetLocalDate(VeriffValue v) {
        String x = tryGetValue(v);
        if (StringUtils.isBlank(x)) return  null;
        return LocalDate.parse(v.getValue());
    }

    private static DateTime tryGetDateTime(VeriffValue v) {
        String x = tryGetValue(v);
        if (StringUtils.isBlank(x)) return  null;
        return DateTime.parse(v.getValue());
    }

    private void createNewPerson(VeriffVerification veriff, String personId) {
        VerificationEvidence identity_evidence = getVerificationEvidence(veriff);

        VeriffPerson veriff_person = veriff.getPerson();



        Person new_person = new Person()
                .withId(personId)
                .withTraits(new IdentityTraits()
                        .withGender(getGender(veriff_person))
                        .withBirthdate(tryGetLocalDate(veriff_person.getDateOfBirth()))
                        .withCitizenship(tryGetValue( veriff_person.getCitizenship()))    // TODO
                        .withLocale("") // TOOD
                        .withName(new IdentityName() // TODO
                                .withFamilyName(tryGetValue(veriff_person.getLastName()))
                                .withGivenName(tryGetValue(veriff_person.getFirstName()))
                        )
                )
                .withCreationDate(DateTime.now())
                .withDocuments(
                        Lists.newArrayList(
                                getPersonIdentityDocument(veriff)
                        )
                )
                .withName(Lists.newArrayList(
                        getName(veriff_person, identity_evidence)
                ));

        new_person.setVerification(new PersonVerification()
                .withTimestamp(DateTime.now())
                .withStatus(toStatus(veriff)));

        this.burr.persons().insertObject(new_person.getId(), new_person);
    }

    private PersonIdentityDocument getPersonIdentityDocument(VeriffVerification veriff) {
        return new PersonIdentityDocument()
                .withCountryCode(tryGetValue(veriff.getDocument().getCountry()))
                .withDateEffective(tryGetDateTime(veriff.getDocument().getValidFrom()))
                .withDateCreated(DateTime.now())
                .withDateExpires(tryGetDateTime( veriff.getDocument().getValidUntil()))
                .withLink(URI.create("https://station.veriff.com/verifications/" + veriff.getId()))
                .withVendorReferenceData(veriff.getId())
                .withVendor("veriff")
                .withType(mapToLocalIdType(tryGetValue( veriff.getDocument().getType())));
    }

    /**
     * Downloading the photo and video files
     * You need to get the sessionId of a session you want to download the media for. This can be found from the decision webhook (verification.id parameter), which is automatically sent after a decision has been made for a verification session.
     * With the sessionId, make a GET request to /sessions/{sessionId}/media endpoint.
     * From there, you will find a mediaId for every media file we have stored.
     * With the mediaId-s, you can make a request to GET /media/{mediaId} endpoint to get the media file in .jpeg or .mp4 format.
     *
     * @param veriff_document_type
     * @return
     */

    private PersonIdentityDocument.IdentificationType mapToLocalIdType(String veriff_document_type) {
        if (StringUtils.isBlank(veriff_document_type)) {
            return null;
        }
        if (veriff_document_type.equalsIgnoreCase("PASSPORT")) {
            return PersonIdentityDocument.IdentificationType.PASSPORT;
        } else if (veriff_document_type.equalsIgnoreCase("ID_CARD")) {
            return PersonIdentityDocument.IdentificationType.ID_CARD;
        } else if (veriff_document_type.equalsIgnoreCase("DRIVERS_LICENSE")) {
            return PersonIdentityDocument.IdentificationType.DRIVERS_LICENSE;
        } else if (veriff_document_type.equalsIgnoreCase("RESIDENCE_PERMIT")) {
            return PersonIdentityDocument.IdentificationType.RESIDENCE_PERMIT;
        } else if (veriff_document_type.equalsIgnoreCase("VISA")) {
            return PersonIdentityDocument.IdentificationType.VISA;
        }
        return null;
    }

    private void audit(String body, String type) {
        // parse
        String json = Json.toJson(Json.toJsonNode(body));
        String key = U.md5(json);
        VeriffMessage msg = new VeriffMessage();
        msg.timestamp = DateTime.now();
        msg.id = key;
        msg.json = Json.toJson(Json.toJsonNode(json));
        msg.type = type;

        this.storage.veriff_audit().insertObject(msg.id, msg);
    }
}

