package io.klustr.persons;

import io.klustr.AbstractApiService;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.schemas.console.identity.IdentityMetadataPublic;
import io.klustr.schemas.console.identity.IdentityTraits;
import io.klustr.schemas.integrations.ory.Identity;
import io.klustr.schemas.persons.Person;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Gives the digital profile for a user which is displayed
 * to others and them and is a customizable version of thier
 * online presence (vs. verified identity).
 */
@RestController
@Component
@RequestMapping("/identities")
@Tag(name = "Identity APIs", description = "Identity Management APIs")
public class UserService extends AbstractApiService {

    private final PersonStorage storage;
    private final KratosApi kratos;

    public UserService(PersonStorage storage, KratosApi kratos) {
        this.storage = storage;
        this.kratos = kratos;
    }

    @GetMapping("/me")
    @Operation(
summary = "Retrieve the user's digital identity profile.",
            operationId = "getMyIdentity",
            description = """
This endpoint provides access to the user's customizable online presence. It allows visibility into personal information associated with their identity, which is displayed to others. Access is granted based on the context of the authenticated request.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.get')")
    public Identity getIdentity(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return this.kratos.getIdentity(oauth.getName());
    }

    @GetMapping("/me/profile")
    @Operation(
summary = "Retrieve current user's customizable profile information.",
            operationId = "getMyProfile",
            description = """
This endpoint allows users to access their own profile information, which is customizable and reflects their online presence. The returned data is tailored to the user's identity context, ensuring appropriate visibility for the user. This helps users manage their digital profiles effectively.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.get')")
    public ProfileResponse getProfile(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Person person = this.storage.persons().getObject(oauth.getName());
        ProfileResponse p = new ProfileResponse();
        p.metadata_public = person.getMetadataPublic();
        p.traits = person.getTraits();
        return p;
    }

    @GetMapping("/public/{userId}/profile")
    @Operation(
summary = "Retrieve public profile information for a user",
            operationId = "getPublicUserProfile",
            description = """
This endpoint provides access to a user's public profile information, ensuring that only non-sensitive details are shared. This allows others to view the user's customizable online presence while safeguarding their privacy. Use this to understand how users present themselves in a public context.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    @PreAuthorize("hasAuthority('identity.get')")
    public ProfileResponse getProfile(@PathVariable("userId") String userId,
                                      @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Person person = this.storage.persons().getObject(userId);
        ProfileResponse p = new ProfileResponse();
        p.metadata_public = person.getMetadataPublic();
        p.traits = person.getTraits();
        p.traits.setBirthdate(null);
        p.traits.setCitizenship(null);
        p.traits.setGender(null);
        p.traits.setLocale(null);
        p.traits.setEmail(null);
        return p;
    }

    public static class Profile {
        public IdentityTraits traits;
        public IdentityMetadataPublic metadata_public;
    }

    public static class ProfileResponse {
        public IdentityTraits traits;
        public IdentityMetadataPublic metadata_public;
    }
}