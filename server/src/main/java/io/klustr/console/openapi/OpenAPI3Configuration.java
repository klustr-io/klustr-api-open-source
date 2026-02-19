package io.klustr.console.openapi;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.klustr.spring.OAuthCredentialType;
import org.springframework.context.annotation.Configuration;

@Configuration
/**
 * The main difference between the two is that the client credentials
 * flow relies on application authorization rather than involving the
 * user. Since there is no user authorization,
 * the flow only interacts with the Token endpoint.
 */
@SecurityScheme(name = OAuthCredentialType.USER_TO_SERVICE,
        type = SecuritySchemeType.OAUTH2,
        description = "An OIDC service account that was authenticated.",
        flows = @OAuthFlows(
                clientCredentials = @OAuthFlow(
                        authorizationUrl = "https://secure.dev.klustr.io/hydra/oauth2/auth",
                        tokenUrl = "https://secure.dev.klustr.io/hydra/oauth2/token"
                )
        )
)

@SecurityScheme(name = OAuthCredentialType.SERVICE_TO_SERVICE,
        type = SecuritySchemeType.OAUTH2,
        description = "An OIDC service account that was authenticated.",
        flows = @OAuthFlows(
                authorizationCode = @OAuthFlow(
                        tokenUrl = "https://secure.dev.klustr.io/hydra/oauth2/token"
                )
        )
)

public class OpenAPI3Configuration {
}
