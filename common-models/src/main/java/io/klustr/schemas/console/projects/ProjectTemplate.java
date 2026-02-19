
package io.klustr.schemas.console.projects;

import java.util.HashMap;
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
 * ProjectTemplate
 * <p>
 * Enables a standardized library where a user can pick from a template and apply it to a project so that they can quickly get up and running.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "scope",
    "image",
    "requires_image",
    "description",
    "type",
    "provisioner",
    "port",
    "expose_port",
    "protocol",
    "resolver",
    "endpoint_url",
    "documentation_url",
    "o11y",
    "required_features",
    "creation_date",
    "status",
    "manifest_filename",
    "default_machine_type",
    "autoscale",
    "restrictions"
})
@Generated("jsonschema2pojo")
public class ProjectTemplate {

    /**
     * The unique identifier for the project template
     * (Required)
     * 
     */
    @JsonProperty("id")
    @JsonPropertyDescription("The unique identifier for the project template")
    private String id;
    /**
     * Name of the project template
     * (Required)
     * 
     */
    @JsonProperty("name")
    @JsonPropertyDescription("Name of the project template")
    private String name;
    /**
     * The scope of this project when released, it can be ORGanization level, which means only one instance per org. or project scoped.
     * 
     */
    @JsonProperty("scope")
    @JsonPropertyDescription("The scope of this project when released, it can be ORGanization level, which means only one instance per org. or project scoped.")
    private ProjectTemplate.Scope scope;
    /**
     * The image to use when deploying.
     * 
     */
    @JsonProperty("image")
    @JsonPropertyDescription("The image to use when deploying.")
    private String image;
    /**
     * Requires an image to deploy
     * 
     */
    @JsonProperty("requires_image")
    @JsonPropertyDescription("Requires an image to deploy")
    private Boolean requiresImage;
    /**
     * The description of this project template for a user to find and discover it.
     * 
     */
    @JsonProperty("description")
    @JsonPropertyDescription("The description of this project template for a user to find and discover it.")
    private String description;
    /**
     * The type of project template used to indicate how to access and what to use
     * 
     */
    @JsonProperty("type")
    @JsonPropertyDescription("The type of project template used to indicate how to access and what to use")
    private ProjectTemplate.Type type;
    @JsonProperty("provisioner")
    private ProjectTemplate.Provisioner provisioner;
    /**
     * The port exposed by this template when deployed
     * 
     */
    @JsonProperty("port")
    @JsonPropertyDescription("The port exposed by this template when deployed")
    private Integer port;
    /**
     * When deployed this will claim an available public port. When active, during first deployment will claim a port that maps to the internal port.
     * 
     */
    @JsonProperty("expose_port")
    @JsonPropertyDescription("When deployed this will claim an available public port. When active, during first deployment will claim a port that maps to the internal port.")
    private Boolean exposePort;
    /**
     * Specify how we might route traffic to the instance. This is useful for domain routing such as {projectId}.compute.dev.klustr.io
     * 
     */
    @JsonProperty("protocol")
    @JsonPropertyDescription("Specify how we might route traffic to the instance. This is useful for domain routing such as {projectId}.compute.dev.klustr.io")
    private ProjectTemplate.Protocol protocol;
    /**
     * Specify how we resolve in a load balancer to get access to the service. If mode is HTTP we can safely assume to use http. For example `use_backend postgres_bar if { req.ssl_sni -m beg bar. }` is used with sni routing.
     * 
     */
    @JsonProperty("resolver")
    @JsonPropertyDescription("Specify how we resolve in a load balancer to get access to the service. If mode is HTTP we can safely assume to use http. For example `use_backend postgres_bar if { req.ssl_sni -m beg bar. }` is used with sni routing.")
    private ProjectTemplate.Resolver resolver;
    /**
     * The URL template that is used that will drive how to access for example https://${projectId}.dev.klustr.io
     * 
     */
    @JsonProperty("endpoint_url")
    @JsonPropertyDescription("The URL template that is used that will drive how to access for example https://${projectId}.dev.klustr.io")
    private String endpointUrl = "https://${projectId}.dev.klustr.io";
    /**
     * The full documentation for this project template for users to follow along
     * 
     */
    @JsonProperty("documentation_url")
    @JsonPropertyDescription("The full documentation for this project template for users to follow along")
    private String documentationUrl;
    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    @JsonPropertyDescription("Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.")
    private O11y o11y;
    /**
     * The required features for this project template in order to function.
     * 
     */
    @JsonProperty("required_features")
    @JsonPropertyDescription("The required features for this project template in order to function.")
    private RequiredFeatures requiredFeatures;
    /**
     * The date this template was created.
     * 
     */
    @JsonProperty("creation_date")
    @JsonPropertyDescription("The date this template was created.")
    private DateTime creationDate;
    /**
     * The status of this template.
     * 
     */
    @JsonProperty("status")
    @JsonPropertyDescription("The status of this template.")
    private ProjectTemplate.Status status;
    /**
     * The name of the file used when provisioning and templating the docker compose. Must be loaded into resources in order to resolve.
     * 
     */
    @JsonProperty("manifest_filename")
    @JsonPropertyDescription("The name of the file used when provisioning and templating the docker compose. Must be loaded into resources in order to resolve.")
    private String manifestFilename;
    /**
     * The default machine type for this instance
     * 
     */
    @JsonProperty("default_machine_type")
    @JsonPropertyDescription("The default machine type for this instance")
    private String defaultMachineType = "m1.small";
    /**
     * This project type supports autoscaling features and given load will enable autoscaling isntances up when needed.
     * 
     */
    @JsonProperty("autoscale")
    @JsonPropertyDescription("This project type supports autoscaling features and given load will enable autoscaling isntances up when needed.")
    private Boolean autoscale;
    /**
     * ProjectTemplateRestrictions
     * <p>
     * Any restrictions such as limiting the number of instances allowed to be provisioned. For example, an organization may only have 1 pushgateway or 1 IDP provider etc.
     * 
     */
    @JsonProperty("restrictions")
    @JsonPropertyDescription("Any restrictions such as limiting the number of instances allowed to be provisioned. For example, an organization may only have 1 pushgateway or 1 IDP provider etc.")
    private ProjectTemplateRestrictions restrictions;

