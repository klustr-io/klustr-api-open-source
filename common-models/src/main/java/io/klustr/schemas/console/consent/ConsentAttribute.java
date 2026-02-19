
package io.klustr.schemas.console.consent;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * ConsentAttribute
 * <p>
 * The scope of consent that is used to manage consent for users.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "category",
    "subcategory",
    "label",
    "org_id",
    "status",
    "description",
    "sensitivity",
    "visible"
})
@Generated("jsonschema2pojo")
public class ConsentAttribute {

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID of this consent scope.")
    private String id;
    /**
     * The top level category used for organizing scopes.
     * 
     */
    @JsonProperty("category")
    @JsonPropertyDescription("The top level category used for organizing scopes.")
    private String category;
    /**
     * The a secondary level category used for organizing scopes.
     * 
     */
    @JsonProperty("subcategory")
    @JsonPropertyDescription("The a secondary level category used for organizing scopes.")
    private String subcategory;
    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("label")
    @JsonPropertyDescription("Supports default english label with i18n translations")
    private Text label;
    /**
     * If this attribute is custom to a specific organization.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("If this attribute is custom to a specific organization.")
    private String orgId;
    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this consent attribute.")
    private ConsentAttribute.Status status;
    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("Supports default english label with i18n translations")
    private Text description;
    /**
     * The level of sensitivity for this scope.
     * 
     */
    @JsonProperty("sensitivity")
    @JsonPropertyDescription("The level of sensitivity for this scope.")
    private ConsentAttribute.Sensitivity sensitivity;
    /**
     * If the scope should be visible to users when granting consent.
     * 
     */
    @JsonProperty("visible")
    @JsonPropertyDescription("If the scope should be visible to users when granting consent.")
    private Boolean visible = true;

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID of this consent scope.
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ConsentAttribute withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The top level category used for organizing scopes.
     * 
     */
    @JsonProperty("category")
    public String getCategory() {
        return category;
    }

    /**
     * The top level category used for organizing scopes.
     * 
     */
    @JsonProperty("category")
    public void setCategory(String category) {
        this.category = category;
    }

    public ConsentAttribute withCategory(String category) {
        this.category = category;
        return this;
    }

    /**
     * The a secondary level category used for organizing scopes.
     * 
     */
    @JsonProperty("subcategory")
    public String getSubcategory() {
        return subcategory;
    }

    /**
     * The a secondary level category used for organizing scopes.
     * 
     */
    @JsonProperty("subcategory")
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public ConsentAttribute withSubcategory(String subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("label")
    public Text getLabel() {
        return label;
    }

    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("label")
    public void setLabel(Text label) {
        this.label = label;
    }

    public ConsentAttribute withLabel(Text label) {
        this.label = label;
        return this;
    }

    /**
     * If this attribute is custom to a specific organization.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * If this attribute is custom to a specific organization.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public ConsentAttribute withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    public ConsentAttribute.Status getStatus() {
        return status;
    }

    /**
     * The status of this consent attribute.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ConsentAttribute.Status status) {
        this.status = status;
    }

    public ConsentAttribute withStatus(ConsentAttribute.Status status) {
        this.status = status;
        return this;
    }

    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("description")
    public Text getDescription() {
        return description;
    }

    /**
     * Text
     * <p>
     * Supports default english label with i18n translations
     * 
     */
    @JsonProperty("description")
    public void setDescription(Text description) {
        this.description = description;
    }

    public ConsentAttribute withDescription(Text description) {
        this.description = description;
        return this;
    }

    /**
     * The level of sensitivity for this scope.
     * 
     */
    @JsonProperty("sensitivity")
    public ConsentAttribute.Sensitivity getSensitivity() {
        return sensitivity;
    }

    /**
     * The level of sensitivity for this scope.
     * 
     */
    @JsonProperty("sensitivity")
    public void setSensitivity(ConsentAttribute.Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
    }

    public ConsentAttribute withSensitivity(ConsentAttribute.Sensitivity sensitivity) {
        this.sensitivity = sensitivity;
        return this;
    }

    /**
     * If the scope should be visible to users when granting consent.
     * 
     */
    @JsonProperty("visible")
    public Boolean getVisible() {
        return visible;
    }

    /**
     * If the scope should be visible to users when granting consent.
     * 
     */
    @JsonProperty("visible")
    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public ConsentAttribute withVisible(Boolean visible) {
        this.visible = visible;
        return this;
    }


    /**
     * The level of sensitivity for this scope.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Sensitivity {

        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");
        private final String value;
        private final static Map<String, ConsentAttribute.Sensitivity> CONSTANTS = new HashMap<String, ConsentAttribute.Sensitivity>();

        static {
            for (ConsentAttribute.Sensitivity c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Sensitivity(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static ConsentAttribute.Sensitivity fromValue(String value) {
            ConsentAttribute.Sensitivity constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The status of this consent attribute.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        TEST("test"),
        STAGE("stage"),
        GA("ga"),
        DEPRECATED("deprecated");
        private final String value;
        private final static Map<String, ConsentAttribute.Status> CONSTANTS = new HashMap<String, ConsentAttribute.Status>();

        static {
            for (ConsentAttribute.Status c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Status(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static ConsentAttribute.Status fromValue(String value) {
            ConsentAttribute.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
