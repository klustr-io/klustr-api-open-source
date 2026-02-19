package io.klustr.console.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Configuration
@Component
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi all() {
        return GroupedOpenApi.builder().group("APIs")
                .addOpenApiCustomizer(new OpenApiCustomizer() {
                    @Override
                    public void customise(OpenAPI openApi) {
                        SecurityRequirement apiKey = new SecurityRequirement()
                                .addList("api-token");
                        if (openApi.getSecurity() == null) {
                            openApi.setSecurity(new ArrayList<>());
                        }
                        openApi.getSecurity().add(apiKey);
                    }
                })
                .build();
    }
}