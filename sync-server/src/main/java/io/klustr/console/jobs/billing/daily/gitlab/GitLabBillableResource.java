package io.klustr.console.jobs.billing.daily.gitlab;

import io.klustr.billing.BillingCustomerApi;
import io.klustr.billing.BillingUsageApi;
import io.klustr.billing.models.BillingEventForGitLabStorageUsage;
import io.klustr.console.jobs.billing.DailyBillableResource;
import io.klustr.console.storage.Storage;
import io.klustr.integrations.gitlab.GitLabProjectResourceProvider;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.git.GitProjectReference;
import io.klustr.schemas.integrations.git.GitProjectStatistics;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GitLabBillableResource implements DailyBillableResource {

    private GitLabProjectResourceProvider projects;

    public GitLabBillableResource(GitLabProjectResourceProvider projects) {
        this.projects = projects;
    }

    @Override
    public void bill(Project project, BillingUsageApi usageApi) {

        Optional<GitProjectReference> gitLabProject = this.projects.tryGetProject(project.getId());
        if (gitLabProject.isEmpty()) {
            return;
        }

        GitProjectReference obj = gitLabProject.get();
        GitProjectStatistics stats = obj.getStatistics();
        if (stats == null) {
            return;
        }

        Integer size = stats.getRepositorySize();

        BillingEventForGitLabStorageUsage event = BillingEventForGitLabStorageUsage.builder().withProjectId(project.getId())
                .withTimestamp(DateTime.now())
                .withTotal(size).build();

        usageApi.recordUsage(project.getId(),
                event);
    }
}
