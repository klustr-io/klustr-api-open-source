package io.klustr.console;

import io.klustr.permissions.PermissionProvider;
import io.klustr.spring.CustomMethodSecurityExpressionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/**
 * Given the convention of '/internal' we apply some stanard access rules '/admin'
 * with the expectation that these routes can be access directly or if needed would
 * be wrapped in a API gateway to check general ACL access.
 * <p>
 * It is assumed there is no user context, or OIDC authorization needed.
 * <p>
 * /internal should have IP restrictions, or other connection based restrictions when hosted by API
 * /admin should have IP restrictions, or other connection based restrictions when hosted by API
 */
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class StandardSecurityConfiguration {

    private final PermissionProvider permissionProvider;

    public StandardSecurityConfiguration(PermissionProvider permissionProvider) {
        this.permissionProvider = permissionProvider;
    }

    @Bean
    public MethodSecurityExpressionHandler methodSecurityExpressionHandler() {
        return new CustomMethodSecurityExpressionHandler(permissionProvider);
    }

    @Bean(name = "PublicEndpoints")
    public WebSecurityCustomizer appSecurity() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                        "/console/projects/ids",    // generates random names (no auth provided)
                        "/admin/sync/**",
                        "/oauth2/token",
                        "/ekyc/veriff",
                        "/ekyc/veriff/**",
                        "/ekyc/veriff/**",
                        "/ory/kratos/**",
                        "/ory/hydra/**",
                        "/api**",
                        "/internal/**",
                        "/api/**",
                        "/prometheus/**"
                )
                .requestMatchers(HttpMethod.GET,
                        "/consent/scopes",
                        "/consent/scopes/**",
                        "/directory/public/**",
                        "/prometheus/**"
                );
    }

    @Bean(name = "StandardSecurityConfiguration")
    public WebSecurityCustomizer standardSecurity() {
        return (web) -> web
                .ignoring()

                // metrics overall
                .requestMatchers(HttpMethod.GET, "/metrics/**")
                .requestMatchers(HttpMethod.GET, "/metrics**")

                // metrics overall
                .requestMatchers(HttpMethod.GET, "/actuator*")
                .requestMatchers(HttpMethod.GET, "/actuator/**")

                // health metrics for spring boot
                .requestMatchers(HttpMethod.GET, "/health*")
                .requestMatchers(HttpMethod.GET, "/health/**")

                .requestMatchers(HttpMethod.GET, "/i18n/**")
                .requestMatchers(HttpMethod.GET, "/error/**")

                // api docs
                .requestMatchers(HttpMethod.GET, "/swagger-ui/**")
                .requestMatchers(HttpMethod.GET, "/api-docs/**")
                .requestMatchers(HttpMethod.GET, "/api-docs.yaml/**")
                .requestMatchers(HttpMethod.GET, "/api-docs.json/**")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs/**")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.yaml")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.yaml/**")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.json/**")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.yml")
                .requestMatchers(HttpMethod.GET, "/v3/api-docs.json")

                .requestMatchers(HttpMethod.POST, "/swagger/*")
                .requestMatchers(HttpMethod.POST, "/api-docs/*")
                .requestMatchers(HttpMethod.POST, "/error/*")
                .requestMatchers(HttpMethod.PUT, "/swagger/*")
                .requestMatchers(HttpMethod.PUT, "/api-docs/*")
                .requestMatchers(HttpMethod.PUT, "/error/*")
                .requestMatchers(HttpMethod.DELETE, "/swagger/*")
                .requestMatchers(HttpMethod.DELETE, "/api-docs/*")
                .requestMatchers(HttpMethod.DELETE, "/error/*");
    }

}