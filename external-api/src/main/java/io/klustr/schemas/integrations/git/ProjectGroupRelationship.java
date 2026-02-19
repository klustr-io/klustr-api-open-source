
package io.klustr.schemas.integrations.git;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ProjectGroupRelationship
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "group_id",
    "group_name",
    "group_full_path",
    "group_access_level"
})
@Generated("jsonschema2pojo")
public class ProjectGroupRelationship {

    @JsonProperty("group_id")
    private Integer groupId;
    @JsonProperty("group_name")
    private String groupName;
    @JsonProperty("group_full_path")
    private String groupFullPath;
    @JsonProperty("group_access_level")
    private Integer groupAccessLevel;

    @JsonProperty("group_id")
    public Integer getGroupId() {
        return groupId;
    }

    @JsonProperty("group_id")
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public ProjectGroupRelationship withGroupId(Integer groupId) {
        this.groupId = groupId;
        return this;
    }

    @JsonProperty("group_name")
    public String getGroupName() {
        return groupName;
    }

    @JsonProperty("group_name")
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ProjectGroupRelationship withGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    @JsonProperty("group_full_path")
    public String getGroupFullPath() {
        return groupFullPath;
    }

    @JsonProperty("group_full_path")
    public void setGroupFullPath(String groupFullPath) {
        this.groupFullPath = groupFullPath;
    }

    public ProjectGroupRelationship withGroupFullPath(String groupFullPath) {
        this.groupFullPath = groupFullPath;
        return this;
    }

    @JsonProperty("group_access_level")
    public Integer getGroupAccessLevel() {
        return groupAccessLevel;
    }

    @JsonProperty("group_access_level")
    public void setGroupAccessLevel(Integer groupAccessLevel) {
        this.groupAccessLevel = groupAccessLevel;
    }

    public ProjectGroupRelationship withGroupAccessLevel(Integer groupAccessLevel) {
        this.groupAccessLevel = groupAccessLevel;
        return this;
    }

}
