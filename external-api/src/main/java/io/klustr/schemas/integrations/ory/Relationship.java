
package io.klustr.schemas.integrations.ory;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Relationship
 * <p>
 * Relationships are the underlying datatype of Ory Permissions. They encode relations between objects, which are the resources that you want to manage to, and subjects, which are the people or things that want to access these resources. A relationship is associated with a namespace where its relation has to be defined and configured.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "namespace",
    "object",
    "subject_id",
    "relation",
    "subject_set"
})
@Generated("jsonschema2pojo")
public class Relationship {

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    @JsonPropertyDescription("The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.")
    private String namespace;
    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    @JsonPropertyDescription("Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.")
    private String object;
    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject_id")
    @JsonPropertyDescription("In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.")
    private String subjectId;
    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    @JsonPropertyDescription("The relationship to the specified object forming the tie.")
    private String relation;
    /**
     * RelationshipSubset
     * <p>
     * A way to abstract and indirectly map. For example if you modeled { 'namespace': 'residents', 'object': '*', 'relation': 'viewers' } 
     * 
     */
    @JsonProperty("subject_set")
    @JsonPropertyDescription("A way to abstract and indirectly map. For example if you modeled { 'namespace': 'residents', 'object': '*', 'relation': 'viewers' } ")
    private RelationshipSubset subjectSet;

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    public String getNamespace() {
        return namespace;
    }

    /**
     * The namespace that bundles the objects. This could be group, video, file, document, and is basically the 'noun' being protected. They are used to scope objects and subjects.
     * 
     */
    @JsonProperty("namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Relationship withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    public String getObject() {
        return object;
    }

    /**
     * Objects are identifiers of entities in an application. For example, objects can represent files, network ports, or physical items. It's up to the application to map its objects to unambiguous identifiers. The name length limit for object identifiers is 64 characters.
     * 
     */
    @JsonProperty("object")
    public void setObject(String object) {
        this.object = object;
    }

    public Relationship withObject(String object) {
        this.object = object;
        return this;
    }

    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject_id")
    public String getSubjectId() {
        return subjectId;
    }

    /**
     * In Ory Keto subjects are a recursive polymorphic datatype. They refer to specific subjects by an identifier defined by the application, for example users, or to sets of subjects.
     * 
     */
    @JsonProperty("subject_id")
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public Relationship withSubjectId(String subjectId) {
        this.subjectId = subjectId;
        return this;
    }

    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    public String getRelation() {
        return relation;
    }

    /**
     * The relationship to the specified object forming the tie.
     * 
     */
    @JsonProperty("relation")
    public void setRelation(String relation) {
        this.relation = relation;
    }

    public Relationship withRelation(String relation) {
        this.relation = relation;
        return this;
    }

    /**
     * RelationshipSubset
     * <p>
     * A way to abstract and indirectly map. For example if you modeled { 'namespace': 'residents', 'object': '*', 'relation': 'viewers' } 
     * 
     */
    @JsonProperty("subject_set")
    public RelationshipSubset getSubjectSet() {
        return subjectSet;
    }

    /**
     * RelationshipSubset
     * <p>
     * A way to abstract and indirectly map. For example if you modeled { 'namespace': 'residents', 'object': '*', 'relation': 'viewers' } 
     * 
     */
    @JsonProperty("subject_set")
    public void setSubjectSet(RelationshipSubset subjectSet) {
        this.subjectSet = subjectSet;
    }

    public Relationship withSubjectSet(RelationshipSubset subjectSet) {
        this.subjectSet = subjectSet;
        return this;
    }

}
