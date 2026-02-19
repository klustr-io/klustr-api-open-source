package io.klustr.persons;

import io.klustr.schemas.persons.households.Household;
import io.klustr.schemas.persons.households.HouseholdMember;
import io.klustr.storage.DocumentResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.Pagination;
import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

/**
 * Gives the digital profile for a user which is displayed
 * to others and them and is a customizable version of thier
 * online presence (vs. verified identity).
 */
@RestController
@Component
@RequestMapping("/identities/admin/households/")
@Tag(name = "Household Admin APIs", description = "Identity Management APIs")
public class HouseholdAdminService extends AbstractApiService {

    private final PersonStorage storage;

    public HouseholdAdminService(PersonStorage storage) {
        this.storage = storage;
    }

    @GetMapping
    @Operation(
summary = "List registered households for administrative users.",
            operationId = "adminListHouseholds",
            description = """
This endpoint retrieves a paginated list of households registered in the system. It is designed for administrative users to manage and view household information efficiently. Use the 'limit' and 'start' parameters to control the number of households returned.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.list')")
    public DocumentResult<Household> listPeople(
            @RequestParam(value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(value = "start", defaultValue = "0") Integer start,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        return this.storage.households().insecureQuery(new Pagination().withStart(start).withLimit(limit));
    }

    @PostMapping
    @Operation(
summary = "Admin creates a new household record.",
            operationId = "adminCreateHousehold",
            description = """
This endpoint enables an admin to create a new household record. The created household will be visible to other users based on their permissions. This operation is crucial for effectively managing household identities within the system.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.create')")
    public Household create(
            @RequestBody Household household,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        household.setId(UUID.randomUUID().toString());
        household.setCreationDate(DateTime.now());
        this.storage.households().insertObject(household.getId(), household);
        return household;
    }

    @PutMapping("/{householdId}")
    @Operation(
summary = "Admin update of household record details",
            operationId = "adminUpdateHousehold",
            description = """
This operation allows administrative users to update household records. It is crucial for maintaining accurate and complete household information, which supports effective management and user assistance. Ensure that all provided data is verified to uphold data integrity.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.update')")
    public Household update(
            @PathVariable("householdId") String householdId,
            @RequestBody Household household,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        if (null == this.storage.households().getObject(householdId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        household.setId(householdId);
        household.setCreationDate(DateTime.now());

        this.storage.households().updateObject(household.getId(), household);
        return household;
    }

    @PutMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Admin update of household member information",
            operationId = "adminUpdateHouseholdMember",
            description = """
This operation allows administrative users to update the details of a specific member within a household. Ensuring that member data is accurate is essential for maintaining the integrity of household profiles. This helps enhance the overall user experience and promotes trust among household members.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.members.update')")
    public Household updateMembers(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = this.storage.households().getObject(householdId);
        if (null == household) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        household.setModifiedDate(DateTime.now());
        household.getMembers().add(new HouseholdMember().withPersonId(personId).withStartDate(DateTime.now()));

        this.storage.households().updateObject(household.getId(), household);
        return household;
    }

    @DeleteMapping("/{householdId}/members/{personId}")
    @Operation(
summary = "Admin removes a member from a household.",
            operationId = "adminRemoveHouseholdMember",
            description = """
This endpoint allows an administrator to permanently remove a specified member from a household. This action alters the household's composition and is essential for maintaining accurate membership records. Use this to effectively manage household memberships.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.members.update')")
    public Household removeMembers(
            @PathVariable("householdId") String householdId,
            @PathVariable("personId") String personId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Household household = this.storage.households().getObject(householdId);
        if (null == household) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }

        household.setModifiedDate(DateTime.now());
        Optional<HouseholdMember> match = household.getMembers().stream().filter(x -> x.getPersonId().equalsIgnoreCase(personId)).findFirst();
        if (match.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        match.get().setExpirationDate(DateTime.now());

        this.storage.households().updateObject(household.getId(), household);
        return household;
    }

    @DeleteMapping("/{householdId}")
    @Operation(
summary = "Admin delete a specified household by ID",
            operationId = "adminDeleteHousehold",
            description = """
This endpoint allows an admin to permanently delete a household using its ID. This action ensures that all associated data is removed from the system, which is essential for maintaining accurate records and effective identity management.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('households.delete')")
    public void delete(
            @PathVariable("householdId") String householdId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        if (null == this.storage.households().getObject(householdId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND
            );
        }
        this.storage.households().deleteObject(householdId);
    }
}