    /**
     * The unique identifier for the project template
     * (Required)
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The unique identifier for the project template
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public ProjectTemplate withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Name of the project template
     * (Required)
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Name of the project template
     * (Required)
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public ProjectTemplate withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * The scope of this project when released, it can be ORGanization level, which means only one instance per org. or project scoped.
     * 
     */
    @JsonProperty("scope")
    public ProjectTemplate.Scope getScope() {
        return scope;
    }

    /**
     * The scope of this project when released, it can be ORGanization level, which means only one instance per org. or project scoped.
     * 
     */
    @JsonProperty("scope")
    public void setScope(ProjectTemplate.Scope scope) {
        this.scope = scope;
    }

    public ProjectTemplate withScope(ProjectTemplate.Scope scope) {
        this.scope = scope;
        return this;
    }

    /**
     * The image to use when deploying.
     * 
     */
    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    /**
     * The image to use when deploying.
     * 
     */
    @JsonProperty("image")
    public void setImage(String image) {
        this.image = image;
    }

    public ProjectTemplate withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * Requires an image to deploy
     * 
     */
    @JsonProperty("requires_image")
    public Boolean getRequiresImage() {
        return requiresImage;
    }

    /**
     * Requires an image to deploy
     * 
     */
    @JsonProperty("requires_image")
    public void setRequiresImage(Boolean requiresImage) {
        this.requiresImage = requiresImage;
    }

    public ProjectTemplate withRequiresImage(Boolean requiresImage) {
        this.requiresImage = requiresImage;
        return this;
    }

