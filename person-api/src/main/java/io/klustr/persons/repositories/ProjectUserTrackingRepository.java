package io.klustr.persons.repositories;

import io.klustr.schemas.console.stats.ProjectStats;
import io.klustr.console.storage.Storage;
import io.klustr.schemas.console.stats.ProjectUsers;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Will enable a project to get stats on the total number of users.
 */
@Component
public class ProjectUserTrackingRepository {

    private final Storage storage;

    public ProjectUserTrackingRepository(Storage storage) {
        this.storage = storage;
    }

    public Optional<ProjectUsers> getUsers(String projectId) {
        ProjectUsers projectUsers = this.storage.project_users().getObject(projectId);
        if (projectUsers == null) {
            return Optional.empty();
        }
        return Optional.of(projectUsers);
    }

    public Optional<ProjectStats> getStats(String id) {
        ProjectUsers users = this.storage.project_users().getObject(id);
        if (users == null) {
            return Optional.empty();
        } else {
            ProjectStats result = new ProjectStats()
                    .withId(users.getId())
                    .withProjectId(users.getProjectId())
                    .withUniqueUsers(users.getUsers().size())
                    .withClientId(users.getClientId());
            return Optional.of(result);
        }
    }

}
