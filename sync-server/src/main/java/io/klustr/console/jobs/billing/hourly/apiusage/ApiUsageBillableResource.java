package io.klustr.console.jobs.billing.hourly.apiusage;

import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEvent;
import io.klustr.billing.models.BillingEventForApiUsage;
import io.klustr.console.jobs.billing.HourlyBillableResource;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.prometheus.HttpMetric;
import io.klustr.schemas.integrations.prometheus.HttpUsageMetrics;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Will charge people for usage of APIs using prometheus
 * adapter to get the metrics for the specific project. Requires
 * prometheus to be reporting this. We expect this to be called
 * every minute.
 */
@Component
public class ApiUsageBillableResource implements HourlyBillableResource {

    private static final Logger log = LoggerFactory.getLogger(ApiUsageBillableResource.class);

    private final PrometheusApi prometheus;

    public ApiUsageBillableResource(PrometheusApi prometheus) {
        this.prometheus = prometheus;
    }

    public void bill(Project project, BillingUsageApi usageApi) {
        HttpUsageMetrics project_metrics = this.prometheus.getCurrentApiGatewayUsage(project.getId(), "2m", "1m");
        billApiUsage(usageApi, project.getOrgId(), project.getId(), project_metrics);

    }

    private void billApiUsage( BillingUsageApi usageApi, String orgId, String projectId, HttpUsageMetrics project_metrics) {
        List<HttpMetric> services = project_metrics.getData().getResult();
        for (HttpMetric m :
                services) {
            String api_code = m.getMetric().getService().toLowerCase();
            log.info("API {} pulled for project {}", api_code, projectId);

            m.getValues().forEach(day -> {
                long ts = Long.parseLong(day.get(0).toString());
                DateTime dt = new DateTime(ts * 1000);
                double count = Double.parseDouble(day.get(1).toString());
                if (count <= 0) {
                    log.debug("Organization '{}' for project '{}' is missing usage.",  orgId, projectId);
                    return;
                }

                log.info("Billing Lago org {} for API usage on project {} with {} API with {} calls",
                        orgId, projectId, api_code, count);

                BillingEvent event =
                        BillingEventForApiUsage.builder()
                                .withApiCode(api_code)
                                .withProjectId(projectId)
                                .withTimestamp(dt)
                                .withTotal((int) count)
                                .build();
                try {
                    usageApi.recordUsage(projectId, event);
                } catch (Exception ex) {
                    // already played
                    log.warn("Unable to record Billing Lago API usage on project {} with {} API with {} calls",
                             projectId, api_code, count, ex);
                }
            });
        }
    }
}