    /**
     * The description of this project template for a user to find and discover it.
     * 
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * The description of this project template for a user to find and discover it.
     * 
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectTemplate withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The type of project template used to indicate how to access and what to use
     * 
     */
    @JsonProperty("type")
    public ProjectTemplate.Type getType() {
        return type;
    }

    /**
     * The type of project template used to indicate how to access and what to use
     * 
     */
    @JsonProperty("type")
    public void setType(ProjectTemplate.Type type) {
        this.type = type;
    }

    public ProjectTemplate withType(ProjectTemplate.Type type) {
        this.type = type;
        return this;
    }

    @JsonProperty("provisioner")
    public ProjectTemplate.Provisioner getProvisioner() {
        return provisioner;
    }

    @JsonProperty("provisioner")
    public void setProvisioner(ProjectTemplate.Provisioner provisioner) {
        this.provisioner = provisioner;
    }

    public ProjectTemplate withProvisioner(ProjectTemplate.Provisioner provisioner) {
        this.provisioner = provisioner;
        return this;
    }

    /**
     * The port exposed by this template when deployed
     * 
     */
    @JsonProperty("port")
    public Integer getPort() {
        return port;
    }

    /**
     * The port exposed by this template when deployed
     * 
     */
    @JsonProperty("port")
    public void setPort(Integer port) {
        this.port = port;
    }

    public ProjectTemplate withPort(Integer port) {
        this.port = port;
        return this;
    }

    /**
     * When deployed this will claim an available public port. When active, during first deployment will claim a port that maps to the internal port.
     * 
     */
    @JsonProperty("expose_port")
    public Boolean getExposePort() {
        return exposePort;
    }

    /**
     * When deployed this will claim an available public port. When active, during first deployment will claim a port that maps to the internal port.
     * 
     */
    @JsonProperty("expose_port")
    public void setExposePort(Boolean exposePort) {
        this.exposePort = exposePort;
    }

    public ProjectTemplate withExposePort(Boolean exposePort) {
        this.exposePort = exposePort;
        return this;
    }

    /**
     * Specify how we might route traffic to the instance. This is useful for domain routing such as {projectId}.compute.dev.klustr.io
     * 
     */
    @JsonProperty("protocol")
    public ProjectTemplate.Protocol getProtocol() {
        return protocol;
    }

    /**
     * Specify how we might route traffic to the instance. This is useful for domain routing such as {projectId}.compute.dev.klustr.io
     * 
     */
    @JsonProperty("protocol")
    public void setProtocol(ProjectTemplate.Protocol protocol) {
        this.protocol = protocol;
    }

    public ProjectTemplate withProtocol(ProjectTemplate.Protocol protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Specify how we resolve in a load balancer to get access to the service. If mode is HTTP we can safely assume to use http. For example `use_backend postgres_bar if { req.ssl_sni -m beg bar. }` is used with sni routing.
     * 
     */
    @JsonProperty("resolver")
    public ProjectTemplate.Resolver getResolver() {
        return resolver;
    }

    /**
     * Specify how we resolve in a load balancer to get access to the service. If mode is HTTP we can safely assume to use http. For example `use_backend postgres_bar if { req.ssl_sni -m beg bar. }` is used with sni routing.
     * 
     */
    @JsonProperty("resolver")
    public void setResolver(ProjectTemplate.Resolver resolver) {
        this.resolver = resolver;
    }

    public ProjectTemplate withResolver(ProjectTemplate.Resolver resolver) {
        this.resolver = resolver;
        return this;
    }

    /**
     * The URL template that is used that will drive how to access for example https://${projectId}.dev.klustr.io
     * 
     */
    @JsonProperty("endpoint_url")
    public String getEndpointUrl() {
        return endpointUrl;
    }

    /**
     * The URL template that is used that will drive how to access for example https://${projectId}.dev.klustr.io
     * 
     */
    @JsonProperty("endpoint_url")
    public void setEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
    }

    public ProjectTemplate withEndpointUrl(String endpointUrl) {
        this.endpointUrl = endpointUrl;
        return this;
    }

