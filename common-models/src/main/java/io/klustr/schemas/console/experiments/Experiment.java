
package io.klustr.schemas.console.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.DateTime;


/**
 * Experiment
 * <p>
 * The generic information about an experiment such as labels and names.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "tags",
    "creation_date",
    "start_date",
    "stop_date",
    "description",
    "media",
    "status",
    "owner",
    "project_id",
    "org_id",
    "external_id",
    "external_source",
    "scopes",
    "privacy_url",
    "tos_url",
    "info_url"
})
@Generated("jsonschema2pojo")
public class Experiment {

    /**
     * The unique ID for this experiment
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique ID for this experiment")
    private String id;
    /**
     * The name for this experiment
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("The name for this experiment")
    private String name;
    /**
     * The tags for this experiment
     * 
     */
    @JsonProperty("tags")
    @JsonPropertyDescription("The tags for this experiment")
    private List<String> tags = new ArrayList<String>();
    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this client was created.")
    private DateTime creationDate;
    /**
     * The date this experiment will go live.
     * 
     */
    @JsonProperty("start_date")
    @JsonPropertyDescription("The date this experiment will go live.")
    private DateTime startDate;
    /**
     * The date this experiment will stop.
     * 
     */
    @JsonProperty("stop_date")
    @JsonPropertyDescription("The date this experiment will stop.")
    private DateTime stopDate;
    /**
     * The general description for this experiment
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The general description for this experiment")
    private String description;
    /**
     * ExperimentMedia
     * <p>
     * The media associated with this experiment for display to a user.
     * 
     */
    @JsonProperty("media")
    @JsonPropertyDescription("The media associated with this experiment for display to a user.")
    private ExperimentMedia media;
    /**
     * The status of this given experiment
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this given experiment")
    private Experiment.Status status;
    /**
     * The owner for this experiment
     * 
     */
    @JsonProperty("owner")
    @JsonPropertyDescription("The owner for this experiment")
    private String owner;
    /**
     * The project that owns this experiment which can then map to an organization or billable entity.
     * 
     */
    @JsonProperty("project_id")
    @JsonPropertyDescription("The project that owns this experiment which can then map to an organization or billable entity.")
    private String projectId;
    /**
     * The organization that owns this particular experiment.
     * 
     */
    @JsonProperty("org_id")
    @JsonPropertyDescription("The organization that owns this particular experiment.")
    private String orgId;
    /**
     * The external ID of this experiment if the experiment is being run outside of the platform.
     * 
     */
    @JsonProperty("external_id")
    @JsonPropertyDescription("The external ID of this experiment if the experiment is being run outside of the platform.")
    private String externalId;
    /**
     * If running externally what is the source of this experiment (Growthbook, Mode, etc)
     * 
     */
    @JsonProperty("external_source")
    @JsonPropertyDescription("If running externally what is the source of this experiment (Growthbook, Mode, etc)")
    private String externalSource;
    /**
     * The additional scopes that this experiment needs in order to run.
     * 
     */
    @JsonProperty("scopes")
    @JsonPropertyDescription("The additional scopes that this experiment needs in order to run.")
    private List<ExperimentScope> scopes = new ArrayList<ExperimentScope>();
    /**
     * Additional privacy disclaimers with links for this experiment.
     * 
     */
    @JsonProperty("privacy_url")
    @JsonPropertyDescription("Additional privacy disclaimers with links for this experiment.")
    private String privacyUrl;
    /**
     * Additional terms and conditions for this experiment.
     * 
     */
    @JsonProperty("tos_url")
    @JsonPropertyDescription("Additional terms and conditions for this experiment.")
    private String tosUrl;
    /**
     * Additional information about this experiment.
     * 
     */
    @JsonProperty("info_url")
    @JsonPropertyDescription("Additional information about this experiment.")
    private String infoUrl;

    /**
     * The unique ID for this experiment
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique ID for this experiment
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Experiment withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * The name for this experiment
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name for this experiment
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Experiment withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The tags for this experiment
     * 
     */
    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }

    /**
     * The tags for this experiment
     * 
     */
    @JsonProperty("tags")
    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Experiment withTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this client was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Experiment withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The date this experiment will go live.
     * 
     */
    @JsonProperty("start_date")
    public DateTime getStartDate() {
        return startDate;
    }

    /**
     * The date this experiment will go live.
     * 
     */
    @JsonProperty("start_date")
    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public Experiment withStartDate(DateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    /**
     * The date this experiment will stop.
     * 
     */
    @JsonProperty("stop_date")
    public DateTime getStopDate() {
        return stopDate;
    }

    /**
     * The date this experiment will stop.
     * 
     */
    @JsonProperty("stop_date")
    public void setStopDate(DateTime stopDate) {
        this.stopDate = stopDate;
    }

    public Experiment withStopDate(DateTime stopDate) {
        this.stopDate = stopDate;
        return this;
    }

    /**
     * The general description for this experiment
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The general description for this experiment
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Experiment withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * ExperimentMedia
     * <p>
     * The media associated with this experiment for display to a user.
     * 
     */
    @JsonProperty("media")
    public ExperimentMedia getMedia() {
        return media;
    }

    /**
     * ExperimentMedia
     * <p>
     * The media associated with this experiment for display to a user.
     * 
     */
    @JsonProperty("media")
    public void setMedia(ExperimentMedia media) {
        this.media = media;
    }

    public Experiment withMedia(ExperimentMedia media) {
        this.media = media;
        return this;
    }

    /**
     * The status of this given experiment
     * 
     */
    @JsonProperty("status")
    public Experiment.Status getStatus() {
        return status;
    }

    /**
     * The status of this given experiment
     * 
     */
    @JsonProperty("status")
    public void setStatus(Experiment.Status status) {
        this.status = status;
    }

    public Experiment withStatus(Experiment.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The owner for this experiment
     * 
     */
    @JsonProperty("owner")
    public String getOwner() {
        return owner;
    }

    /**
     * The owner for this experiment
     * 
     */
    @JsonProperty("owner")
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Experiment withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    /**
     * The project that owns this experiment which can then map to an organization or billable entity.
     * 
     */
    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    /**
     * The project that owns this experiment which can then map to an organization or billable entity.
     * 
     */
    @JsonProperty("project_id")
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public Experiment withProjectId(String projectId) {
        this.projectId = projectId;
        return this;
    }

    /**
     * The organization that owns this particular experiment.
     * 
     */
    @JsonProperty("org_id")
    public String getOrgId() {
        return orgId;
    }

    /**
     * The organization that owns this particular experiment.
     * 
     */
    @JsonProperty("org_id")
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Experiment withOrgId(String orgId) {
        this.orgId = orgId;
        return this;
    }

    /**
     * The external ID of this experiment if the experiment is being run outside of the platform.
     * 
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * The external ID of this experiment if the experiment is being run outside of the platform.
     * 
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Experiment withExternalId(String externalId) {
        this.externalId = externalId;
        return this;
    }

    /**
     * If running externally what is the source of this experiment (Growthbook, Mode, etc)
     * 
     */
    @JsonProperty("external_source")
    public String getExternalSource() {
        return externalSource;
    }

    /**
     * If running externally what is the source of this experiment (Growthbook, Mode, etc)
     * 
     */
    @JsonProperty("external_source")
    public void setExternalSource(String externalSource) {
        this.externalSource = externalSource;
    }

    public Experiment withExternalSource(String externalSource) {
        this.externalSource = externalSource;
        return this;
    }

    /**
     * The additional scopes that this experiment needs in order to run.
     * 
     */
    @JsonProperty("scopes")
    public List<ExperimentScope> getScopes() {
        return scopes;
    }

    /**
     * The additional scopes that this experiment needs in order to run.
     * 
     */
    @JsonProperty("scopes")
    public void setScopes(List<ExperimentScope> scopes) {
        this.scopes = scopes;
    }

    public Experiment withScopes(List<ExperimentScope> scopes) {
        this.scopes = scopes;
        return this;
    }

    /**
     * Additional privacy disclaimers with links for this experiment.
     * 
     */
    @JsonProperty("privacy_url")
    public String getPrivacyUrl() {
        return privacyUrl;
    }

    /**
     * Additional privacy disclaimers with links for this experiment.
     * 
     */
    @JsonProperty("privacy_url")
    public void setPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
    }

    public Experiment withPrivacyUrl(String privacyUrl) {
        this.privacyUrl = privacyUrl;
        return this;
    }

    /**
     * Additional terms and conditions for this experiment.
     * 
     */
    @JsonProperty("tos_url")
    public String getTosUrl() {
        return tosUrl;
    }

    /**
     * Additional terms and conditions for this experiment.
     * 
     */
    @JsonProperty("tos_url")
    public void setTosUrl(String tosUrl) {
        this.tosUrl = tosUrl;
    }

    public Experiment withTosUrl(String tosUrl) {
        this.tosUrl = tosUrl;
        return this;
    }

    /**
     * Additional information about this experiment.
     * 
     */
    @JsonProperty("info_url")
    public String getInfoUrl() {
        return infoUrl;
    }

    /**
     * Additional information about this experiment.
     * 
     */
    @JsonProperty("info_url")
    public void setInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
    }

    public Experiment withInfoUrl(String infoUrl) {
        this.infoUrl = infoUrl;
        return this;
    }


    /**
     * The status of this given experiment
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        PUBLISHED("published"),
        DRAFT("draft"),
        DELETED("deleted"),
        RUNNING("running"),
        PAUSED("paused");
        private final String value;
        private final static Map<String, Experiment.Status> CONSTANTS = new HashMap<String, Experiment.Status>();

        static {
            for (Experiment.Status c: values()) {
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
        public static Experiment.Status fromValue(String value) {
            Experiment.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
