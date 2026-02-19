package io.klustr.persons;

import io.klustr.schemas.persons.Person;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.spring.OAuthCredentialType;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Gives the digital profile for a user which is displayed
 * to others and them and is a customizable version of thier
 * online presence (vs. verified identity).
 */
@RestController
@Component
@RequestMapping("/identities/me/person")
@Tag(name = "Person APIs", description = "Identity Management APIs")
public class PersonService extends AbstractApiService {

    private final PersonStorage storage;

    public PersonService(PersonStorage storage) {
        this.storage = storage;
    }

    @GetMapping
    @Operation(
summary = "Retrieve your personal profile information.",
            operationId = "getMyPersonProfile",
            description = """
This endpoint allows you to access your personal profile details. It includes customizable information visible to others, helping you manage your online presence. Use this to enhance your digital identity and ensure it reflects your preferences.
""",
            security = {@SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)}
)
    public Person getPerson(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Person person = this.storage.persons().getObject(oauth.getName());
        if (null == person) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        return person;
    }

    @PutMapping
    @Operation(
summary = "Update your personal profile information",
            operationId = "updateMyPersonInfo",
            description = """
This endpoint allows you to modify your personal profile details. The updates will be visible in your digital presence, which is customizable for others to see. Please ensure the accuracy of the information to maintain a reliable online identity.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Person updatePerson(
            @RequestBody Person person,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (null == this.storage.persons().getObject(oauth.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        person.setId(oauth.getName());
        person.setCreationDate(DateTime.now());

        this.storage.persons().updateObject(oauth.getName(), person);
        return person;
    }

    @DeleteMapping
    @Operation(
summary = "Delete your personal profile permanently",
            operationId = "deleteMyPersonProfile",
            description = """
This endpoint allows you to permanently delete your personal profile. This action is irreversible and will remove all associated data from the system. Please ensure you fully understand the implications of this action before proceeding.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public void deletePerson(
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        if (null == this.storage.persons().getObject(oauth.getName())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        this.storage.persons().deleteObject(oauth.getName());
    }
}