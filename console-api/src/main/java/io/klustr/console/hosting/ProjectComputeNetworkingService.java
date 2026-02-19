package io.klustr.console.hosting;

import com.google.common.collect.Lists;
import io.klustr.compute.ProjectTemplateProvider;
import io.klustr.compute.dns.ComputeDnsProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerProvider;
import io.klustr.compute.ingress.ComputeLoadBalancerResolver;
import io.klustr.console.storage.Storage;
import io.klustr.git.GitProjectDockerImageReferenceProvider;
import io.klustr.git.GitProjectResourceProvider;
import io.klustr.integrations.gitlab.GitLabProjectDockerImageReferenceProvider;
import io.klustr.integrations.haproxy.acls.*;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectTemplate;
import io.klustr.schemas.integrations.compute.*;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.utils.U;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Returns information regarding the status of the ingress for a given project.
 */
@RestController
@Component
@RequestMapping("/console/projects")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectComputeNetworkingService {

    private static final Logger log = LoggerFactory.getLogger(ProjectComputeNetworkingService.class);

    private final Storage storage;

    private final PortainerComputeResourceProvider computeProvider;
    private final ComputeLoadBalancerResolver loadbalancers;
    private final GitProjectDockerImageReferenceProvider images;
    private final GitProjectResourceProvider git;
    private final ComputeDnsProvider dns;
    private final ProjectTemplateProvider templates;

    public ProjectComputeNetworkingService(Storage storage,
                                           GitLabProjectDockerImageReferenceProvider images,
                                           GitProjectResourceProvider git,
                                           PortainerComputeResourceProvider computeProvider,
                                           ComputeDnsProvider dns,
                                           ProjectTemplateProvider templates,
                                           ComputeLoadBalancerResolver loadbalancers) {
        this.storage = storage;
        this.images = images;
        this.git = git;
        this.loadbalancers = loadbalancers;
        this.dns = dns;
        this.computeProvider = computeProvider;
        this.templates = templates;
    }

    @GetMapping("/{projectId}/ingress")
    @Operation(
            operationId = "getProjectNetworkingIngressStatus", summary = "Examines the projects current container and sees if ingress is enabled and its status.",
            description = """
                    In order for a project to be accessible externally there must be a configured ingress into the container. This will return the  current 
                    status (dns, ip, routing) so that a person can see if there needs to be a configuration change or activation or disable of this ingress.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId", description = "The project ID")
            }
    )
    public ComputeIngress getProjectNetworkingIngressStatus(@PathVariable("projectId") String projectId,
                                                            @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        ComputeLoadBalancerProvider lb = this.loadbalancers.resolve(projectId, project.getOrgId());
        return lb.getStatus(projectId);
    }

    @PostMapping("/{projectId}/ingress/dns")
    @Operation(
            operationId = "dns", summary = "Register the DNS entry for the container.",
            description = """
                    Given a specific project will register the public ip for later use
                    by the ingress.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId", description = "The project ID"),
                    @Parameter(required = false, name = "wait_ms", description = "Wait until the server itself sees the DNS update useful to block until DNS is resolved.")
            }
    )
    public void dns(@PathVariable("projectId") String projectId,
                    @RequestParam(value = "wait_ms", required = false, defaultValue = "10000") Integer wait_ms,
                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {
        Project project = this.storage.projects().getObject(projectId);
        String zone = "dev.klustr.io.";

        ComputeLoadBalancerProvider lb = this.loadbalancers.resolve(projectId, project.getOrgId());
        Set<String> ips = lb.getPublicIPV4Addresses();
        List<ComputeDnsZoneRecord> records = ips.stream().map(ip -> {
            return new ComputeDnsZoneRecord().withContent(ip).withDisabled(false);
        }).toList();

        // register DNS alias
        this.dns.createRecords(zone, Lists.newArrayList(
                new ComputeDnsZoneRequest()
                        .withName(projectId + "." + zone)
                        .withType("A")
                        .withTtl(60)
                        .withRecords(records)
        ));

        U.waitForDnsOrExit(projectId + ".dev.klustr.io", wait_ms);
    }

    @PostMapping("/{projectId}/ingress/public")
    @Operation(
            operationId = "publish", summary = "Enable public ingress",
            description = """
                    Given a specific project will examine the template associated and configure
                    the instance so it can be connected to through the public ingress. This could mean
                    exposing the instance over HTTPS or in other instances for example mongo, the default
                    mongo port.
                    
                    If your corporation has a default security policy, firewall rules will still apply
                    so that you "public" is still effectively private.
                    """,
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE),
            parameters = {
                    @Parameter(required = true, name = "projectId", description = "The project ID"),
            }
    )
    public void publish(@PathVariable("projectId") String projectId,
                        @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        Project project = this.storage.projects().getObject(projectId);

        if (StringUtils.isBlank(project.getTemplateId())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The project does not have a template assigned to it."
            );
        }

        Optional<ProjectTemplate> template = this.templates.getTemplate(project.getTemplateId());
        if (template.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "The project template could not be resolved '" + project.getTemplateId() + "'"
            );
        }

        ProjectTemplate tpl = template.get();

        // given protocol = http, or protocol = tcp
        // sni or other for routing
        // create the backend and map to port
        ComputeLoadBalancerProvider route = this.loadbalancers.resolve(projectId, project.getOrgId());
        ComputeIngress status = route.getStatus(projectId);

        String zone = "dev.klustr.io.";

        ComputeLoadBalancerProvider lb = this.loadbalancers.resolve(projectId, project.getOrgId());
        Set<String> ips = lb.getPublicIPV4Addresses();
        List<ComputeDnsZoneRecord> records = ips.stream().map(ip -> {
            return new ComputeDnsZoneRecord().withContent(ip).withDisabled(false);
        }).toList();

        // register DNS alias
        this.dns.createRecords(zone, Lists.newArrayList(
                new ComputeDnsZoneRequest()
                        .withName(projectId + "." + zone)
                        .withType("A")
                        .withTtl(60)
                        .withRecords(records)
        ));

        io.klustr.compute.ingress.FrontEnd fe = route.guessFrontEndFromPort(tpl.getPort());

        // create our route
        String backend_name = projectId;
        String backend_server_name = projectId + "_server";

        route.createBackendIfNotExists(new LoadBalancerBackendResource()
                .withName(backend_name)
                .withMode(tpl.getProtocol() == ProjectTemplate.Protocol.HTTP ? LoadBalancerBackendResource.Mode.HTTP : LoadBalancerBackendResource.Mode.TCP)
        );

        route.addServerToBackend(backend_name, new LoadBalancerBackendServer()
                .withName(backend_server_name)
                .withPort(tpl.getPort())
                .withAddress(projectId)
        );

        String acl_name = backend_name + "_acl";
        LoadBalancerAcl acl = null;
        if (tpl.getResolver() == ProjectTemplate.Resolver.HTTP_HOSTNAME_HEADER) {
            route.addAclIfNotExists(fe, acl_name, new HostnameAcl(projectId));
        } else if (tpl.getResolver() == ProjectTemplate.Resolver.SSL_FC_SNI) {
            route.addAclIfNotExists(fe, acl_name, new SSL_FC_ServerNameIndicationAcl(projectId));
        } else if (tpl.getResolver() == ProjectTemplate.Resolver.REQ_SSL_SNI) {
            route.addAclIfNotExists(fe, acl_name, new SSL_REQ_ServerNameIndicationAcl(projectId));
        } else {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "The project template uses an unknown resolver of '" + tpl.getResolver() + "'"
            );
        }

        route.addBackendSwitchingRule(fe, new LoadBalancerMatchesAclCondition().withAcl(acl_name).withBackend(backend_name));
    }
}