    /**
     * The full documentation for this project template for users to follow along
     * 
     */
    @JsonProperty("documentation_url")
    public String getDocumentationUrl() {
        return documentationUrl;
    }

    /**
     * The full documentation for this project template for users to follow along
     * 
     */
    @JsonProperty("documentation_url")
    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }

    public ProjectTemplate withDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
        return this;
    }

    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    public O11y getO11y() {
        return o11y;
    }

    /**
     * Observability endpoints for this by default added, for example if mongoDB we can expose default metric endpoints.
     * 
     */
    @JsonProperty("o11y")
    public void setO11y(O11y o11y) {
        this.o11y = o11y;
    }

    public ProjectTemplate withO11y(O11y o11y) {
        this.o11y = o11y;
        return this;
    }

    /**
     * The required features for this project template in order to function.
     * 
     */
    @JsonProperty("required_features")
    public RequiredFeatures getRequiredFeatures() {
        return requiredFeatures;
    }

    /**
     * The required features for this project template in order to function.
     * 
     */
    @JsonProperty("required_features")
    public void setRequiredFeatures(RequiredFeatures requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
    }

    public ProjectTemplate withRequiredFeatures(RequiredFeatures requiredFeatures) {
        this.requiredFeatures = requiredFeatures;
        return this;
    }

    /**
     * The date this template was created.
     * 
     */
    @JsonProperty("creation_date")
    public DateTime getCreationDate() {
        return creationDate;
    }

    /**
     * The date this template was created.
     * 
     */
    @JsonProperty("creation_date")
    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ProjectTemplate withCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    /**
     * The status of this template.
     * 
     */
    @JsonProperty("status")
    public ProjectTemplate.Status getStatus() {
        return status;
    }

    /**
     * The status of this template.
     * 
     */
    @JsonProperty("status")
    public void setStatus(ProjectTemplate.Status status) {
        this.status = status;
    }

    public ProjectTemplate withStatus(ProjectTemplate.Status status) {
        this.status = status;
        return this;
    }

    /**
     * The name of the file used when provisioning and templating the docker compose. Must be loaded into resources in order to resolve.
     * 
     */
    @JsonProperty("manifest_filename")
    public String getManifestFilename() {
        return manifestFilename;
    }

    /**
     * The name of the file used when provisioning and templating the docker compose. Must be loaded into resources in order to resolve.
     * 
     */
    @JsonProperty("manifest_filename")
    public void setManifestFilename(String manifestFilename) {
        this.manifestFilename = manifestFilename;
    }

    public ProjectTemplate withManifestFilename(String manifestFilename) {
        this.manifestFilename = manifestFilename;
        return this;
    }

    /**
     * The default machine type for this instance
     * 
     */
    @JsonProperty("default_machine_type")
    public String getDefaultMachineType() {
        return defaultMachineType;
    }

    /**
     * The default machine type for this instance
     * 
     */
    @JsonProperty("default_machine_type")
    public void setDefaultMachineType(String defaultMachineType) {
        this.defaultMachineType = defaultMachineType;
    }

    public ProjectTemplate withDefaultMachineType(String defaultMachineType) {
        this.defaultMachineType = defaultMachineType;
        return this;
    }

    /**
     * This project type supports autoscaling features and given load will enable autoscaling isntances up when needed.
     * 
     */
    @JsonProperty("autoscale")
    public Boolean getAutoscale() {
        return autoscale;
    }

    /**
     * This project type supports autoscaling features and given load will enable autoscaling isntances up when needed.
     * 
     */
    @JsonProperty("autoscale")
    public void setAutoscale(Boolean autoscale) {
        this.autoscale = autoscale;
    }

    public ProjectTemplate withAutoscale(Boolean autoscale) {
        this.autoscale = autoscale;
        return this;
    }

    /**
     * ProjectTemplateRestrictions
     * <p>
     * Any restrictions such as limiting the number of instances allowed to be provisioned. For example, an organization may only have 1 pushgateway or 1 IDP provider etc.
     * 
     */
    @JsonProperty("restrictions")
    public ProjectTemplateRestrictions getRestrictions() {
        return restrictions;
    }

    /**
     * ProjectTemplateRestrictions
     * <p>
     * Any restrictions such as limiting the number of instances allowed to be provisioned. For example, an organization may only have 1 pushgateway or 1 IDP provider etc.
     * 
     */
    @JsonProperty("restrictions")
    public void setRestrictions(ProjectTemplateRestrictions restrictions) {
        this.restrictions = restrictions;
    }

    public ProjectTemplate withRestrictions(ProjectTemplateRestrictions restrictions) {
        this.restrictions = restrictions;
        return this;
    }


    /**
     * Specify how we might route traffic to the instance. This is useful for domain routing such as {projectId}.compute.dev.klustr.io
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Protocol {

        HTTP("http"),
        TCP("tcp");
        private final String value;
        private final static Map<String, ProjectTemplate.Protocol> CONSTANTS = new HashMap<String, ProjectTemplate.Protocol>();

        static {
            for (ProjectTemplate.Protocol c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Protocol(String value) {
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
        public static ProjectTemplate.Protocol fromValue(String value) {
            ProjectTemplate.Protocol constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("jsonschema2pojo")
    public enum Provisioner {

        PORTAINER("portainer"),
        POSTGRES("postgres");
        private final String value;
        private final static Map<String, ProjectTemplate.Provisioner> CONSTANTS = new HashMap<String, ProjectTemplate.Provisioner>();

        static {
            for (ProjectTemplate.Provisioner c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Provisioner(String value) {
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
        public static ProjectTemplate.Provisioner fromValue(String value) {
            ProjectTemplate.Provisioner constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * Specify how we resolve in a load balancer to get access to the service. If mode is HTTP we can safely assume to use http. For example `use_backend postgres_bar if { req.ssl_sni -m beg bar. }` is used with sni routing.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Resolver {

        HTTP_HOSTNAME_HEADER("http_hostname_header"),
        SSL_FC_SNI("ssl_fc_sni"),
        REQ_SSL_SNI("req_ssl_sni");
        private final String value;
        private final static Map<String, ProjectTemplate.Resolver> CONSTANTS = new HashMap<String, ProjectTemplate.Resolver>();

        static {
            for (ProjectTemplate.Resolver c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Resolver(String value) {
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
        public static ProjectTemplate.Resolver fromValue(String value) {
            ProjectTemplate.Resolver constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The scope of this project when released, it can be ORGanization level, which means only one instance per org. or project scoped.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Scope {

        ORG("org"),
        PROJECT("project");
        private final String value;
        private final static Map<String, ProjectTemplate.Scope> CONSTANTS = new HashMap<String, ProjectTemplate.Scope>();

        static {
            for (ProjectTemplate.Scope c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Scope(String value) {
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
        public static ProjectTemplate.Scope fromValue(String value) {
            ProjectTemplate.Scope constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The status of this template.
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Status {

        ACTIVE("active"),
        INACTIVE("inactive");
        private final String value;
        private final static Map<String, ProjectTemplate.Status> CONSTANTS = new HashMap<String, ProjectTemplate.Status>();

        static {
            for (ProjectTemplate.Status c: values()) {
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
        public static ProjectTemplate.Status fromValue(String value) {
            ProjectTemplate.Status constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }


    /**
     * The type of project template used to indicate how to access and what to use
     * 
     */
    @Generated("jsonschema2pojo")
    public enum Type {

        WEBSITE("website"),
        API("api"),
        DATA_STORAGE("data_storage"),
        TOOL("tool");
        private final String value;
        private final static Map<String, ProjectTemplate.Type> CONSTANTS = new HashMap<String, ProjectTemplate.Type>();

        static {
            for (ProjectTemplate.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        Type(String value) {
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
        public static ProjectTemplate.Type fromValue(String value) {
            ProjectTemplate.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
