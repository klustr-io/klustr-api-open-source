package io.klustr.integrations.lago;

import com.google.common.collect.Lists;
import com.lago.openapi.api.*;
import com.lago.openapi.client.ApiClient;
import com.lago.openapi.client.ApiException;
import com.lago.openapi.client.ServerConfiguration;
import com.lago.openapi.model.*;
import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingPlanApi;
import io.klustr.billing.BillingSubscriptionApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEvent;
import io.klustr.billing.models.BillingKey;
import io.klustr.billing.models.BillingMetricApi;
import io.klustr.schemas.console.CurrencyCode;
import io.klustr.schemas.console.billing.*;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
@ConditionalOnProperty(name = "lago.url")
public class LagoBillingAdapter implements BillingUsageApi, BillingSubscriptionApi, BillingCustomerApi, BillingMetricApi, BillingPlanApi {

    private static final Logger log = LoggerFactory.getLogger(LagoBillingAdapter.class);

    private final CustomersApi customerApi;
    private final EventsApi eventsApi;
    private final SubscriptionsApi subscriptionsApi;
    private final BillableMetricsApi metricsApi;
    private final PlansApi planApi;

    private final String apiKey;
    private final String url;

    public LagoBillingAdapter(LagoProperties properties) {

        this.apiKey = properties.getApiKey();
        this.url = properties.getUrl();

        ApiClient client = new ApiClient();
        client.setServerVariables(new HashMap<>());
        client.setBearerToken(apiKey);

        ServerConfiguration serverConfiguration = new ServerConfiguration(url, "...", new HashMap<>());
        List<ServerConfiguration> es = Lists.newArrayList(serverConfiguration);
        client.setServers(es);

        customerApi = new CustomersApi(client);
        eventsApi = new EventsApi(client);
        subscriptionsApi = new SubscriptionsApi(client);
        metricsApi = new BillableMetricsApi(client);
        planApi = new PlansApi(client);
    }

    public void health() {
        try {
            this.customerApi.findAllCustomers(0, 10);
        } catch (Exception ex) {
            throw new RuntimeException("Could not connect to lago using url=[" + url + "] and key=[" + apiKey + "].", ex);
        }
    }

