
package io.klustr.schemas.console.features.minio;

import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.klustr.schemas.console.features.AbstractProjectFeature;
import io.klustr.schemas.console.features.Branding;
import io.klustr.schemas.console.features.Category;
import io.klustr.schemas.console.features.CompanyReference;
import io.klustr.schemas.console.features.FeatureDocumentation;
import io.klustr.schemas.console.features.FeaturePricing;
import io.klustr.schemas.console.features.FeatureStatus;
import io.klustr.schemas.console.features.core.ProjectFeatureConstraints;
import io.klustr.schemas.console.features.core.ProjectFeatureUISpec;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;


/**
 * ProjectMinioFeature
 * <p>
 * Enables minio features for a specific project.
 * 
 */
@JsonTypeName("minio")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({

})
@Generated("jsonschema2pojo")
public class ProjectMinioFeature
    extends AbstractProjectFeature
{


    @Override
    public ProjectMinioFeature withId(java.lang.String id) {
        super.withId(id);
        return this;
    }

    @Override
    public ProjectMinioFeature withName(java.lang.String name) {
        super.withName(name);
        return this;
    }

    @Override
    public ProjectMinioFeature withCategory(Category category) {
        super.withCategory(category);
        return this;
    }

    @Override
    public ProjectMinioFeature withTags(List<java.lang.String> tags) {
        super.withTags(tags);
        return this;
    }

    @Override
    public ProjectMinioFeature withStatus(FeatureStatus status) {
        super.withStatus(status);
        return this;
    }

    @Override
    public ProjectMinioFeature withDescription(java.lang.String description) {
        super.withDescription(description);
        return this;
    }

    @Override
    public ProjectMinioFeature withDocumentationUrl(java.lang.String documentationUrl) {
        super.withDocumentationUrl(documentationUrl);
        return this;
    }

    @Override
    public ProjectMinioFeature withDocumentation(FeatureDocumentation documentation) {
        super.withDocumentation(documentation);
        return this;
    }

    @Override
    public ProjectMinioFeature withPricing(FeaturePricing pricing) {
        super.withPricing(pricing);
        return this;
    }

    @Override
    public ProjectMinioFeature withBranding(Branding branding) {
        super.withBranding(branding);
        return this;
    }

    @Override
    public ProjectMinioFeature withCompany(CompanyReference company) {
        super.withCompany(company);
        return this;
    }

    @Override
    public ProjectMinioFeature withUi(ProjectFeatureUISpec ui) {
        super.withUi(ui);
        return this;
    }

    @Override
    public ProjectMinioFeature withConstraints(ProjectFeatureConstraints constraints) {
        super.withConstraints(constraints);
        return this;
    }

    @Override
    public ProjectMinioFeature withConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        super.withConfiguration(configuration);
        return this;
    }

    @Override
    public ProjectMinioFeature withMetadata(Map<String, String> metadata) {
        super.withMetadata(metadata);
        return this;
    }

}
