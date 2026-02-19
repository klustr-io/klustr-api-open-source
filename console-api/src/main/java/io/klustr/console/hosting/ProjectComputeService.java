package io.klustr.console.hosting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.klustr.compute.MockProjectTemplateRepository;
import io.klustr.compute.ProjectTemplateProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.console.hosting.templates.EnvironmentVars;
import io.klustr.console.hosting.templates.ProjectDependencyHydrationRunner;
import io.klustr.console.storage.Storage;
import io.klustr.git.GitProjectDockerImageReferenceProvider;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.gitlab.GitLabProjectDockerImageReferenceProvider;
import io.klustr.integrations.haproxy.HaproxyComputeLoadbalancerProvider;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.integrations.portainer.PortainerPostgresSharedInstanceProvider;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLogo;
import io.klustr.schemas.console.apps.*;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectServiceReference;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.git.GitProjectDockerImageReference;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.portainer.Port;
import io.klustr.schemas.integrations.portainer.PortainerComputeReference;
import io.klustr.spring.OAuthCredentialType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Enables the interaction and deployment of compute resources for a project.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectComputeService {

    private static final Logger log = LoggerFactory.getLogger(ProjectComputeService.class);

    private final Storage storage;

    private final PortainerComputeResourceProvider compute;
    private final GitProjectDockerImageReferenceProvider images;
    private final GitProjectResourceProvider git;
    private final ProjectTemplateProvider templates;
    private final ProjectDependencyHydrationRunner hydrator;

    public ProjectComputeService(Storage storage,
                                 GitLabProjectDockerImageReferenceProvider images,
                                 GitProjectResourceProvider git,
                                 ProjectTemplateProvider templates,
                                 PortainerComputeResourceProvider compute,
                                 ProjectDependencyHydrationRunner hydrator) {
        this.storage = storage;
        this.images = images;
        this.git = git;
        this.compute = compute;
        this.templates = templates;
        this.hydrator = hydrator;
    }

    @GetMapping("/{projectId}/containers")
    @Operation(
            description = """
This endpoint provides details on a compute instance owned by the project. You can use this to check which templates are running, the machine type, and any related port and ingress information.
""",
            operationId = "getInstance",
            summary = "Retrieve instance details for your compute",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public ResponseEntity<ComputeReferenceEntity> getInstance(@PathVariable("projectId") String projectId,
                                                                 @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);

        List<PortainerComputeReference> nodes = this.compute.getComputeNodes(project.getOrgId(), projectId);
        if (nodes.isEmpty()) {
            if (StringUtils.isNotBlank(project.getTemplateId())) {
                ComputeReferenceEntity x = new ComputeReferenceEntity();
                x.template = this.templates.getTemplate(project.getTemplateId()).orElse(null);
                return ResponseEntity.ok(x);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        PortainerComputeReference ref = nodes.get(0);
        ComputeReferenceEntity e = new ComputeReferenceEntity();
        e.id = ref.getId();
        e.image_id =ref.getImageID();
        e.image = ref.getImage();
        e.created = new DateTime((long) ref.getCreated() * 1000, DateTimeZone.UTC);
        e.private_ports = ref.getPorts() != null ? ref.getPorts().stream().map(Port::getPrivatePort).collect(Collectors.toSet()) : null;
        e.public_ports = ref.getPorts() != null ? ref.getPorts().stream().map(Port::getPublicPort).collect(Collectors.toSet()) : null;

        if (StringUtils.isNotBlank(project.getTemplateId())) {
            e.template = this.templates.getTemplate(project.getTemplateId()).orElse(null);
        }

        if (e.template != null && e.template.getExposePort() != null && e.template.getExposePort() == true) {
            String endpoint = e.template.getEndpointUrl();
            endpoint = StringUtils.replace(endpoint, "{projectId}", projectId);
            if (e.public_ports != null && !e.public_ports.isEmpty()) {
                String portvalue = String.valueOf(e.public_ports.stream().findFirst().get());
                endpoint = StringUtils.replace(endpoint, "{port}", portvalue);
            }
            e.endpoint = endpoint;
        }

        return ResponseEntity.ok(e);
    }

    public static class ComputeReferenceEntity {
        public String id;
        public DateTime created;
        public String image_id;
        public String image;
        public ProjectTemplate template;
        public String endpoint;
        public Set<Integer> private_ports = Sets.newConcurrentHashSet();
        public Set<Integer> public_ports = Sets.newConcurrentHashSet();
    }

    @PostMapping("/{projectId}/containers/provision")
    @Operation(
            operationId = "deployDockerProject", summary = "Provision Project Compute",
            description = """
This will create and setup the container as well as deploy the project and run it in an instance configured. After complete
the instance will be available and you can decide to expose it over ingress.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId",description = "The project ID"),
                    @Parameter(required = true, name = "image",description = "The image to deploy to this container, if applicable for the template."),
                    @Parameter(required = true, name = "template_id",description = "The template to use."),
                    @Parameter(required = true, name = "machineType",description = "The type of machine to deploy, see /.metadata machine names for types of machine."),
            }
    )
    public void deploy(@PathVariable("projectId") String projectId,
                                          @RequestParam(value = "image", required = false) String image,
                                          @RequestParam(value = "machineType", required = false, defaultValue = "m1.small") String machineType,
                       @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);
        String org_id = project.getOrgId();

        String templateId = project.getTemplateId();

        Optional<ProjectTemplate> template = new MockProjectTemplateRepository().getTemplates().stream().filter(x -> x.getId().equalsIgnoreCase(templateId)).findFirst();
        if (template.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The specified template could not be found."
            );
        }
        ProjectTemplate tpl = template.get();
        if (tpl.getRequiresImage()) {
            if (StringUtils.isBlank(image)) {
                // lookup latest template
                Optional<GitProjectReference> ref = this.git.tryGetProject(projectId);
                if (ref.isEmpty()) {
                    throw new RuntimeException("Provision requires an image name and none was found inside project '" + projectId + "'");
                }
                GitProjectDockerImageReference rs = this.images.getLastestImage(ref.get());
                if (rs == null) {
                    throw new RuntimeException("Provision requires an image name and none was found inside project '" + projectId + "'");
                }
                image = "sha256:" + rs.getRevision();
            }
        }

        // we have our template but sometimes templates require
        // additional dependencies, for now depende
        EnvironmentVars environmentVars = hydrator.runHydration(storage, org_id, projectId, tpl);

        Provisioner p = new PortainerProvisioner(storage, compute);
        p.provision(org_id, projectId, tpl, machineType, environmentVars, image != null ? Optional.of(image) : Optional.empty());
    }

    public static class PostgreDatabaseProvision implements Provisioner {
        private final PortainerPostgresSharedInstanceProvider db;
        private final Storage storage;

        public PostgreDatabaseProvision(Storage storage, PortainerComputeResourceProvider compute) {
            this.db = new PortainerPostgresSharedInstanceProvider(compute);
            this.storage = storage;
        }

        @Override
        public void provision(String orgId, String projectId, ProjectTemplate template, String machineType, EnvironmentVars vars, Optional<String> dockerImage) {

            Project project = this.storage.projects().getObject(projectId);

            if (project.getServices() == null) {
                project.withServices(Lists.newArrayList());
            }

            Optional<ProjectServiceReference> match = project.getServices().stream().filter(x -> {
                return x.getId().equalsIgnoreCase("postgres-database");
            }).findFirst();
            if (match.isPresent()) return;  // already provisioned

            String username = vars.getVars().get("USERNAME");
            String password = vars.getVars().get("PASSWORD");
            PortainerPostgresSharedInstanceProvider.Result re = this.db.provision(projectId, username, password);

                project.getServices().add(
                        new ProjectServiceReference()
                                .withId("postgres-database")
                                .withType(ProjectServiceReference.Type.DATABASE)
                                .withUri(re.endpoint)
                );
                storage.projects().updateObject(projectId, project);
        }
    }

    public class PortainerProvisioner implements  Provisioner{

        private final PortainerComputeResourceProvider compute;
        private final Storage storage;

        public PortainerProvisioner(Storage storage, PortainerComputeResourceProvider compute) {
            this.compute = compute;
            this.storage = storage;
        }


        @Override
        public void provision(String orgId, String projectId, ProjectTemplate template, String machineType, EnvironmentVars vars, Optional<String> dockerImage) {
            this.compute.provision(orgId, projectId, template, machineType, vars != null ? vars.getVars() : Maps.newConcurrentMap(), dockerImage);
        }
    }

    public interface Provisioner {
        public void provision(String orgId, String projectId, ProjectTemplate template, String machineType, EnvironmentVars vars, Optional<String> dockerImage);
    }

}