    public Optional<com.lago.openapi.model.LagoCustomerUsageObject> getCurrentUsage(BillingKey key)
    {
        try {
            LagoSubscription res = this.subscriptionsApi.findSubscription(key.getProjectId());
            LagoSubscriptionObjectExtended sub = res.getSubscription();


            LagoCustomerUsageObject usage = this.customerApi.findCustomerCurrentUsage(sub.getExternalCustomerId(),
                    sub.getExternalId()).getCustomerUsage();
            return Optional.of(usage);
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty();
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void recordUsage(String projectId, BillingEvent event) {
        try {
            Map<String, LagoEventInputEventPropertiesValue> props = new HashMap<>();
            event.properties.keySet().forEach(x -> {
                props.put(x, new LagoEventInputEventPropertiesValue(event.properties.get(x)));
            });
            LagoEventInputEvent lagoEventInputEvent = new LagoEventInputEvent()
                    .code(event.getLagoMappedBillingCode())
                    .timestamp(new LagoEventInputEventTimestamp(event.timestamp))
                    .externalSubscriptionId(event.external_subscription_id)
                    .transactionId(event.transaction_id)
                    .properties(props);
            LagoEventInput req = new LagoEventInput().event(lagoEventInputEvent);
            com.lago.openapi.model.LagoEvent res = this.eventsApi.createEvent(req);
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                log.warn("Response code 4xx {} returned from API for event {}", ex.getCode(), Json.toJson(event));
                return; // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<BillingCustomer> getCustomer(String code) {
        try {
            com.lago.openapi.model.LagoCustomer customer = this.customerApi.findCustomer(code);
            return Optional.of(LagoConverter.fromCustomer(customer));
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty();
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<BillingCustomer> createCustomer(BillingCustomer obj) {
        try {
            LagoCustomerCreateInput input = LagoConverter.input(obj);
            com.lago.openapi.model.LagoCustomer c = this.customerApi.createCustomer(input);
            return Optional.of( LagoConverter.fromCustomer(c));
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty(); // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<BillingCustomer> updateCustomer(BillingCustomer obj) {
        return createCustomer(obj);
    }

    private LagoSubscriptionCreateInputSubscription map(BillingSubscription subscription) {
        return new LagoSubscriptionCreateInputSubscription()
                        .subscriptionAt( subscription.getSubscriptionAt().toDate())
                        .billingTime(subscription.getBillingTime() != null && subscription.getBillingTime() == BillingSubscription.BillingTime.ANNIVERSARY ?
                                LagoSubscriptionCreateInputSubscription.BillingTimeEnum.ANNIVERSARY :
                                LagoSubscriptionCreateInputSubscription.BillingTimeEnum.CALENDAR)
                        .name(subscription.getName())
                        .externalId(subscription.getExternalId())
                        .externalCustomerId(subscription.getExternalCustomerId())
                        .planCode(subscription.getPlanCode());
    }

    private LagoSubscriptionUpdateInputSubscription update(BillingSubscription subscription) {
        return new LagoSubscriptionUpdateInputSubscription()
                .subscriptionAt(subscription.getSubscriptionAt().toDate())
                .name(subscription.getName());
    }

    @Override
    public void createSubscription(String externalId, BillingSubscription subscription) {
        try {
            LagoSubscriptionCreateInputSubscription map = map(subscription);
            this.subscriptionsApi.createSubscription(new LagoSubscriptionCreateInput().subscription(map)
            );
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return; // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updateSubscriptionName(String externalId, String name) {
        try {
            LagoSubscriptionUpdateInputSubscription update = new LagoSubscriptionUpdateInputSubscription().name(name);

            this.subscriptionsApi.updateSubscription(externalId, new LagoSubscriptionUpdateInput()
                    .status(LagoSubscriptionUpdateInput.StatusEnum.ACTIVE)
                    .subscription(update)
            );
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return; // already updated
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<BillingSubscription> getSubscription(String externalId) {
        try {
            LagoSubscription data = this.subscriptionsApi.findSubscription(externalId);
            LagoSubscriptionObjectExtended doc = data.getSubscription();

            BillingSubscription sub = new BillingSubscription()
                    .withBillingTime(BillingSubscription.BillingTime.CALENDAR)
                    .withName(doc.getName())
                    .withId(doc.getLagoId().toString())
                    .withPlanCode(doc.getPlanCode())
                    .withSubscriptionAt(doc.getSubscriptionAt() != null ? new DateTime(doc.getSubscriptionAt()) : null)
                    .withExternalCustomerId(doc.getExternalCustomerId())
                    .withExternalId(doc.getExternalId())
                    .withName(externalId)
                    .withEndingAt(doc.getEndingAt() != null ? new DateTime(doc.getEndingAt()) : null);

            return Optional.of(sub);
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty(); // already updated
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Optional<BillingMetric> getMetric(String code) {
        try {
            LagoBillableMetric m = this.metricsApi.findBillableMetric(code);
            return Optional.of( new BillingMetric()
                    .withCode(code)
                    .withId(m.getBillableMetric().getLagoId().toString())
                    .withName(m.getBillableMetric().getName())
                    .withDescription(m.getBillableMetric().getDescription())
                    .withRecurring(m.getBillableMetric().getRecurring())
                    .withAggregationType(BillingMetric.AggregationType.valueOf(m.getBillableMetric().getAggregationType().name())));
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty(); // already updated
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public void createMetric(BillingMetric metric) {
        LagoBillableMetricCreateInput input = new LagoBillableMetricCreateInput()
                .billableMetric(new LagoBillableMetricCreateInputBillableMetric()
                        .code(metric.getCode())
                        .name(metric.getName())
                        .description(metric.getDescription())
                        .fieldName(metric.getFieldName())
                        .recurring(metric.getRecurring())
                        .aggregationType(LagoBillableMetricCreateInputBillableMetric.AggregationTypeEnum.fromValue(metric.getAggregationType().value()))
                );
        try {
            LagoBillableMetric m = null;
            try {
             m = this.metricsApi.findBillableMetric(metric.getCode());
            } catch (Exception ex) {
                // not found
            }

            if (m == null) {
                this.metricsApi.createBillableMetric(input);
            } else {
                this.metricsApi.updateBillableMetric(metric.getCode(), new LagoBillableMetricUpdateInput()
                        .billableMetric(new LagoBillableMetricBaseInput()
                                .description(metric.getDescription())
                                .aggregationType(LagoBillableMetricBaseInput.AggregationTypeEnum.fromValue(metric.getAggregationType().value()))
                                .name(metric.getName())
                                .fieldName(metric.getFieldName())
                                .recurring(metric.getRecurring())
                                .code(metric.getCode())
                        )
                );
            }
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return; // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    LagoPlanCreateInputPlan toPlanInput(BillingPlan plan) {
        ArrayList<LagoPlanCreateInputPlanChargesInner> charges = new ArrayList<>();

        if (plan.getCharges() != null) {
            plan.getCharges().forEach(charge -> {

                // package pricing for this
                if (charge.getPackagePricing() != null) {
                    charge.getBillableMetricCodes().forEach(metricCode -> {

                        LagoBillableMetric metric = null;
                        try {
                            metric = metricsApi.findBillableMetric(metricCode);
                        } catch (Exception ex) {
                            return;
                        }

                        charges.add(new LagoPlanCreateInputPlanChargesInner()
                                .chargeModel(LagoPlanCreateInputPlanChargesInner.ChargeModelEnum.PACKAGE)
                                .invoiceDisplayName(metric.getBillableMetric().getName())
                                .billableMetricId(metric.getBillableMetric().getLagoId())
                                .properties(new LagoChargeFilterInputProperties()
                                        .packageSize(charge.getPackagePricing().getPackageSize())
                                        .freeUnits(charge.getPackagePricing().getFreeUnits())
                                        .amount(charge.getPackagePricing().getAmount().toString())
                                )
                        );
                    });
                }

                if (charge.getVolumePricing() != null) {
                    charge.getBillableMetricCodes().forEach(metricCode -> {

                        LagoBillableMetric metric = null;
                        try {
                            metric = metricsApi.findBillableMetric(metricCode);
                        } catch (Exception ex) {
                            return;
                        }

                        LagoPlanCreateInputPlanChargesInner inner = new LagoPlanCreateInputPlanChargesInner()
                                .chargeModel(LagoPlanCreateInputPlanChargesInner.ChargeModelEnum.VOLUME)
                                .invoiceDisplayName(metric.getBillableMetric().getName())
                                .billableMetricId(metric.getBillableMetric().getLagoId())
                                .properties(new LagoChargeFilterInputProperties());

                        charge.getVolumePricing().getRange().forEach(range -> {


                            inner.getProperties().addVolumeRangesItem(
                                    new LagoChargePropertiesVolumeRangesInner()
                                            .perUnitAmount(range.getPerUnitPrice().toString())
                                            .toValue(range.getStop() != null ? range.getStop() : null)
                                            .fromValue(range.getStart())
                                            .flatAmount(range.getFlatFee().toString()));

                        });

                        charges.add(inner);
                    });
                }
            });
        }

        LagoPlanCreateInputPlan input = new LagoPlanCreateInputPlan()
                .code(plan.getCode())
                .name(plan.getName())
                .charges(charges)
                .interval(LagoPlanCreateInputPlan.IntervalEnum.MONTHLY)
                .billChargesMonthly(true)
                .payInAdvance(false)
                .description(plan.getDescription())
                .trialPeriod(plan.getTrialPeriod() != null ? new BigDecimal(plan.getTrialPeriod()) : null)
                .amountCents(plan.getAmountCents())
                .amountCurrency(LagoCurrency.fromValue(plan.getAmountCurrency() != null ? plan.getAmountCurrency().value() : "USD"));

        return input;
    }

    @Override
    public void createPlan(BillingPlan plan) {

        LagoPlanCreateInputPlan input = this.toPlanInput(plan);

        try {
            this.planApi.createPlan(new LagoPlanCreateInput().plan(input));
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return; // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void updatePlan(BillingPlan plan) {
        LagoPlanCreateInputPlan input = this.toPlanInput(plan);

        String json = U.toJson(input);
        LagoPlanUpdateInputPlan update = U.fromJson(json, LagoPlanUpdateInputPlan.class);

        try {
            LagoPlanUpdateInput req = new LagoPlanUpdateInput().plan(update);
            this.planApi.updatePlan(plan.getCode(), req);
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return; // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Optional<BillingPlan> getPlan(String code) {
        try {
            LagoPlan plan = this.planApi.findPlan(code);

            // convert :(
            LagoPlanObject data = plan.getPlan();
            List<BillingPlanUsagePrice> prices = new ArrayList<>();

            // extract usages
            data.getCharges().forEach(lagoCharge -> {
                if (lagoCharge.getChargeModel() == LagoChargeObject.ChargeModelEnum.PACKAGE) {

                    prices.add(new BillingPlanUsagePrice()
                                    .withBillableMetricCodes(Lists.newArrayList(lagoCharge.getBillableMetricCode()))
                                    .withId(lagoCharge.getLagoId().toString())
                                    .withInvoiceDisplayName(lagoCharge.getInvoiceDisplayName())
                            .withPackagePricing(new BillingPlanChargePackagePricing()
                                    .withFreeUnits(lagoCharge.getProperties().getFreeUnits())
                                    .withPackageSize(lagoCharge.getProperties().getPackageSize())
                                    .withAmount(Double.parseDouble(lagoCharge.getProperties().getAmount()))
                            )
                    );

                } else if (lagoCharge.getChargeModel() == LagoChargeObject.ChargeModelEnum.VOLUME) {

                    BillingPlanUsagePrice price = new BillingPlanUsagePrice()
                            .withBillableMetricCodes(Lists.newArrayList(lagoCharge.getBillableMetricCode()))
                            .withId(lagoCharge.getLagoId().toString())
                            .withInvoiceDisplayName(lagoCharge.getInvoiceDisplayName())
                            .withVolumePricing(new BillingPlanChargeVolumePricing().withRange(Lists.newArrayList()));

                    lagoCharge.getProperties().getVolumeRanges().forEach(v -> {
                        price.getVolumePricing().getRange().add(
                                new BillingPlanChargeVolumePricingRange()
                                        .withFlatFee(Double.parseDouble(v.getFlatAmount()))
                                        .withStart(v.getFromValue())
                                        .withStop(v.getToValue())
                                        .withPerUnitPrice(Double.parseDouble(v.getPerUnitAmount()))
                        );
                    });

                    prices.add(price);
                }  else {
                    throw new RuntimeException("Can not support charge model --> " + lagoCharge.getChargeModel() + " in current mapping.");
                }
            });

            BillingPlan result = new BillingPlan()
                    .withCode(data.getCode())
                    .withDescription(data.getDescription())
                    .withId(data.getLagoId().toString())
                    .withName(data.getName())
                    .withAmountCents(data.getAmountCents())
                    .withAmountCurrency(CurrencyCode.fromValue(data.getAmountCurrency().getValue()))
                    .withInterval(BillingPlan.Interval.MONTHLY)
                    .withTrialPeriod(data.getTrialPeriod() != null ? data.getTrialPeriod().intValue() : 0)
                    .withInvoiceDisplayName(data.getInvoiceDisplayName())
                    .withCharges(prices);

            return Optional.of(result);
        } catch (ApiException ex) {
            if (ex.getCode() >= 400 && ex.getCode() < 500) {
                return Optional.empty(); // already exists
            }
            throw new RuntimeException(ex);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
