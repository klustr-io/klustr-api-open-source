package io.klustr.console.jobs.billing.hourly.compute;

import com.lago.openapi.model.LagoCustomerUsageObject;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEvent;
import io.klustr.billing.models.BillingEventForComputeInstanceUsage;
import io.klustr.billing.models.BillingKey;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.integrations.lago.LagoProperties;
import io.klustr.integrations.prometheus.MetricSeries;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.utils.U;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;

public class ComputeHourlyBillingProviderTest {

    PrometheusApi prom = new PrometheusApi("https://metrics.dev.klustr.io");
    private LagoBillingAdapter lago =
            new LagoBillingAdapter(LagoProperties.newBuilder().withUrl("https://lago-api.dev.klustr.io/api/v1")
                    .withApiKey("41347c4a-9708-40e8-89c8-8dc6c101cd7c").build());

    CompositeMeterRegistry metrics = new CompositeMeterRegistry();
    RethinkDbConnectionPool pool = new RethinkDbConnectionPool("rethinkdb.dev.klustr.io", 28015, metrics);
    Storage s = new Storage(new RethinkDbDocumentDatabaseFactory(pool), mock(DbAdapterBroadcasterFactory.class), metrics);


    @Test
    public void we_can_calculate_overall_billing() {

        ComputeHourlyBillingProvider p = new ComputeHourlyBillingProvider(prom);
        Project project = s.projects().getObject("anaconda-buzzard-e95730");
        p.bill(project, new BillingUsageApi() {
            @Override
            public Optional<LagoCustomerUsageObject> getCurrentUsage(BillingKey key) {
                return Optional.empty();
            }

            @Override
            public void recordUsage(String projectId, BillingEvent event) {
                System.out.println(U.toJsonPrettyFormat(event));
                lago.recordUsage(projectId, event);
            }
        });

//        String q = "sum by(project_id, id) ( count_over_time(container_cpu_usage_seconds_total{project!=\"\", org_id!=\"klustr.io\"" +
//                "}[10m]) )";
//        List<MetricSeries> results = p.query(q, "NOW/HOUR-72HOUR", "NOW/HOUR", "10m");
//
//        results.forEach(instance -> {
//            String project_id = instance.getMetric().get("project_id");
//            String id = instance.getMetric().get("id");
//            instance.getPoints().forEach(hour -> {
//                DateTime key = hour.getTime();
//                Integer minutes = ((Double)(hour.getValue() * 0.25)).intValue();
//
//                lago.recordUsage(project_id, new BillingEventForComputeInstanceUsage.Context()
//                        .withInstanceType("m1.small")
//                        .withProjectId(project_id)
//                        .withTotal(minutes)
//                        .withInstanceId(id)
//                                .withTimestamp(key)
//                        .build()
//                );
//
//            });
//        });
    }
}
