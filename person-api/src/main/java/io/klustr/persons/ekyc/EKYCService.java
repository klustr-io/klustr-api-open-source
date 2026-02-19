package io.klustr.persons.ekyc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.ekyc.veriff.EkycVeriffSessionHandler;
import io.klustr.persons.ekyc.veriff.VeriffIntegration;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.PersonVerification;
import io.klustr.schemas.persons.verification.PersonVerificationRequest;
import io.klustr.schemas.persons.verification.PersonVerificationSession;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.spring.OAuthCredentialType;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Handles the overall onboarding flow and state management for users.
 */
@RestController
@Component
@RequestMapping("/ekyc/me")
@Tag(name = "EKYC APIs", description = "Trusted verification of people and users.")
public class EKYCService extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(EKYCService.class);

    private final VeriffIntegration veriff;
    private final EkycStorage storage;

    private final RethinkDbPersonRepository persons;

    private final KratosApi kratos;

    private final EkycVeriffSessionHandler handler;

    public EKYCService(EkycStorage storage,
                       KratosApi kratos,
                       RethinkDbPersonRepository persons,
                       VeriffIntegration veriff) {
        this.veriff = veriff;
        this.storage = storage;
        this.persons = persons;
        this.kratos = kratos;
        this.handler = new EkycVeriffSessionHandler(storage, veriff);
    }


    @PostMapping("/sessions")
    @Operation(
summary = "Initiate a personal verification session for users",
            operationId = "startPersonVerificationSession",
            description = """
This endpoint initiates a secure eKYC session for individual users. It allows users to verify their identity while ensuring privacy and data protection. The session is designed to facilitate trusted verification processes.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public PersonVerificationSession session(@RequestBody PersonVerificationRequest request,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        Identity identity = this.kratos.getIdentity(user.getName());
        return this.handler.request(user.getName(), identity, request.getCallback());
    }


    @GetMapping("/sessions")
    @Operation(
summary = "Retrieve current user's verification session details",
            operationId = "getMyEkycUserSession",
            description = """
This endpoint allows authenticated users to access their ongoing verification session. It provides crucial information about their current status in the onboarding process, helping users understand their verification progress and the next steps they need to take.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public EkycUserSession getUserSessions(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        String userId = user.getName();
        EkycUserSession session = this.storage.eykc_sessions().getObject(userId);
        if (session == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity is not owned by user."
            );
        }
        return session;
    }

    /**
     * Returns the session associated to the current log-in user.
     *
     * @param user The context of the logged-in user.
     */
    @GetMapping("/status")
    @Operation(
summary = "Retrieve current verification status for the authenticated user",
            operationId = "getMyUserStatus",
            description = """
This endpoint allows users to check their current verification status. It is designed for personal use, enabling users to monitor their onboarding progress. The response includes essential details about the user's verification state.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public UserStatusResponse getUserStatus(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        String userId = user.getName();
        EkycUserSession session = this.storage.eykc_sessions().getObject(userId);
        if (session == null) {
            return new UserStatusResponse();
        }
        if (session.sessions.isEmpty()) {
            return new UserStatusResponse();
        }

        Optional<PersonVerificationSession> created = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("created")).findFirst();
        Optional<PersonVerificationSession> success = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("success")).findFirst();
        Optional<PersonVerificationSession> started = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("started")).findFirst();
        Optional<PersonVerificationSession> rejected = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("rejected")).findFirst();
        Optional<PersonVerificationSession> submitted = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("submitted")).findFirst();
        Optional<PersonVerificationSession> expired = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("expired")).findFirst();


        UserStatusResponse res = new UserStatusResponse();

        Optional<Person> person = persons.tryGetPersonById(userId);
        if (person.isPresent()) {
            PersonVerification verification = person.get().getVerification();
            if (verification != null) {
                res.verification = verification;
            }
        }

        res.started = started.orElse(null);
        res.success = success.orElse(null);
        res.rejected = rejected.orElse(null);
        res.created = created.orElse(null);
        res.submitted = submitted.orElse(null);
        res.expired = expired.orElse(null);
        res.total_sessions = session.sessions.size();
        return res;
    }

    @GetMapping("/sessions/current")
    @Operation(
summary = "Retrieve recent verification request for the authenticated user",
            operationId = "getMyMostRecentVerificationRequest",
            description = """
This endpoint allows users to access their most recent verification request status. It provides essential insights into the verification progress, fostering transparency and trust during the onboarding journey.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public PersonVerificationRequest getMostRecentRequest(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal user) {
        String userId = user.getName();
        EkycUserSession session = this.storage.eykc_sessions().getObject(userId);
        if (session == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity is not owned by user."
            );
        }
        Optional<PersonVerificationSession> prev = session.sessions.values().stream().findFirst();
        if (prev.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Entity is not owned by user."
            );
        }
        return prev.get().getOriginalRequest();
    }

    public static class UserStatusResponse {
        public DateTime timestamp = DateTime.now();
        public int total_sessions = 0;
        public PersonVerification verification;
        public PersonVerificationSession started;
        public PersonVerificationSession rejected;
        public PersonVerificationSession success;
        public PersonVerificationSession expired;
        public PersonVerificationSession submitted;
        public PersonVerificationSession created;
    }
}
