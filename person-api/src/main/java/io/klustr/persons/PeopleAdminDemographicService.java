package io.klustr.persons;

import com.webcohesion.enunciate.metadata.swagger.OperationId;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.persons.demographics.DemographicProvider;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.persons.demographics.Demographic;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.spring.OAuthCredentialType;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Administrative interface for getting and updating demographic
 * information about a person.
 */
@RestController
@Component
@RequestMapping("/identities/admin/persons")
@Tag(name = "Person Admin APIs", description = "Identity Management APIs")
public class PeopleAdminDemographicService {

    private final RethinkDbPersonRepository repo;

    private final KratosApi kratos;

    private final DemographicProvider demographics;

    public PeopleAdminDemographicService(RethinkDbPersonRepository repo,
                                         KratosApi kratos,
                                         DemographicProvider demographics) {
        this.repo = repo;
        this.kratos = kratos;
        this.demographics = demographics;
    }


    @GetMapping("/{personId}/demographics")
    @OperationId("Demographics")
    @Operation(
summary = "Retrieve demographic information for a specific person.",
            operationId = "adminGetDemographicInfo",
            description = """
This endpoint allows administrators to retrieve detailed demographic information for a person using their unique ID. It is designed for internal use to manage user data securely. Ensure that proper authorization is enforced to protect sensitive information.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public Demographic getDemographics(@PathVariable("personId") String personId) {
        Optional<Person> person = this.repo.tryGetPersonById(personId);
        if (person.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        return demographics.getDemographics(person.get().getId());
    }
}
