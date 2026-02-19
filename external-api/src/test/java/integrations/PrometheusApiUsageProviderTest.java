package integrations;

import com.google.common.collect.Lists;
import io.klustr.compute.Timeframe;
import io.klustr.compute.impl.PrometheusComputeUsageProvider;
import io.klustr.integrations.prometheus.DataPoint;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.schemas.integrations.prometheus.*;
import io.klustr.utils.DateMathParser;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

public class PrometheusApiUsageProviderTest {

    PrometheusApi p = new PrometheusApi("https://metrics.dev.klustr.io");

    @Test
    public void we_can_get_haproxy() {
        PrometheusComputeUsageProvider api = new PrometheusComputeUsageProvider(p);
        List<DataPoint> dataPoints = api.haproxy_server_http_requests_total("individual-crayfish-6b0e3c", Timeframe.Prior(1).Hours());
        System.out.println(Json.toJsonPrettyFormat(dataPoints));
    }

    @Test
    public void we_can_get_grouped() {

        PrometheusMetricResponse json = p.getKongHttpRequestsGroupedByAPI("presidential-quelea-fe3320", "1d", "1m");


        List<PrometheusMetric> filled_metrics = json.getData().getResult().stream().filter(r -> {
            return r.getValues().stream().anyMatch(v -> {
                double d = Double.parseDouble(v.get(1).toString());
                return d > 0;
            });
        }).toList();

        System.out.println(U.toJsonPrettyFormat(filled_metrics));
    }

    @Test
    public void we_can_get_http_metrics() {
        HttpUsageMetrics metrics = p.getCurrentApiGatewayUsage("presidential-quelea-fe3320",
                "1d",
                "1s"
        );
        assertThat(metrics).isNotNull();
        assertThat(metrics.getData()).isNotNull();
        assertThat(metrics.getData().getResult()).isNotEmpty();

        System.out.println(U.toJsonPrettyFormat(metrics));

    }

    @Test
    public void fill_dates() {
        DateTime stop = DateMathParser.parse("NOW/DAY");
        DateTime start = DateMathParser.parse("NOW/DAY-2DAYS");

        List<Integer> range = Lists.newArrayList();
        while (start.isBefore(stop)) {
            range.add(U.toEpochSeconds(start));
            start = start.plusHours(1);
        }

        System.out.println(U.toJsonPrettyFormat(range));
    }

    @Test
    public void we_can_handle_response() {
        List<List<Object>> lists = Lists.newArrayList();
        lists.add(Lists.newArrayList("1", 2));
        HttpUsageMetrics metrics = new HttpUsageMetrics()
                .withData(new HttpUsageMetric()
                        .withResultType("foo")
                        .withResult(Lists.newArrayList(
                                new HttpMetric()
                                        .withMetric(new Metric()
                                                .withService("foo"))
                                        .withValues(lists)
                        )));

        System.out.println(U.toJsonPrettyFormat(metrics));
    }
}
