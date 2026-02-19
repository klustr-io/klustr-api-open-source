package io.klustr.console.jobs.lago;

import io.klustr.consent.ConsentStorage;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.kong.interfaces.GatewayConsumerProvider;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.schemas.console.billing.BillingMetric;
import io.klustr.schemas.console.billing.BillingPlan;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Given the current YAML configuration will parse the {@link LagoConfiguration} and
 * populate lago with the specified key metrics and plans.
 */
@Component
public class SyncBillingPlansAndMetricsToLago {

    private LagoConfiguration config;
    private LagoBillingAdapter billing;

    public SyncBillingPlansAndMetricsToLago(Storage storage,
                                            ConsentStorage consent,
                                            GatewayConsumerProvider kong,
                                            LagoConfiguration config,
                                            LagoBillingAdapter billing) {
        this.config = config;
        this.billing = billing;
        this.init();
    }

    public void init() {
        config.getMetrics().forEach(x -> {
            Optional<BillingMetric> match = this.billing.getMetric(x.getCode());
            if (match.isEmpty()) {
                this.billing.createMetric(x);
            }
        });

        config.getPlans().forEach(plan -> {
            Optional<BillingPlan> match = billing.getPlan(plan.getCode());
            if (match.isEmpty()) {
                billing.createPlan(plan);
            } else {
                billing.updatePlan(plan);
            }
        });
    }
}
