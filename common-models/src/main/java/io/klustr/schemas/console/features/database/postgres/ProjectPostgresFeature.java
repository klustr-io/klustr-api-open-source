
package io.klustr.schemas.console.features.database.postgres;

import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
import io.klustr.schemas.console.features.database.DatabaseSpec;
import io.klustr.schemas.console.features.database.ProjectDatabaseFeature;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;


/**
 * ProjectPostgresFeature
 * <p>
 * PostgreSQL database feature definition.
 * 
 */
@JsonTypeName("postgres")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
@Generated("jsonschema2pojo")
public class ProjectPostgresFeature
    extends ProjectDatabaseFeature
{


    @Override
    public ProjectPostgresFeature withDatabase(DatabaseSpec database) {
        super.withDatabase(database);
        return this;
    }

    @Override
    public ProjectPostgresFeature withProvisioning(ProvisioningSpec provisioning) {
        super.withProvisioning(provisioning);
        return this;
    }

    @Override
    public ProjectPostgresFeature withNetworking(NetworkingSpec networking) {
        super.withNetworking(networking);
        return this;
    }

    @Override
    public ProjectPostgresFeature withObservability(ObservabilitySpec observability) {
        super.withObservability(observability);
        return this;
    }

    @Override
    public ProjectPostgresFeature withId(java.lang.String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProjectPostgresFeature withName(java.lang.String name) {
        super.withName(name);
        return this;
    }

    @Override
    public ProjectPostgresFeature withCategory(Category category) {
        super.withCategory(category);
        return this;
    }

    @Override
    public ProjectPostgresFeature withTags(List<java.lang.String> tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public ProjectPostgresFeature withStatus(FeatureStatus status) {
        super.withStatus(status);
        return this;
    }

    @Override
    public ProjectPostgresFeature withDescription(java.lang.String description) {
        super.withDescription(description);
        return this;
    }

    @Override
    public ProjectPostgresFeature withDocumentationUrl(java.lang.String documentationUrl) {
        super.withDocumentationUrl(documentationUrl);
        return this;
    }

    @Override
    public ProjectPostgresFeature withDocumentation(FeatureDocumentation documentation) {
        super.withDocumentation(documentation);
        return this;
    }

    @Override
    public ProjectPostgresFeature withPricing(FeaturePricing pricing) {
        super.withPricing(pricing);
        return this;
    }

    @Override
    public ProjectPostgresFeature withBranding(Branding branding) {
        super.withBranding(branding);
        return this;
    }

    @Override
    public ProjectPostgresFeature withCompany(CompanyReference company) {
        super.withCompany(company);
        return this;
    }

    @Override
    public ProjectPostgresFeature withUi(ProjectFeatureUISpec ui) {
        super.withUi(ui);
        return this;
    }

    @Override
    public ProjectPostgresFeature withConstraints(ProjectFeatureConstraints constraints) {
        super.withConstraints(constraints);
        return this;
    }

    @Override
    public ProjectPostgresFeature withConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        super.withConfiguration(configuration);
        return this;
    }

    @Override
    public ProjectPostgresFeature withMetadata(Map<String, String> metadata) {
        super.withMetadata(metadata);
        return this;
    }

}
