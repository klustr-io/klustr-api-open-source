package io.klustr.console.usage;

import com.google.common.collect.Lists;
import io.klustr.persons.repositories.ProjectUserTrackingRepository;
import io.klustr.schemas.integrations.prometheus.PrometheusMetric;
import io.klustr.schemas.integrations.prometheus.PrometheusMetricResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.schemas.persons.Person;
import io.klustr.schemas.console.stats.ProjectStats;
import io.klustr.schemas.console.stats.ProjectUserReference;
import io.klustr.schemas.console.stats.ProjectUsers;
import io.klustr.console.security.ProjectSecurityPolicy;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.spring.OAuthCredentialType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Enaables a service developer or console to get information
 * and metadata around a projects key statistics such as number
 * of users and user lists.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Billing & Usage APIs", description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectMetricsService {

    private static final Logger log = LoggerFactory.getLogger(ProjectMetricsService.class);

    private final ProjectUserTrackingRepository login;

    private final RethinkDbPersonRepository persons;

    private final PrometheusApi usage;

    private final ProjectSecurityPolicy policy;

    public ProjectMetricsService(ProjectUserTrackingRepository login,
                                 ProjectSecurityPolicy policy,
                                 RethinkDbPersonRepository persons,
                                 PrometheusApi usage) {
        this.login = login;
        this.persons = persons;
        this.policy = policy;
        this.usage = usage;
    }

    @GetMapping("/{projectId}/usage/http")
    @Operation(
description = """
This endpoint provides detailed statistics on the HTTP usage of a specified project. It includes metrics such as the number of users and their activity over a defined time interval. This information is essential for monitoring project performance and user engagement.
""",
            operationId = "getProjectHttpUsage",
            summary = "Retrieve HTTP usage metrics for a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public PrometheusMetricResponse getHttpUsage(@PathVariable("projectId") String projectId,
                                                 @RequestParam(value = "start", defaultValue = "10m") String start,
                                                 @RequestParam(value = "interval", defaultValue = "10s") String interval,
                                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        return this.usage.getKongHttpRequestMetric(projectId, start, interval);
    }

    @GetMapping("/{projectId}/usage/apis")
    @Operation(
description = """
This endpoint provides detailed API usage metrics for a specified project. Users can obtain statistics such as the number of API calls and user activity over a defined time interval. It is designed to assist developers in monitoring and optimizing their project's API consumption.
""",
            operationId = "getProjectApiUsage",
            summary = "Retrieve API usage statistics for a project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public PrometheusMetricResponse getApiUsage(@PathVariable("projectId") String projectId,
                                                @RequestParam(value = "start", defaultValue = "10m") String start,
                                                @RequestParam(value = "interval", defaultValue = "10s") String interval,
                                                @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        PrometheusMetricResponse json = this.usage.getKongHttpRequestsGroupedByAPI(projectId, start, interval);
        List<PrometheusMetric> filled_metrics = json.getData().getResult().stream().filter(r -> {
            return r.getValues().stream().anyMatch(v -> {
                double d = Double.parseDouble(v.get(1).toString());
                return d > 0;
            });
        }).toList();
        json.getData().setResult(filled_metrics);
        return json;
    }

    /**
     * Returns the current project statistics
     *
     * @param projectId The current projects billing information
     * @return The billing for the current period.
     */
    @GetMapping("/{projectId}/usage/stats")
    @Operation(
description = """
This endpoint returns key statistics for the specified project, including user counts and detailed user lists. It is designed for service developers and console users to gain insights into project usage. Access is secured and requires appropriate authentication.
""",
            operationId = "getProjectStatistics",
            summary = "Retrieve project user statistics and metrics",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ProjectStats getProjectStatistics(@PathVariable("projectId") String projectId,
                                             @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<ProjectStats> stats = this.login.getStats(projectId);
        if (stats.isEmpty()) {
            return new ProjectStats()
                    .withProjectId(projectId)
                    .withUniqueUsers(0)
                    .withId(projectId);
        }
        return stats.get();
    }

    /**
     * Returns the current project user stats.
     *
     * @param projectId The current projects billing information
     * @return The billing for the current period.
     */
    @GetMapping("/{projectId}/usage/users")
    @Operation(
description = """
This endpoint provides detailed statistics about the users associated with the specified project. It includes metrics such as the total number of users and their respective lists. This information is crucial for understanding user engagement and project usage.
""",
            operationId = "getProjectUserStatistics",
            summary = "Retrieve user statistics for the current project",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
)
    public ProjectUsers getProjectUsers(@PathVariable("projectId") String projectId,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Optional<ProjectUsers> users = this.login.getUsers(projectId);
        if (users.isEmpty()) {
            return new ProjectUsers()
                    .withProjectId(projectId)
                    .withUsers(Lists.newArrayList())
                    .withId(projectId);
        }

        users.get().getUsers().forEach(user -> {
            String id = user.getId();
            Optional<Person> person = this.persons.tryGetPersonById(id);

            if (person.isEmpty()) return;

            user.withMetadataPublic(person.get().getMetadataPublic())
                    .withTraits(person.get().getTraits());
        });

        users.get().getUsers().sort(new Comparator<ProjectUserReference>() {
            @Override
            public int compare(ProjectUserReference o1, ProjectUserReference o2) {
                return o2.getLastAccess().compareTo(o1.getLastAccess());
            }
        });


        return users.get();
    }


}
