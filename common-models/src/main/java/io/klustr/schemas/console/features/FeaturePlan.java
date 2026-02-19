
package io.klustr.schemas.console.features;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * FeaturePlan
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "name",
    "tier",
    "subscription_prices",
    "package_pricing",
    "volume_pricing"
})
@Generated("jsonschema2pojo")
public class FeaturePlan {

    /**
     * The name for this plan
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this plan")
    private String name;
    @JsonProperty("tier")
    private PlanTier tier;
    /**
     * SubscriptionPrices
     * <p>
     * 
     * 
     */
    @JsonProperty("subscription_prices")
    private List<SubscriptionPrice> subscriptionPrices = new ArrayList<SubscriptionPrice>();
    @JsonProperty("package_pricing")
    private List<PackagePrice> packagePricing = new ArrayList<PackagePrice>();
    @JsonProperty("volume_pricing")
    private List<VolumePrice> volumePricing = new ArrayList<VolumePrice>();

    /**
     * The name for this plan
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this plan
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public FeaturePlan withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("tier")
    public PlanTier getTier() {
        return tier;
    }

    @JsonProperty("tier")
    public void setTier(PlanTier tier) {
        this.tier = tier;
    }

    public FeaturePlan withTier(PlanTier tier) {
        this.tier = tier;
        return this;
    }

    /**
     * SubscriptionPrices
     * <p>
     * 
     * 
     */
    @JsonProperty("subscription_prices")
    public List<SubscriptionPrice> getSubscriptionPrices() {
        return subscriptionPrices;
    }

    /**
     * SubscriptionPrices
     * <p>
     * 
     * 
     */
    @JsonProperty("subscription_prices")
    public void setSubscriptionPrices(List<SubscriptionPrice> subscriptionPrices) {
        this.subscriptionPrices = subscriptionPrices;
    }

    public FeaturePlan withSubscriptionPrices(List<SubscriptionPrice> subscriptionPrices) {
        this.subscriptionPrices = subscriptionPrices;
        return this;
    }

    @JsonProperty("package_pricing")
    public List<PackagePrice> getPackagePricing() {
        return packagePricing;
    }

    @JsonProperty("package_pricing")
    public void setPackagePricing(List<PackagePrice> packagePricing) {
        this.packagePricing = packagePricing;
    }

    public FeaturePlan withPackagePricing(List<PackagePrice> packagePricing) {
        this.packagePricing = packagePricing;
        return this;
    }

    @JsonProperty("volume_pricing")
    public List<VolumePrice> getVolumePricing() {
        return volumePricing;
    }

    @JsonProperty("volume_pricing")
    public void setVolumePricing(List<VolumePrice> volumePricing) {
        this.volumePricing = volumePricing;
    }

    public FeaturePlan withVolumePricing(List<VolumePrice> volumePricing) {
        this.volumePricing = volumePricing;
        return this;
    }

}
