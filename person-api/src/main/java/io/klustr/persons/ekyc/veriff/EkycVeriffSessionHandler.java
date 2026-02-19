package io.klustr.persons.ekyc.veriff;

import io.klustr.persons.ekyc.EkycStorage;
import io.klustr.persons.ekyc.EkycUserSession;
import io.klustr.schemas.persons.veriff.*;
import io.klustr.schemas.persons.verification.PersonVerificationSession;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

public class EkycVeriffSessionHandler {
    private static final Logger log = LoggerFactory.getLogger(EkycVeriffSessionHandler.class);

    private final VeriffIntegration veriff;
    private final EkycStorage storage;

    public EkycVeriffSessionHandler(EkycStorage storage,
                                    VeriffIntegration veriff) {
        this.veriff = veriff;
        this.storage = storage;
    }

    public PersonVerificationSession request(String userId, Identity identity, String callback) {
        String requestId = UUID.randomUUID().toString();

        if (identity.getTraits().getName() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person is required to have name information."
            );
        }
        if (StringUtils.isBlank(identity.getTraits().getName().getFamilyName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person must have a family name."
            );
        }
        if (StringUtils.isBlank(identity.getTraits().getName().getGivenName())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person must have a given name."
            );
        }
        if (identity.getTraits().getBirthdate() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person is required to have a date of birth."
            );
        }

        // TODO by country?
        if (new DateTime(identity.getTraits().getBirthdate()).isAfter(DateTime.now().withTimeAtStartOfDay().minusYears(14))) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Person can not be verified is age is less than 14."
            );
        }

        // get the available sessions
        EkycUserSession ekyc_session = this.storage.eykc_sessions().getObject(userId);
        boolean exists = (ekyc_session != null);
        if (!exists) {
            ekyc_session = new EkycUserSession();
            ekyc_session.id = userId;
        }

        String gender = identity.getTraits().getGender() != null ? identity.getTraits().getGender().value().startsWith("m") ? "M" : "F" : "M";

        // check if we already made this exact same request before
        // if so we can check the status
        // TODO possible race condition if multiple requests made or load balanced to multiple servers
        if (ekyc_session.sessions.containsKey(requestId)) {
            return ekyc_session.sessions.get(requestId);
        } else {

            DateTimeFormatter yyyy_MM_dd = DateTimeFormat.forPattern("yyyy-MM-dd");
            // java.time.format.DateTimeFormatter yyyy_MM_dd = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = identity.getTraits().getBirthdate();

            VeriffSessionRequest veriff_api_request = new VeriffSessionRequest()
                    .withVerification(new VeriffSessionVerification()
                            .withCallback(callback)
                            .withPerson(new VeriffPersonRequest()
                                    .withFirstName(identity.getTraits().getName().getGivenName())
                                    .withLastName(identity.getTraits().getName().getFamilyName())
                                    .withGender(gender)
                                    .withDateOfBirth(dob.toString(yyyy_MM_dd))
                            )
                            .withVendorData(userId)
                    );

            VeriffSessionResponse veriff_session = this.veriff.session(veriff_api_request);

            PersonVerificationSession session = new PersonVerificationSession()
                    .withSessionToken(veriff_session.getVerification().getSessionToken())
                    .withUrl(veriff_session.getVerification().getUrl())
                    .withStatus(veriff_session.getVerification().getStatus())
                    .withId(veriff_session.getVerification().getId());

            ekyc_session.sessions.put(requestId, session);

            if (exists) {
                this.storage.eykc_sessions().updateObject(userId, ekyc_session);
            } else {
                this.storage.eykc_sessions().insertObject(userId, ekyc_session);
            }

            return session;
        }
    }
}
