package io.klustr.persons.ekyc.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.ekyc.EKYCService;
import io.klustr.persons.ekyc.EkycStorage;
import io.klustr.persons.ekyc.EkycUserSession;
import io.klustr.persons.ekyc.veriff.EkycVeriffSessionHandler;
import io.klustr.persons.ekyc.veriff.VeriffIntegration;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.PersonVerification;
import io.klustr.schemas.persons.verification.PersonVerificationSession;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Handles the overall onboarding flow and state management for users.
 */
@RestController
@Component
@RequestMapping("/ekyc/admin")
@Tag(name = "EKYC Admin APIs", description = "Trusted verification of people and users.")
public class EKYCAdminService extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(EKYCAdminService.class);
    private final EkycStorage storage;

    private final RethinkDbPersonRepository persons;

    private final EkycVeriffSessionHandler handler;

    private final KratosApi kratos;

    public EKYCAdminService(EkycStorage storage,
                            KratosApi kratos,
                            RethinkDbPersonRepository persons,
                            VeriffIntegration veriff) {
        this.storage = storage;
        this.persons = persons;
        this.kratos = kratos;
        this.handler = new EkycVeriffSessionHandler(storage, veriff);
    }

    /**
     * Creates a new verification session
     *
     * @return The session found for the user.
     */
    @PostMapping("/{userId}/sessions")
    @Operation(
summary = "Create a session for user verification",
            operationId = "adminCreateUserSession",
            description = """
Initiates a verification session for a specific user identified by userId. This operation is intended for admin use, allowing trusted verification processes to be managed effectively. Ensure that the provided session request adheres to the required structure for successful session creation.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('ekyc.session.create')")
    public PersonVerificationSession createSession(@PathVariable("userId") String userId, @RequestBody SessionRequest request) {
        Identity identity = this.kratos.getIdentity(userId);
        return this.handler.request(userId, identity, request.callback);
    }

    /**
     * Gets the specified users session.
     *
     * @return The session found for the user.
     */
    @GetMapping("/{userId}/sessions")
    @Operation(
summary = "Retrieve a specific user's session details",
            operationId = "adminGetUserSession",
            description = """
This endpoint allows administrators to retrieve the session details of a specific user identified by their user ID. It is intended for use in managing user sessions and ensuring proper onboarding flow and state management. This operation is crucial for maintaining oversight and control over user interactions within the EKYC system.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('ekyc.session.get')")
    public EkycUserSession list(@PathVariable("userId") String userId) {
        EkycUserSession session = this.storage.eykc_sessions().getObject(userId);
        if (session == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No sessions available for the user id."
            );
        }
        return session;
    }

    @GetMapping("/{userId}/status")
    @Operation(
summary = "Retrieve the session status of a specific user",
            operationId = "adminGetUserStatus",
            description = """
This endpoint allows administrators to access the session status of a specified user. It provides insights into the user's current onboarding state and session details. This information is crucial for managing user verification processes effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('ekyc.session.get')")
    public EKYCService.UserStatusResponse getUserStatus(@PathVariable("userId") String userId) {
        EkycUserSession session = this.storage.eykc_sessions().getObject(userId);
        if (session == null) {
            return new EKYCService.UserStatusResponse();
        }
        if (session.sessions.isEmpty()) {
            return new EKYCService.UserStatusResponse();
        }

        Optional<PersonVerificationSession> created = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("created")).findFirst();
        Optional<PersonVerificationSession> success = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("success")).findFirst();
        Optional<PersonVerificationSession> started = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("started")).findFirst();
        Optional<PersonVerificationSession> rejected = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("rejected")).findFirst();
        Optional<PersonVerificationSession> submitted = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("submitted")).findFirst();
        Optional<PersonVerificationSession> expired = session.sessions.values().stream().filter(x -> x.getStatus().equalsIgnoreCase("expired")).findFirst();


        EKYCService.UserStatusResponse res = new EKYCService.UserStatusResponse();

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

    /**
     * Returns all the sessions in the database.
     *
     * @return The sessions in the database.
     */
    @GetMapping
    @Operation(
summary = "Admin list of user sessions",
            operationId = "adminListUserSessions",
            description = """
Retrieves a list of user sessions for administrative oversight. This endpoint allows administrators to manage and monitor user activity effectively. The results can be paginated using the 'limit' and 'start' parameters to facilitate efficient data handling.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('ekyc.session.list')")
    public List<EkycUserSession> list(
            @RequestParam(value = "limit", defaultValue = "1024") Integer limit,
            @RequestParam(value = "start", defaultValue = "0") Integer start
    ) {
        List<EkycUserSession> list = this.storage.eykc_sessions().insecureQuery(new Pagination().withStart(start).withLimit(limit)).docs;
        return list;
    }

    public static class SessionRequest {
        public String callback;
    }

}
