package io.klustr.persons.admin;

import com.webcohesion.enunciate.metadata.rs.ResourceGroup;
import io.klustr.permissions.PermissionProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.AbstractApiService;
import io.klustr.persons.PersonStorage;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;


@RestController
@Component
@RequestMapping("/identities/admin/users")
@ResourceGroup("Identity API")
@Tag(name = "Identity Admin APIs", description = "Identity Management APIs")
public class AdminIdentityService extends AbstractApiService {

    private static final Logger log = LoggerFactory.getLogger(AdminIdentityService.class);

    private final KratosApi kratos;

    private final PermissionProvider permissions;

    private final PersonStorage storage;

    public AdminIdentityService(KratosApi kratos,
                                PersonStorage storage,
                                PermissionProvider permissions) {
        log.info("Admin identity service started.");
        this.kratos = kratos;
        this.permissions = permissions;
        this.storage = storage;
    }

    /**
     * Lists identities currently in the system.
     *
     * @return The identity of the user.
     */
    @GetMapping
    @Operation(
operationId = "adminListIdentities",
            summary = "Retrieve all registered identities for administration.",
            description = """
This endpoint provides administrators with access to a comprehensive list of identities registered in the system. It is designed for internal management purposes, ensuring effective oversight of user identities. Utilize this API to facilitate administrative tasks and maintain proper identity management.
""",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Identity.class))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.list')")
    public List<Identity> listIdentities() {
        return this.kratos.listIdentities();
    }

    /**
     * Gets the identity for the specified user.
     *
     * @param subjectId The ID of the user to retrieve
     * @return The identity of the user.
     */
    @GetMapping("/{subjectId}")
    @Operation(
operationId = "adminGetIdentityBySubjectId",
            summary = "Retrieve user identity by subject ID for admin",
            description = """
This endpoint allows administrators to securely access user identity details associated with a specific subject ID. It ensures that only authorized personnel can retrieve sensitive identity information, facilitating effective user identity management within the admin context.
""",
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Identity.class))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.get')")
    public Identity getIdentity(@PathVariable("subjectId") String subjectId) {
        return this.kratos.getIdentity(subjectId);
    }

    @PostMapping
    @Operation(
operationId = "adminCreateIdentity",
            summary = "Create a new identity in the admin context",
            description = """
This endpoint enables administrators to create a new identity and register personal information. It is designed for identity management, ensuring proper authorization is in place. This operation is essential for maintaining accurate user records and facilitating communication through various channels.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The consent scopes available", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Identity.class))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.create')")
    public Identity create(@RequestBody Identity identity) {
        identity.setId(UUID.randomUUID().toString());
        return this.kratos.create(identity);
    }

    @PutMapping("/{subjectId}")
    @Operation(
operationId = "adminUpdateIdentity",
            summary = "Update an identity in the admin context",
            description = """
This endpoint allows administrators to modify the details of an existing identity. It ensures that only authorized users can make changes to identity information. Proper permissions are required to access this functionality, maintaining the integrity of the identity management system.
""",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The consent scopes available", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Identity.class))
                    })
            },
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.update')")
    public void update(@RequestBody Identity identity) {
        this.kratos.update(identity);
    }


    @DeleteMapping("/{subjectId}")
    @Operation(
operationId = "adminDeleteUserIdentity",
            summary = "Admin action to delete a user identity.",
            description = """
This endpoint allows an admin to delete a user identity. It ensures that the action is performed with the appropriate permissions. This operation is essential for managing user accounts and maintaining security within the identity management system.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.delete')")
    public void deleteIdentity(
            @PathVariable("subjectId") String subjectId,
            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // can't delete self
        if (oauth.getName().equalsIgnoreCase(subjectId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Can not delete self."
            );
        }

        // first remove person
        Person person = this.storage.persons().getObject(subjectId);
        if (person != null) {
            this.storage.persons().deleteObject(person.getId());
        }

        // remove identity
        Identity identity = this.kratos.getIdentity(subjectId);
        if (identity != null) {
            this.kratos.deleteIdentity(subjectId);
        }
    }
}