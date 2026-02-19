package io.klustr.persons;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.schemas.persons.Person;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

/**
 * Gives the digital profile for a user which is displayed
 * to others and them and is a customizable version of thier
 * online presence (vs. verified identity).
 */
@RestController
@Component
@RequestMapping("/identities/admin/persons")
@Tag(name = "Person Admin APIs", description = "Identity Management APIs")
public class PersonAdminService extends AbstractApiService {

    private final PersonStorage storage;

    public PersonAdminService(PersonStorage storage) {
        this.storage = storage;
    }

    @GetMapping
    @Operation(
summary = "List registered persons in the identity management system.",
            operationId = "adminListRegisteredPersons",
            description = """
This endpoint retrieves a paginated list of persons registered in the identity management system. It is intended for administrative users to view and manage user profiles. Use the limit and start parameters to control the number of results returned.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('persons.list')")
    public DocumentResult<Person> listPeople(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        return this.storage.persons().insecureQuery(new Pagination().withStart(start).withLimit(limit));
    }

    @PostMapping
    @Operation(
summary = "Create a new person record as an admin.",
            operationId = "adminCreatePersonRecord",
            description = """
This endpoint enables an admin to create a new person record in the identity management system. The created record serves as a customizable digital profile for the user, ensuring accurate representation and management of identities within the system.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('persons.create')")
    public Person createPerson(
            @RequestBody Person person,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        person.setId(UUID.randomUUID().toString());
        person.setCreationDate(DateTime.now());
        this.storage.persons().insertObject(person.getId(), person);
        return person;
    }

    @GetMapping("/{personId}")
    @Operation(
summary = "Retrieve a specific person's profile as an admin.",
            operationId = "adminGetPersonProfile",
            description = """
This endpoint provides admins access to a user's detailed profile. The information is strictly for identity management and is accessible only to authorized personnel. Ensure that proper authentication is implemented to safeguard sensitive user data.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('persons.get')")
    public Person getPerson(
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO check for permissions on this person?

        Person person = this.storage.persons().getObject(personId);
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        return person;
    }

    @PutMapping("/{personId}")
    @Operation(
summary = "Admin update of a person's digital profile.",
            operationId = "adminUpdatePersonProfile",
            description = """
This operation allows administrative users to update the profile information of a specified person. It ensures that the digital presence of individuals is accurate and current, facilitating effective identity management. This is crucial for maintaining the integrity of user records.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('persons.update')")
    public Person updatePerson(
            @PathVariable("personId") String personId,
            @RequestBody Person person,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (null == this.storage.persons().getObject(personId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        person.setId(personId);
        person.setCreationDate(DateTime.now());

        this.storage.persons().updateObject(personId, person);
        return person;
    }

    @DeleteMapping("/{personId}")
    @Operation(
summary = "Admin delete a specified person profile",
            operationId = "adminDeletePersonProfile",
            description = """
This endpoint enables an administrator to permanently remove a person's profile from the identity management system. By executing this action, the user's digital presence is erased, ensuring their information is no longer accessible. This operation is essential for upholding data privacy and complying with user requests.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('persons.delete')")
    public void deletePerson(
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        if (null == this.storage.persons().getObject(personId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        this.storage.persons().deleteObject(personId);
    }
}