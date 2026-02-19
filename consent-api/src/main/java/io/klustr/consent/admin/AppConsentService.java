package io.klustr.consent.admin;

import io.klustr.consent.UserConsentProvider;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.consent.responses.UserConsent360ResponseBody;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.projects.Project;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * Handles when an external app would like to get the users consent using
 * a client credential or a user credential and will only return the consent
 * for that app and the flattened hierarchy. This is read only as apps
 * should never be able to set or revoke consent directly.
 */
//@RestController
//@Component
//@RequestMapping("/consent")
//@Tag(name = "Consent APIs", description = "Provides access to consent and privacy operations.")
public class AppConsentService {
    private final UserConsentProvider provider;
    private final Storage storage;

    public AppConsentService(            Storage storage,
                                         UserConsentProvider provider,
                                         UserConsentRepository userConsent) {
        this.provider = provider;
        this.storage = storage;

    }

    @GetMapping("/{userId}/{appId}")
    @Operation(
operationId = "getUserAppConsent",
            summary = "Retrieve consent for a user and app.",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE),
            description = """
This endpoint retrieves the consent information for a specified user associated with a particular app. The requesting client must possess valid credentials linked to the project in question. Ensure that the appropriate permissions are granted to access this sensitive data.
"""
)
    public UserConsent360ResponseBody getAllUserConsent(@PathVariable("userId") String userId,
                                                        @PathVariable("appId") String appId,
                                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        String projectId = PrincipleUtils.tryGetProjectId(oauth);
        if (StringUtils.isBlank(projectId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Project Not Found.");
        }
        String clientId = PrincipleUtils.tryGetClientId(oauth);
        if (StringUtils.isBlank(clientId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Client Not Found.");
        }

        Optional<Project> project = this.storage.projects().tryGetObject(projectId);
        if (project.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Project Not Found.");
        }

        if (project.get().getApp() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "App Not Found.");
        }

        if (!project.get().getApp().getId().equalsIgnoreCase(appId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Project linked to appId does not match.");
        }

        // TODO pairwise identifier

        return this.provider.get360ConsentByAppId(userId, appId);
    }
}
