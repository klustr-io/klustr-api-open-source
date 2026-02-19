package io.klustr.console.jobs.billing.hourly.compute;

import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEventForComputeInstanceUsage;
import io.klustr.compute.impl.PrometheusComputeUsageProvider;
import io.klustr.console.jobs.billing.HourlyBillableResource;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.prometheus.MetricSeries;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ComputeHourlyBillingProvider implements HourlyBillableResource {

    private static final Logger log = LoggerFactory.getLogger(ComputeHourlyBillingProvider.class);
    private final PrometheusApi usage;

    public ComputeHourlyBillingProvider(PrometheusApi usage) {
        this.usage = usage;
    }

    @Override
    public void bill(Project project, BillingUsageApi usageApi) {
        String q = "sum by(project_id, id) ( count_over_time(container_cpu_usage_seconds_total{project=\"" + project.getId() + "\", org_id!=\"klustr.io\"" +
                "}[10m]) )";
        List<MetricSeries> results = this.usage.query(q, "NOW/HOUR-7DAYS", "NOW/HOUR", "10m");

        results.forEach(instance -> {
            String project_id = instance.getMetric().get("project_id");
            String id = instance.getMetric().get("id");

            if (!project.getId().equalsIgnoreCase(project_id)) {
                return;
            }

            instance.getPoints().forEach(hour -> {
                DateTime key = hour.getTime();
                int minutes = ((Double)(hour.getValue() * 0.25)).intValue();

                // jitter in uptime and just round up
                if (minutes >= 7) {
                    minutes = 10;
                }

                BillingEventForComputeInstanceUsage event = new BillingEventForComputeInstanceUsage.Context()
                        .withInstanceType("m1.small")
                        .withProjectId(project_id)
                        .withTotal(minutes)
                        .withInstanceId(id)
                        .withTimestamp(key)
                        .build();

                usageApi.recordUsage(project_id, event);

            });
        });
    }
}
