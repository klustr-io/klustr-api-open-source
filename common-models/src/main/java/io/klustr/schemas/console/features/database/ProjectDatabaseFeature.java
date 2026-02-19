
package io.klustr.schemas.console.features.database;

import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
 * ProjectDatabaseFeature
 * <p>
 * Base catalog definition for database features.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "database",
    "provisioning",
    "networking",
    "observability"
})
@Generated("jsonschema2pojo")
public class ProjectDatabaseFeature
    extends AbstractProjectFeature
{

    /**
     * DatabaseSpec
     * <p>
     * Generic database engine specification.
     * (Required)
     * 
     */
    @JsonProperty("database")
    @JsonPropertyDescription("Generic database engine specification.")
    private DatabaseSpec database;
    /**
     * ProvisioningSpec
     * <p>
     * Provisioning hints for deploying the feature.
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
     * DatabaseSpec
     * <p>
     * Generic database engine specification.
     * (Required)
     * 
     */
    @JsonProperty("database")
    public DatabaseSpec getDatabase() {
        return database;
    }

    /**
     * DatabaseSpec
     * <p>
     * Generic database engine specification.
     * (Required)
     * 
     */
    @JsonProperty("database")
    public void setDatabase(DatabaseSpec database) {
        this.database = database;
    }

    public ProjectDatabaseFeature withDatabase(DatabaseSpec database) {
        this.database = database;
        return this;
    }

    /**
     * ProvisioningSpec
     * <p>
     * Provisioning hints for deploying the feature.
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
     * 
     */
    @JsonProperty("provisioning")
    public void setProvisioning(ProvisioningSpec provisioning) {
        this.provisioning = provisioning;
    }

    public ProjectDatabaseFeature withProvisioning(ProvisioningSpec provisioning) {
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

    public ProjectDatabaseFeature withNetworking(NetworkingSpec networking) {
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

    public ProjectDatabaseFeature withObservability(ObservabilitySpec observability) {
        this.observability = observability;
        return this;
    }

    @Override
    public ProjectDatabaseFeature withId(java.lang.String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withName(java.lang.String name) {
        super.withName(name);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withCategory(Category category) {
        super.withCategory(category);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withTags(List<java.lang.String> tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withStatus(FeatureStatus status) {
        super.withStatus(status);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withDescription(java.lang.String description) {
        super.withDescription(description);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withDocumentationUrl(java.lang.String documentationUrl) {
        super.withDocumentationUrl(documentationUrl);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withDocumentation(FeatureDocumentation documentation) {
        super.withDocumentation(documentation);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withPricing(FeaturePricing pricing) {
        super.withPricing(pricing);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withBranding(Branding branding) {
        super.withBranding(branding);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withCompany(CompanyReference company) {
        super.withCompany(company);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withUi(ProjectFeatureUISpec ui) {
        super.withUi(ui);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withConstraints(ProjectFeatureConstraints constraints) {
        super.withConstraints(constraints);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        super.withConfiguration(configuration);
        return this;
    }

    @Override
    public ProjectDatabaseFeature withMetadata(Map<String, String> metadata) {
        super.withMetadata(metadata);
        return this;
    }

}
