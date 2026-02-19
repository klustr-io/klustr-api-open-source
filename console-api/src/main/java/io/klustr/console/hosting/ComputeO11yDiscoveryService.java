package io.klustr.console.hosting;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.Project;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.storage.query.DbQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Will let users configure and expose organization level
 * scanning for metrics and push them to the push gateway
 * at their org level.
 */
@RestController
@Component
@RequestMapping("/console/organization/{orgId}/o11y")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ComputeO11yDiscoveryService {
    private static final Logger log = LoggerFactory.getLogger(ComputeO11yDiscoveryService.class);
    private final Storage storage;

    public ComputeO11yDiscoveryService(Storage storage) {
        this.storage = storage;
    }

    @GetMapping("/endpoints")
    @Operation(
            operationId = "getMetricEndpointsForOrganization", summary = "Will get any configured metric endpoints for an organization.",
            description = """
                    This endpoint enables a user to lookup and get key information about the git project linked to their console project. If the user has activated git on their project this will return the latest information included the git url, and connection options as well as stats.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<MetricEndpoint> getMetricEndpoints(@PathVariable("orgId") String orgId,
                                                   @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        List<MetricEndpoint> endpoints = Lists.newArrayList();

        DbQuery findProjectsByOrg = Db.query("org_id").eq(orgId);
        DocumentResult<Project> result = storage.projects().insecureQuery(findProjectsByOrg, Pagination.all());
        result.docs.forEach(p -> {
            if (p.getO11y() != null && p.getO11y().getEndpoints() != null) {
                p.getO11y().getEndpoints().forEach(e -> {
                    MetricEndpoint metric = new MetricEndpoint(p.getId(), e.getUrl());
                    if (e.getLabels() != null && !e.getLabels().isEmpty()) {
                        e.getLabels().forEach(lbl -> {
                            metric.tags.add(new MetricEndpointTag(lbl.getKey(), lbl.getValue()));
                        });
                    }
                    endpoints.add(metric);
                });
            }
        });

        return endpoints;
    }

    @PostMapping("/health")
    @Operation(
            operationId = "updateO11yHealth", summary = "Will receive status of a push of metrics",
            description = """
                    Enables our proxy metric lib to report its health.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public void updateO11yHealth(@PathVariable("orgId") String orgId,
                                        @RequestBody String json,
                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        // do something with heartbeat
        System.out.println("Heartbeat for Organization: " + json);
    }

    public static class MetricEndpoint {
        public String project_id;
        public String url;
        public Set<MetricEndpointTag> tags = Sets.newHashSet();

        public MetricEndpoint(String project_id, String url) {
            this.project_id = project_id;
            this.url = url;
        }
    }

    public static class MetricEndpointTag {
        public String key;
        public String value;

        public MetricEndpointTag() {

        }

        public MetricEndpointTag(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    @PostMapping("/endpoints/{endpointId}/heartbeat")
    @Operation(
            operationId = "updateEndpointHealth", summary = "If a failure occurs during a scan of an endpoint, it will be reported here.",
            description = """
                    Enables endpoint monitoring so that if an endpoint is dead or not reacable we report back through here.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public void updateEndpointHeartbeat(@PathVariable("orgId") String orgId,
                                                        @PathVariable("endpointId") String endpointId,
                                                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        // do something with heartbeat
        System.out.println("Heartbeat for " + endpointId);
    }
}
