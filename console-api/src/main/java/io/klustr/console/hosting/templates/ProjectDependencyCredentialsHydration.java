package io.klustr.console.hosting.templates;

import com.google.common.collect.Lists;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.projects.*;
import io.klustr.utils.DeterministicUsernameGenerator;
import io.klustr.utils.PasswordGenerator;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(4)
public class ProjectDependencyCredentialsHydration implements ProjectDependencyHydration {

    @Override
    public void hydrate(Storage storage, String org_id, String projectId, ProjectTemplate template, EnvironmentVars env) {
        if (template.getRequiredFeatures() == null) return;
        if (template.getRequiredFeatures().getCredentials() == null) return;
        if (template.getRequiredFeatures().getCredentials().getBasicCredentials() == null) return;  // only basic now

        TemplateCredentialsBasicConfiguration basicCredentials = template.getRequiredFeatures().getCredentials().getBasicCredentials();
        hydrateBasicCredential(storage.projects().getObject(projectId), basicCredentials, storage, env);
    }

    private void hydrateBasicCredential(Project project, TemplateCredentialsBasicConfiguration config, Storage storage, EnvironmentVars env) {

        // make it for later checking if exists
        ProjectSecret secret = new ProjectSecret()
                .withId(config.getId())
                .withLabel(config.getLabel())
                .withBasicCredentials(new ProjectSecretBasicCredential()
                        .withUsername(config.getUsername() != null ? config.getUsername() : DeterministicUsernameGenerator.generate(project.getId()))
                        .withPassword(PasswordGenerator.generate(config.getPasswordLength() != null ? config.getPasswordLength() : 12))
                );

        if (project.getSecrets() == null) {
            project.withSecrets(Lists.newArrayList(secret));
            storage.projects().updateObject(project.getId(), project);

            env.add(config.getEnvVarUsername(), secret.getBasicCredentials().getUsername());
            env.add(config.getEnvVarPassword(), secret.getBasicCredentials().getPassword());

            return;
        }

        Optional<ProjectSecret> match = project.getSecrets().stream().filter(x -> {
            return x.getId().equalsIgnoreCase(secret.getId());
        }).findFirst();

        if (match.isEmpty()) {
            project.getSecrets().add(secret);
            storage.projects().updateObject(project.getId(), project);

            env.add(config.getEnvVarUsername(), secret.getBasicCredentials().getUsername());
            env.add(config.getEnvVarPassword(), secret.getBasicCredentials().getPassword());

            return;
        }

        // match exists populate our env vars
        env.add(config.getEnvVarUsername(), match.get().getBasicCredentials().getUsername());
        env.add(config.getEnvVarPassword(),  match.get().getBasicCredentials().getPassword());
    }
}
