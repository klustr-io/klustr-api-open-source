package io.klustr.console.admin;

import com.nimbusds.jose.util.Base64;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.schemas.integrations.ory.OAuthTokenResponse;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.spring.OAuthCredentialType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

/**
 * Wrapper around our request to make it easier to get a token
 * and used by camunda and others who cant send HTTP form posts.
 */
@RestController
@Component
@RequestMapping("/internal/oauth2")
@Tag(name = "OAuth APIs", description = "Enables getting an authentication token for use with API access.")
public class OAuthTokenService {

    private final HydraApi api;

    public OAuthTokenService(HydraApi api) {
        this.api = api;
    }

    @GetMapping("/introspect")
    @Operation(
summary = "Introspect and validate the provided OAuth token",
            operationId = "introspectToken",
            description = """
This endpoint inspects the provided OAuth token to determine its validity. It generates the necessary API responses based on the token's status. This operation is crucial for ensuring secure access to resources and services.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.SERVICE_TO_SERVICE)
)
    public OAuthTokenResponse introspect(@RequestHeader("Authorization") String authorization) {
        return null;
    }

    // /internal/oauth2/token
    @PostMapping("/token")
    @Operation(
operationId = "getOauthToken",
            summary = "Retrieve OAuth2 token for API access.",
            description = """
This endpoint allows clients to obtain an OAuth2 token through a credential exchange. It is essential for authenticating API requests securely. The token can be used for accessing protected resources across various services.
""",
            security = @SecurityRequirement(name = "BASIC")
)
    public HydraApi.OryTokenResponse token(@RequestHeader("Authorization") String authorization) {
        if (StringUtils.isBlank(authorization)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Basic Authentication Header Expected"
            );
        }
        if (authorization.indexOf("Basic") < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Basic Authentication Header Expected"
            );
        }
        String base64 = authorization.split("Basic")[1].trim();
        String auth = Base64.from(base64).decodeToString();
        String[] pair = auth.split(":");
        String clientId = pair[0];
        String password = pair[1];
        return this.api.auth(clientId, password, new ArrayList<String>());
    }
}
