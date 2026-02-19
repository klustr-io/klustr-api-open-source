package io.klustr.persons;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.demographics.DemographicProvider;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.demographics.Demographic;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.spring.OAuthCredentialType;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Provides information about people and persons.
 */
@RestController
@Component
@RequestMapping("/identities/me/demographics")
@Tag(name = "Identity APIs", description = "Identity Management APIs")
public class PeopleDemographicsService extends AbstractApiService {

    private final RethinkDbPersonRepository repo;

    private final KratosApi kratos;

    private final DemographicProvider demographics;

    public PeopleDemographicsService(RethinkDbPersonRepository repo, KratosApi kratos, DemographicProvider demographics) {
        this.repo = repo;
        this.kratos = kratos;
        this.demographics = demographics;
    }

    /**
     * Returns the person information given the current context
     *
     * @param oauth The context of the user to get the current user for
     * @return Returns the person.
     */
    @GetMapping
    @Operation(
summary = "Retrieve current user's demographic profile information",
            operationId = "getMyDemographics",
            description = """
This endpoint allows authenticated users to access their own demographic profile details. The information is private and securely retrieved, ensuring that users can only view their own data. This promotes user privacy and data protection.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public Demographic getDemographics(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Optional<Person> person = this.repo.tryGetPersonById(oauth.getName());
        if (person.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return demographics.getDemographics(person.get().getId());
    }

}