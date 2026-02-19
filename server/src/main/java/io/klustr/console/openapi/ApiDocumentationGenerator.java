package io.klustr.console.openapi;

import com.google.common.collect.Lists;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.klustr.utils.Yaml;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.SpringDocCustomizers;
import org.springdoc.core.filters.GlobalOpenApiMethodFilter;
import org.springdoc.core.models.GroupedOpenApi;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.SpringDocProviders;
import org.springdoc.core.service.AbstractRequestService;
import org.springdoc.core.service.GenericResponseService;
import org.springdoc.core.service.OpenAPIService;
import org.springdoc.core.service.OperationService;
import org.springdoc.webmvc.api.MultipleOpenApiWebMvcResource;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@Component
@OpenAPIDefinition
public class ApiDocumentationGenerator {

    @Bean
    @Lazy(false)
    MultipleOpenApiWebMvcResource multipleOpenApiResource(
            List<GroupedOpenApi> groupedOpenApis,
            ObjectFactory<OpenAPIService> openAPIServiceObjectFactory,
            AbstractRequestService abstractRequestService,
            GenericResponseService genericResponseService,
            OperationService operationService,
            SpringDocConfigProperties springDocConfigProperties,
            SpringDocProviders springDocProviders,
            SpringDocCustomizers springDocCustomizers
    ) {
        List<GroupedOpenApi> results = Lists.newArrayList();
        String yaml = U.getResourceAsString("openapi.yml", this);
        ApiServiceConfig config = Yaml.fromYaml(yaml, ApiServiceConfig.class);
        config.services.forEach(svc -> {
            results.add(generate(config, svc));
        });
        results.addAll(groupedOpenApis);

        OpenAPIService object = openAPIServiceObjectFactory.getObject();

        return new MultipleOpenApiWebMvcResource(results,
                openAPIServiceObjectFactory,
                abstractRequestService,
                genericResponseService, operationService,
                springDocConfigProperties,
                springDocProviders, springDocCustomizers);
    }

    public enum TokenType {
        SERVER,
        USER
    }

    private GroupedOpenApi generate(ApiServiceConfig host, ApiServiceConfig.ApiService config) {
        return GroupedOpenApi.builder()
                .group(config.group)
                .pathsToMatch(config.match.toArray(new String[0]))
                .pathsToExclude(config.exclude != null ? config.exclude.toArray(new String[0]) : null)
                .displayName(config.title)
                .addOperationCustomizer(ApiDocumentation.oauthExpected())
                .addOperationCustomizer(ApiDocumentation.serviceAccountExpected())
                .addOperationCustomizer(ApiDocumentation.permissionCheck())
                .addOperationCustomizer(ApiDocumentation.sanityCheckAccess())
                .addOpenApiCustomizer(new OpenApiCustomizer() {
                    @Override
                    public void customise(OpenAPI openApi) {
                        // ✏️ Sort tags alphabetically
                        if (openApi.getTags() != null) {
                            openApi.setTags(
                                    openApi.getTags().stream()
                                            .sorted(Comparator.comparing(Tag::getName))
                                            .collect(Collectors.toList())
                            );
                        }

                        // 2. Sort Paths Alphabetically
                        if (openApi.getPaths() != null) {
                            Map<String, PathItem> sortedPathItems = new TreeMap<>(openApi.getPaths());

                            // Optional: Sort HTTP methods alphabetically per path
                            Paths sortedPaths = new Paths();
                            sortedPathItems.forEach((path, pathItem) -> {
                                PathItem sortedItem = new PathItem();

                                // Sort operations by HTTP method name
                                Map<PathItem.HttpMethod, Operation> sortedOps = pathItem.readOperationsMap()
                                        .entrySet().stream()
                                        .sorted(Map.Entry.comparingByKey()) // Sort by GET, POST, etc.
                                        .collect(Collectors.toMap(
                                                Map.Entry::getKey,
                                                Map.Entry::getValue,
                                                (e1, e2) -> e1,
                                                LinkedHashMap::new
                                        ));

                                sortedOps.forEach((method, op) -> sortedItem.operation(method, op));
                                sortedPaths.addPathItem(path, sortedItem);
                            });

                            openApi.setPaths(sortedPaths);
                        }

                        openApi.info(new Info()
                                        .description(
                                                config.description +
                                                        """
                                                                -----
                                                                 """ + CheckAndValidateSecurity.markdown(openApi)
                                        )
                                        .title(config.title)
                                        .version("1.0")
                                )
                                .servers(
                                        host.servers.stream().map(x -> {
                                            return new Server().url(x.url).description(x.name);
                                        }).toList()
                                );
                    }
                })
                .build();
    }

    private ApiResponse create429Response() {
        return new ApiResponse()
                .description("Rate limit exceeded. Too many requests.")
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>()
                                .addProperty("message", new StringSchema().example("API rate limit exceeded"))
                                .addProperty("request_id", new StringSchema().example("9267094f462d3a44272a5928a146a574")
                                ))));
    }


    public ApiResponse create401Response() {
        return new ApiResponse()
                .description("401 Unauthorized - Oauth Failure")
                .content(new Content().addMediaType("application/json",
                        new MediaType().schema(new Schema<>()
                                .addProperty("error", new Schema<>()
                                        .addProperty("code", new StringSchema().example("401"))
                                        .addProperty("message", new StringSchema().example("The resource owner or authorization server denied the request."))
                                ))));
    }
}
