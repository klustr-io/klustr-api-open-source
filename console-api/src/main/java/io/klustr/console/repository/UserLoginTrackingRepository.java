package io.klustr.console.repository;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.stats.ProjectStats;
import io.klustr.schemas.console.stats.ProjectUserReference;
import io.klustr.schemas.console.stats.ProjectUsers;
import io.klustr.console.storage.Storage;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Record stats and aggregations about a projects usage
 * and customers.
 */
@Component
public class UserLoginTrackingRepository {
    private static final Logger log = LoggerFactory.getLogger(UserLoginTrackingRepository.class);

    private final Storage storage;
    private final Counter login;

    public UserLoginTrackingRepository(Storage storage, MeterRegistry registry) {
        this.storage = storage;
        this.login = registry.counter("track.login");
    }

    public void trackUserSignIn(String projectId, String clientId, String userId) {
        this.login.increment();

        ProjectUsers projectUsers = this.storage.project_users().getObject(projectId);
        if (projectUsers == null) {
            projectUsers = new ProjectUsers()
                    .withClientId(clientId)
                    .withId(projectId)
                    .withProjectId(projectId)
                    .withUsers(Lists.newArrayList());
            this.storage.project_users().insertObject(projectId, projectUsers);
        }

        Optional<ProjectUserReference> existingUser = projectUsers.getUsers().stream().filter(x -> x.getId().equalsIgnoreCase(userId)).findFirst();
        boolean isNewUser = existingUser.isEmpty();
        if (isNewUser) {
            projectUsers.getUsers().add(new ProjectUserReference()
                    .withFirstAccess(new DateTime())
                    .withLastAccess(new DateTime())
                    .withId(userId)
                    .withTotalVisits(1d)
            );
        } else {
            existingUser.get().setTotalVisits(existingUser.get().getTotalVisits() + 1);
            existingUser.get().setLastAccess(DateTime.now());
        }
        this.storage.project_users().updateObject(projectId, projectUsers);
    }

}
