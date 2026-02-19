package io.klustr.console;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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


    @Bean(name = "PublicEndpoints")
    public WebSecurityCustomizer appSecurity() {
        return (web) -> web
                .ignoring()
                .requestMatchers(
                        "/internal/**",
                        "/ory/kratos/**",
                        "/ory/hydra/**"
                );
    }

    @Bean(name = "StandardSecurityConfiguration")
    public WebSecurityCustomizer standardSecurity() {
        return (web) -> web
                .ignoring()
                .requestMatchers(HttpMethod.GET, "/metrics/**")
                .requestMatchers(HttpMethod.GET, "/actuator*")
                .requestMatchers(HttpMethod.GET, "/actuator/**")

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