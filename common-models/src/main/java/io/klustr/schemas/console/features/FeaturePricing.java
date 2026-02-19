
package io.klustr.schemas.console.features;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * FeaturePricing
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "plans"
})
@Generated("jsonschema2pojo")
public class FeaturePricing {

    /**
     * FeaturePlans
     * <p>
     * 
     * 
     */
    @JsonProperty("plans")
    private List<FeaturePlan> plans = new ArrayList<FeaturePlan>();

    /**
     * FeaturePlans
     * <p>
     * 
     * 
     */
    @JsonProperty("plans")
    public List<FeaturePlan> getPlans() {
        return plans;
    }

    /**
     * FeaturePlans
     * <p>
     * 
     * 
     */
    @JsonProperty("plans")
    public void setPlans(List<FeaturePlan> plans) {
        this.plans = plans;
    }

    public FeaturePricing withPlans(List<FeaturePlan> plans) {
        this.plans = plans;
        return this;
    }

}
