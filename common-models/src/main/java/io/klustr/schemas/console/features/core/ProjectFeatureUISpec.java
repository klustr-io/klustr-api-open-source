
package io.klustr.schemas.console.features.core;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectFeatureUISpec
 * <p>
 * UI metadata used by the console to render navigation and feature-specific views.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ui_module",
    "ui_label",
    "ui_icon"
})
@Generated("jsonschema2pojo")
public class ProjectFeatureUISpec {

    /**
     * Frontend module responsible for rendering this feature.
     * 
     */
    @JsonProperty("ui_module")
    @JsonPropertyDescription("Frontend module responsible for rendering this feature.")
    private String uiModule;
    /**
     * Navigation label shown in the sidebar.
     * 
     */
    @JsonProperty("ui_label")
    @JsonPropertyDescription("Navigation label shown in the sidebar.")
    private String uiLabel;
    /**
     * Icon identifier used in the UI.
     * 
     */
    @JsonProperty("ui_icon")
    @JsonPropertyDescription("Icon identifier used in the UI.")
    private String uiIcon;

    /**
     * Frontend module responsible for rendering this feature.
     * 
     */
    @JsonProperty("ui_module")
    public String getUiModule() {
        return uiModule;
    }

    /**
     * Frontend module responsible for rendering this feature.
     * 
     */
    @JsonProperty("ui_module")
    public void setUiModule(String uiModule) {
        this.uiModule = uiModule;
    }

    public ProjectFeatureUISpec withUiModule(String uiModule) {
        this.uiModule = uiModule;
        return this;
    }

    /**
     * Navigation label shown in the sidebar.
     * 
     */
    @JsonProperty("ui_label")
    public String getUiLabel() {
        return uiLabel;
    }

    /**
     * Navigation label shown in the sidebar.
     * 
     */
    @JsonProperty("ui_label")
    public void setUiLabel(String uiLabel) {
        this.uiLabel = uiLabel;
    }

    public ProjectFeatureUISpec withUiLabel(String uiLabel) {
        this.uiLabel = uiLabel;
        return this;
    }

    /**
     * Icon identifier used in the UI.
     * 
     */
    @JsonProperty("ui_icon")
    public String getUiIcon() {
        return uiIcon;
    }

    /**
     * Icon identifier used in the UI.
     * 
     */
    @JsonProperty("ui_icon")
    public void setUiIcon(String uiIcon) {
        this.uiIcon = uiIcon;
    }

    public ProjectFeatureUISpec withUiIcon(String uiIcon) {
        this.uiIcon = uiIcon;
        return this;
    }

}
