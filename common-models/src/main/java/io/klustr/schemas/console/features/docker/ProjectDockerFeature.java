
package io.klustr.schemas.console.features.docker;

import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.klustr.schemas.console.features.AbstractProjectFeature;
import io.klustr.schemas.console.features.Branding;
import io.klustr.schemas.console.features.Category;
import io.klustr.schemas.console.features.CompanyReference;
import io.klustr.schemas.console.features.FeatureDocumentation;
import io.klustr.schemas.console.features.FeaturePricing;
import io.klustr.schemas.console.features.FeatureStatus;
import io.klustr.schemas.console.features.core.NetworkingSpec;
import io.klustr.schemas.console.features.core.ObservabilitySpec;
import io.klustr.schemas.console.features.core.ProjectFeatureConstraints;
import io.klustr.schemas.console.features.core.ProjectFeatureUISpec;
import io.klustr.schemas.console.features.core.ProvisioningSpec;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;


/**
 * ProjectDockerFeature
 * <p>
 * Catalog feature representing a generic Docker-based workload deployed via the platform provisioning system.
 * 
 */
@JsonTypeName("docker")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "provisioning",
    "networking",
    "observability"
})
@Generated("jsonschema2pojo")
public class ProjectDockerFeature
    extends AbstractProjectFeature
{

    /**
     * ProvisioningSpec
     * <p>
     * Provisioning hints for deploying the feature.
     * (Required)
     * 
     */
    @JsonProperty("provisioning")
    @JsonPropertyDescription("Provisioning hints for deploying the feature.")
    private ProvisioningSpec provisioning;
    /**
     * NetworkingSpec
     * <p>
     * Networking and exposure configuration.
     * 
     */
    @JsonProperty("networking")
    @JsonPropertyDescription("Networking and exposure configuration.")
    private NetworkingSpec networking;
    /**
     * ObservabilitySpec
     * <p>
     * Observability configuration for metrics and health checks.
     * 
     */
    @JsonProperty("observability")
    @JsonPropertyDescription("Observability configuration for metrics and health checks.")
    private ObservabilitySpec observability;

    /**
     * ProvisioningSpec
     * <p>
     * Provisioning hints for deploying the feature.
     * (Required)
     * 
     */
    @JsonProperty("provisioning")
    public ProvisioningSpec getProvisioning() {
        return provisioning;
    }

    /**
     * ProvisioningSpec
     * <p>
     * Provisioning hints for deploying the feature.
     * (Required)
     * 
     */
    @JsonProperty("provisioning")
    public void setProvisioning(ProvisioningSpec provisioning) {
        this.provisioning = provisioning;
    }

    public ProjectDockerFeature withProvisioning(ProvisioningSpec provisioning) {
        this.provisioning = provisioning;
        return this;
    }

    /**
     * NetworkingSpec
     * <p>
     * Networking and exposure configuration.
     * 
     */
    @JsonProperty("networking")
    public NetworkingSpec getNetworking() {
        return networking;
    }

    /**
     * NetworkingSpec
     * <p>
     * Networking and exposure configuration.
     * 
     */
    @JsonProperty("networking")
    public void setNetworking(NetworkingSpec networking) {
        this.networking = networking;
    }

    public ProjectDockerFeature withNetworking(NetworkingSpec networking) {
        this.networking = networking;
        return this;
    }

    /**
     * ObservabilitySpec
     * <p>
     * Observability configuration for metrics and health checks.
     * 
     */
    @JsonProperty("observability")
    public ObservabilitySpec getObservability() {
        return observability;
    }

    /**
     * ObservabilitySpec
     * <p>
     * Observability configuration for metrics and health checks.
     * 
     */
    @JsonProperty("observability")
    public void setObservability(ObservabilitySpec observability) {
        this.observability = observability;
    }

    public ProjectDockerFeature withObservability(ObservabilitySpec observability) {
        this.observability = observability;
        return this;
    }

    @Override
    public ProjectDockerFeature withId(java.lang.String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProjectDockerFeature withName(java.lang.String name) {
        super.withName(name);
        return this;
    }

    @Override
    public ProjectDockerFeature withCategory(Category category) {
        super.withCategory(category);
        return this;
    }

    @Override
    public ProjectDockerFeature withTags(List<java.lang.String> tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public ProjectDockerFeature withStatus(FeatureStatus status) {
        super.withStatus(status);
        return this;
    }

    @Override
    public ProjectDockerFeature withDescription(java.lang.String description) {
        super.withDescription(description);
        return this;
    }

    @Override
    public ProjectDockerFeature withDocumentationUrl(java.lang.String documentationUrl) {
        super.withDocumentationUrl(documentationUrl);
        return this;
    }

    @Override
    public ProjectDockerFeature withDocumentation(FeatureDocumentation documentation) {
        super.withDocumentation(documentation);
        return this;
    }

    @Override
    public ProjectDockerFeature withPricing(FeaturePricing pricing) {
        super.withPricing(pricing);
        return this;
    }

    @Override
    public ProjectDockerFeature withBranding(Branding branding) {
        super.withBranding(branding);
        return this;
    }

    @Override
    public ProjectDockerFeature withCompany(CompanyReference company) {
        super.withCompany(company);
        return this;
    }

    @Override
    public ProjectDockerFeature withUi(ProjectFeatureUISpec ui) {
        super.withUi(ui);
        return this;
    }

    @Override
    public ProjectDockerFeature withConstraints(ProjectFeatureConstraints constraints) {
        super.withConstraints(constraints);
        return this;
    }

    @Override
    public ProjectDockerFeature withConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        super.withConfiguration(configuration);
        return this;
    }

    @Override
    public ProjectDockerFeature withMetadata(Map<String, String> metadata) {
        super.withMetadata(metadata);
        return this;
    }

}
