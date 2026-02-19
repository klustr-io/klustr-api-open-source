
package io.klustr.schemas.console.features;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.klustr.json.DynamicJson;
import io.klustr.schemas.console.features.core.ProjectFeatureConstraints;
import io.klustr.schemas.console.features.core.ProjectFeatureUISpec;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;


/**
 * AbstractProjectFeature
 * <p>
 * Catalog definition of a feature that can be enabled on projects. This object defines intent, requirements, and provisioning hints, but is not bound to a specific project instance.
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@DynamicJson(property = "type")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "category",
    "tags",
    "status",
    "description",
    "documentation_url",
    "documentation",
    "pricing",
    "branding",
    "company",
    "ui",
    "constraints",
    "configuration",
    "metadata"
})
@Generated("jsonschema2pojo")
public class AbstractProjectFeature {

    /**
     * Stable unique identifier for the feature.
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("Stable unique identifier for the feature.")
    private java.lang.String id;
    /**
     * Human-readable name of the feature.
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Human-readable name of the feature.")
    private java.lang.String name;
    /**
     * High-level category of the feature.
     * 
     */
    @JsonProperty("category")
    @JsonPropertyDescription("High-level category of the feature.")
    private Category category;
    /**
     * Tags applied to this project feature for search
     * 
     */
    @JsonProperty("tags")
    @JsonPropertyDescription("Tags applied to this project feature for search")
    private List<java.lang.String> tags = new ArrayList<java.lang.String>();
    /**
     * Lifecycle status of the feature.
     * (Required)
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("Lifecycle status of the feature.")
    private FeatureStatus status;
    /**
     * Detailed description of what this feature provides.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("Detailed description of what this feature provides.")
    private java.lang.String description;
    /**
     * URL to documentation for this feature.
     * 
     */
    @JsonProperty("documentation_url")
    @JsonPropertyDescription("URL to documentation for this feature.")
    private java.lang.String documentationUrl;
    /**
     * FeatureDocumentation
     * <p>
     * Specific seections of documentation
     * 
     */
    @JsonProperty("documentation")
    @JsonPropertyDescription("Specific seections of documentation")
    private FeatureDocumentation documentation;
    /**
     * FeaturePricing
     * <p>
     * 
     * 
     */
    @JsonProperty("pricing")
    private FeaturePricing pricing;
    /**
     * Branding
     * <p>
     * 
     * 
     */
    @JsonProperty("branding")
    private Branding branding;
    /**
     * CompanyReference
     * <p>
     * The core provider of this project feature can be first or third party
     * 
     */
    @JsonProperty("company")
    @JsonPropertyDescription("The core provider of this project feature can be first or third party")
    private CompanyReference company;
    /**
     * ProjectFeatureUISpec
     * <p>
     * UI metadata used by the console to render navigation and feature-specific views.
     * 
     */
    @JsonProperty("ui")
    @JsonPropertyDescription("UI metadata used by the console to render navigation and feature-specific views.")
    private ProjectFeatureUISpec ui;
    /**
     * ProjectFeatureConstraints
     * <p>
     * Constraints that limit how this feature may be applied.
     * 
     */
    @JsonProperty("constraints")
    @JsonPropertyDescription("Constraints that limit how this feature may be applied.")
    private ProjectFeatureConstraints constraints;
    /**
     * ProjectFeatureRequiredConfiguration
     * <p>
     * Project-level capabilities required by this feature. Each capability is a first-class, typed object containing any configuration required to satisfy it.
     * 
     */
    @JsonProperty("configuration")
    @JsonPropertyDescription("Project-level capabilities required by this feature. Each capability is a first-class, typed object containing any configuration required to satisfy it.")
    private ProjectFeatureRequiredConfiguration configuration;
    /**
     * Free-form metadata used for tagging, migration markers, idempotency, and AI reasoning.
     * 
     */
    @JsonProperty("metadata")
    @JsonPropertyDescription("Free-form metadata used for tagging, migration markers, idempotency, and AI reasoning.")
    private Map<String, String> metadata;

    /**
     * Stable unique identifier for the feature.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public java.lang.String getId() {
        return id;
    }

    /**
     * Stable unique identifier for the feature.
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(java.lang.String id) {
        this.id = id;
    }

    public AbstractProjectFeature withId(java.lang.String id) {
        this.id = id;
        return this;
    }

    /**
     * Human-readable name of the feature.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public java.lang.String getName() {
        return name;
    }

    /**
     * Human-readable name of the feature.
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(java.lang.String name) {
        this.name = name;
    }

    public AbstractProjectFeature withName(java.lang.String name) {
        this.name = name;
        return this;
    }

    /**
     * High-level category of the feature.
     * 
     */
    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    /**
     * High-level category of the feature.
     * 
     */
    @JsonProperty("category")
    public void setCategory(Category category) {
        this.category = category;
    }

    public AbstractProjectFeature withCategory(Category category) {
        this.category = category;
        return this;
    }

    /**
     * Tags applied to this project feature for search
     * 
     */
    @JsonProperty("tags")
    public List<java.lang.String> getTags() {
        return tags;
    }

    /**
     * Tags applied to this project feature for search
     * 
     */
    @JsonProperty("tags")
    public void setTags(List<java.lang.String> tags) {
        this.tags = tags;
    }

    public AbstractProjectFeature withTags(List<java.lang.String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Lifecycle status of the feature.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public FeatureStatus getStatus() {
        return status;
    }

    /**
     * Lifecycle status of the feature.
     * (Required)
     * 
     */
    @JsonProperty("status")
    public void setStatus(FeatureStatus status) {
        this.status = status;
    }

    public AbstractProjectFeature withStatus(FeatureStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Detailed description of what this feature provides.
     * 
     */
    @JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    /**
     * Detailed description of what this feature provides.
     * 
     */
    @JsonProperty("description")
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public AbstractProjectFeature withDescription(java.lang.String description) {
        this.description = description;
        return this;
    }

    /**
     * URL to documentation for this feature.
     * 
     */
    @JsonProperty("documentation_url")
    public java.lang.String getDocumentationUrl() {
        return documentationUrl;
    }

    /**
     * URL to documentation for this feature.
     * 
     */
    @JsonProperty("documentation_url")
    public void setDocumentationUrl(java.lang.String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public AbstractProjectFeature withDocumentationUrl(java.lang.String documentationUrl) {
        this.documentationUrl = documentationUrl;
        return this;
    }

    /**
     * FeatureDocumentation
     * <p>
     * Specific seections of documentation
     * 
     */
    @JsonProperty("documentation")
    public FeatureDocumentation getDocumentation() {
        return documentation;
    }

    /**
     * FeatureDocumentation
     * <p>
     * Specific seections of documentation
     * 
     */
    @JsonProperty("documentation")
    public void setDocumentation(FeatureDocumentation documentation) {
        this.documentation = documentation;
    }

    public AbstractProjectFeature withDocumentation(FeatureDocumentation documentation) {
        this.documentation = documentation;
        return this;
    }

    /**
     * FeaturePricing
     * <p>
     * 
     * 
     */
    @JsonProperty("pricing")
    public FeaturePricing getPricing() {
        return pricing;
    }

    /**
     * FeaturePricing
     * <p>
     * 
     * 
     */
    @JsonProperty("pricing")
    public void setPricing(FeaturePricing pricing) {
        this.pricing = pricing;
    }

    public AbstractProjectFeature withPricing(FeaturePricing pricing) {
        this.pricing = pricing;
        return this;
    }

    /**
     * Branding
     * <p>
     * 
     * 
     */
    @JsonProperty("branding")
    public Branding getBranding() {
        return branding;
    }

    /**
     * Branding
     * <p>
     * 
     * 
     */
    @JsonProperty("branding")
    public void setBranding(Branding branding) {
        this.branding = branding;
    }

    public AbstractProjectFeature withBranding(Branding branding) {
        this.branding = branding;
        return this;
    }

    /**
     * CompanyReference
     * <p>
     * The core provider of this project feature can be first or third party
     * 
     */
    @JsonProperty("company")
    public CompanyReference getCompany() {
        return company;
    }

    /**
     * CompanyReference
     * <p>
     * The core provider of this project feature can be first or third party
     * 
     */
    @JsonProperty("company")
    public void setCompany(CompanyReference company) {
        this.company = company;
    }

    public AbstractProjectFeature withCompany(CompanyReference company) {
        this.company = company;
        return this;
    }

    /**
     * ProjectFeatureUISpec
     * <p>
     * UI metadata used by the console to render navigation and feature-specific views.
     * 
     */
    @JsonProperty("ui")
    public ProjectFeatureUISpec getUi() {
        return ui;
    }

    /**
     * ProjectFeatureUISpec
     * <p>
     * UI metadata used by the console to render navigation and feature-specific views.
     * 
     */
    @JsonProperty("ui")
    public void setUi(ProjectFeatureUISpec ui) {
        this.ui = ui;
    }

    public AbstractProjectFeature withUi(ProjectFeatureUISpec ui) {
        this.ui = ui;
        return this;
    }

    /**
     * ProjectFeatureConstraints
     * <p>
     * Constraints that limit how this feature may be applied.
     * 
     */
    @JsonProperty("constraints")
    public ProjectFeatureConstraints getConstraints() {
        return constraints;
    }

    /**
     * ProjectFeatureConstraints
     * <p>
     * Constraints that limit how this feature may be applied.
     * 
     */
    @JsonProperty("constraints")
    public void setConstraints(ProjectFeatureConstraints constraints) {
        this.constraints = constraints;
    }

    public AbstractProjectFeature withConstraints(ProjectFeatureConstraints constraints) {
        this.constraints = constraints;
        return this;
    }

    /**
     * ProjectFeatureRequiredConfiguration
     * <p>
     * Project-level capabilities required by this feature. Each capability is a first-class, typed object containing any configuration required to satisfy it.
     * 
     */
    @JsonProperty("configuration")
    public ProjectFeatureRequiredConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * ProjectFeatureRequiredConfiguration
     * <p>
     * Project-level capabilities required by this feature. Each capability is a first-class, typed object containing any configuration required to satisfy it.
     * 
     */
    @JsonProperty("configuration")
    public void setConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        this.configuration = configuration;
    }

    public AbstractProjectFeature withConfiguration(ProjectFeatureRequiredConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * Free-form metadata used for tagging, migration markers, idempotency, and AI reasoning.
     * 
     */
    @JsonProperty("metadata")
    public Map<String, String> getMetadata() {
        return metadata;
    }

    /**
     * Free-form metadata used for tagging, migration markers, idempotency, and AI reasoning.
     * 
     */
    @JsonProperty("metadata")
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public AbstractProjectFeature withMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

}
