package io.klustr.console.hosting;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.console.storage.Storage;
import io.klustr.permissions.PrincipleUtils;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.git.GitProjectDockerImageReference;
import io.klustr.spring.OAuthCredentialType;
import io.klustr.storage.DocumentResult;
import io.klustr.storage.Pagination;
import io.klustr.storage.query.Db;
import io.klustr.utils.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@Component
@RequestMapping("/console/compute")
@Tag(name = "Compute APIs",
        description = "Creating and managing accounts, projects, and clients for development.")
public class ProjectComputeSSHKeysService {

    private final Storage storage;

    public ProjectComputeSSHKeysService(Storage storage) {
        this.storage = storage;
    }


    @GetMapping("/sshkeys")
    @Operation(
            operationId = "getSshKeys", summary = "Returns the available docker images for this project.",
            description = """
Will return any configured docker images for this project.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<SSHKeyRegistration> getSshKeys(@AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO real auth
        String orgId = PrincipleUtils.tryGetOrgId(oauth);
        String projectId = PrincipleUtils.tryGetProjectId(oauth);

        DocumentResult<Project> docs = this.storage.projects().insecureQuery(Db.query("org_id").eq(orgId), Pagination.all());

        List<SSHKeyRegistration> results = Lists.newArrayList();

        DocumentResult<JsonNode> keys = this.storage.cert_keys().insecureQuery(Db.all(), Pagination.all());

        docs.docs.forEach(p -> {
            SSHKeyRegistration key = new SSHKeyRegistration().withProjectId(p.getId());
            Set<String> publicKeys = Sets.newConcurrentHashSet();
            keys.docs.forEach(json -> {
                SSHKeyRegistration item = Json.parse(json, SSHKeyRegistration.class);
                publicKeys.addAll(item.public_keys);
            });
            key.withKeys(publicKeys.stream().toList());
            results.add(key);
        });

        return results;
    }

    @GetMapping("/sshkeys/{orgId}")
    @Operation(
            operationId = "getSshKeys", summary = "Returns the available docker images for this project.",
            description = """
Will return any configured docker images for this project.
""",
            security = @SecurityRequirement(name = OAuthCredentialType.USER_TO_SERVICE)
    )
    public List<SSHKeyRegistration> getSshKeys(@PathVariable("orgId") String orgId,
                                               @AuthenticationPrincipal OAuth2AuthenticatedPrincipal oauth) {

        // TODO verify org id is owned by oauth token

        DocumentResult<Project> docs = this.storage.projects().insecureQuery(Db.query("org_id").eq(orgId), Pagination.all());

        List<SSHKeyRegistration> results = Lists.newArrayList();

        DocumentResult<JsonNode> keys = this.storage.cert_keys().insecureQuery(Db.all(), Pagination.all());

        docs.docs.forEach(p -> {
            SSHKeyRegistration key = new SSHKeyRegistration().withProjectId(p.getId());
            Set<String> publicKeys = Sets.newConcurrentHashSet();
            keys.docs.forEach(json -> {
                SSHKeyRegistration item = Json.parse(json, SSHKeyRegistration.class);
                publicKeys.addAll(item.public_keys);
            });
            key.withKeys(publicKeys.stream().toList());
            results.add(key);
        });

        return results;
    }

    public static class SSHKeyRegistration {
        public String project_id;
        public List<String> public_keys = Lists.newArrayList();

        public SSHKeyRegistration() { }

        public SSHKeyRegistration withProjectId(String projectId) {
            this.project_id = projectId;
            return this;
        }

        public SSHKeyRegistration withKeys(List<String> keys) {
            this.public_keys = keys;
            return this;
        }

        public SSHKeyRegistration withKey(String key) {
            this.public_keys.add(key);
            return this;
        }
    }
}
