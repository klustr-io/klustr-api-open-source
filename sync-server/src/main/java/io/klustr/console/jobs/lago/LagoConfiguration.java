package io.klustr.console.jobs.lago;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.billing.BillingMetric;
import io.klustr.schemas.console.billing.BillingPlan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Configuration
@ConfigurationProperties(prefix = "lago")
public class LagoConfiguration {
    private String url;
    private String apiKey;

    private List<BillingPlan> plans = Lists.newArrayList();

    private List<BillingMetric> metrics = Lists.newArrayList();

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public List<BillingMetric> getMetrics() {
        return this.metrics;
    }

    public void setMetrics(List<BillingMetric> metrics) {
        this.metrics = metrics;
    }

    public List<BillingPlan> getPlans() {
        return plans;
    }

    public void setPlans(List<BillingPlan> plans) {
        this.plans = plans;
    }
}
