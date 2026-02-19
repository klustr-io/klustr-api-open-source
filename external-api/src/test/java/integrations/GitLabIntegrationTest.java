package integrations;

import io.klustr.console.storage.Storage;
import io.klustr.git.AccessLevel;
import io.klustr.integrations.gitlab.*;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.integrations.git.*;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.utils.Json;
import io.klustr.utils.U;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

// TODO inject via spring
@Tag("integration")
public class GitLabIntegrationTest {

    private static class TestConfiguration implements GitLabConfigurationProvider {
        @Override
        public GitLabConfiguration getConfiguration() {
            String GITLAB_API_KEY = System.getenv("GITLAB_API_KEY");
            return new GitLabConfiguration(GitLabConfiguration.default_url,  GITLAB_API_KEY);
        }
    }

    private final GitLabUserResourceProvider gitLabUsers = new GitLabUserResourceProvider(new TestConfiguration());

    private final GitLabGroupResourceProvider gitLabGroups = new GitLabGroupResourceProvider(new TestConfiguration());

    private final GitLabGroupMembershipProvider gitLabMembership = new GitLabGroupMembershipProvider(new TestConfiguration());

    private final GitLabProjectResourceProvider gitLabProjects = new GitLabProjectResourceProvider(new TestConfiguration());

    private final GitLabProjectPipelineResourceProvider gitLabPipelines = new GitLabProjectPipelineResourceProvider(new TestConfiguration());

    private final GitLabProjectDockerImageReferenceProvider gitLabRepos = new GitLabProjectDockerImageReferenceProvider(new TestConfiguration());

    @Test
    public void vaccum() {
        CompositeMeterRegistry metrics = new CompositeMeterRegistry();
        RethinkDbConnectionPool pool = new RethinkDbConnectionPool("rethinkdb.dev.klustr.io", 28015, metrics);
        Storage s = new Storage(new RethinkDbDocumentDatabaseFactory(pool), mock(DbAdapterBroadcasterFactory.class), metrics);
        // read from kafka, deleted projects
        // for every deleted project, delete it
        // have multiple listeners for (git, kong, etc)
        this.gitLabProjects.listProjects().forEach(x -> {

            if (x.getPathWithNamespace().toLowerCase().contains("klustr")) {
                return;
            }
            if (x.getPath().toLowerCase().contains("klustr")) {
                return;
            }

            // ([a-z0-9]*)-([a-z0-9]*)-([a-z0-9]*)
            if (x.getName().matches("([a-z0-9]*)-([a-z0-9]*)-([a-z0-9]*)")) {
                    Project project = s.projects().getObject(x.getPath());
                    if (project == null) {
                        try {
                            System.out.println(x.getPath());
                            gitLabRepos.deleteRepository(x);
                            gitLabProjects.deleteProject(x);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
    }

    @Test
    public void we_can_list_projects() throws Exception {
        List<GitProjectReference> projects = gitLabProjects.listProjects();
        assertThat(projects).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(projects));
    }

    @Test
    public void we_can_search_project_by_path() throws Exception {
        // p();
        Optional<GitProjectReference> match = this.gitLabProjects.tryGetProject("itchy-alligator-6a7d70");
        GitProjectReference p = this.gitLabProjects.getProjectById(match.get().getId().toString());

        this.gitLabProjects.updateProject(match.get().getId().toString(), new GitUpdateProjectRequest()
                .withAutoDevopsDeployStrategy("continuous")
                .withAutoDevopsEnabled(true)
        );
        System.out.println(U.toJsonPrettyFormat(p));
    }

    @Test
    public void we_can_find_by_group() throws Exception {
        we_can_list_projects();

        List<GitProjectReference> projects = this.gitLabProjects.listProjectsInGroup("rize.fit");
        assertThat(projects.size()).isGreaterThan(0);
    }

    @Test
    public void we_can_create_project_if_not_exists() throws Exception {
        we_can_create_group_if_not_exists();
        we_can_create_user_if_not_exists();

        GitGroupReference groupRef = gitLabGroups.getGroupByPath(GROUP_PATH).get();
        GitUserReference userRef = gitLabUsers.getByEmail(userEmail).get();
        Optional<GitProjectReference> match = gitLabProjects.listProjects().stream().filter(x -> {
            return x.getName().equalsIgnoreCase("Unit Test");
        }).findFirst();
        if (match.isEmpty()) {
            GitProjectReference projectRef = gitLabProjects.createProject(new GitCreateProjectRequest()
                    .withName("Unit Test")
                    .withPath("unit-test")
                    .withDescription("A test project created via Unit Tests")
                    .withInitializeWithReadme(true)
                    .withNamespaceId(groupRef.getId())
                    .withUserId(userRef.getId())
                    .withIssuesEnabled(true)
                    .withVisibility(GitCreateProjectRequest.Visibility.PRIVATE)
                    .withWikiEnabled(true)
            );
            assertThat(projectRef.getId()).isNotNull();
            assertThat(projectRef.getName()).isEqualTo("Test");
            assertThat(projectRef.getHttpUrlToRepo()).isNotNull();

            System.out.println(Json.toJsonPrettyFormat(projectRef));
        }
    }

    @Test
    public void we_can_link_user_to_oidc() throws Exception {
        gitLabUsers.linkOpenIdProvider("terrance.a.snyder@gmail.com", "621b3e3c-2ae7-494b-ad83-e9e37618bfd4");
    }

    @Test
    public void we_can_list_groups() throws Exception {
        List<GitGroupReference> ref = gitLabGroups.listGroups(Pagination.all());
        assertThat(ref).isNotEmpty();
        assertThat(ref.get(0).getName()).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(ref));
    }

    public static final String GROUP_NAME = "Foo Test";
    public static final String GROUP_PATH = "foo";


    @Test
    public void we_can_create_group_if_not_exists() throws Exception {
        Optional<GitGroupReference> match = gitLabGroups.getGroupByPath(GROUP_PATH);
        if (match.isEmpty()) {
            GitGroupReference organization = gitLabGroups.createGroup(new GitCreateGroupRequest()
                    .withPath(GROUP_PATH)
                    .withName(GROUP_NAME)
            );
            assertThat(organization.getId()).isNotNull();
            assertThat(organization.getName()).isNotEmpty();
            match = gitLabGroups.getGroupByPath(GROUP_PATH);
            assertThat(match.isPresent()).isTrue();
        }
    }

    @Test
    public void we_can_check_and_then_add_user_to_group() {
        Optional<GitGroupReference> groupRef = this.gitLabGroups.getGroupByPath(GROUP_PATH);
        Optional<GitUserReference> userRef = this.gitLabUsers.getByEmail("test@klustr.io");
        assertThat(groupRef.isPresent()).isTrue();
        assertThat(userRef.isPresent()).isTrue();

        Optional<GitLabGroupMembershipProvider.GroupMember> match = this.gitLabMembership.getGroupMemberByEmail(groupRef.get(), userRef.get());
        if (match.isEmpty()) {
            gitLabMembership.addGroupMember(groupRef.get(), userRef.get(), AccessLevel.DEVELOPER);
        }
        List<GitLabGroupMembershipProvider.GroupMember> groupMembers = this.gitLabMembership.listGroupMembers(groupRef.get());
        assertThat(groupMembers.stream().anyMatch(x -> x.getId() == userRef.get().getId())).isTrue();
    }

    @Test
    public void we_can_set_permissions() throws Exception {
        // ensure setup data
        we_can_create_user_if_not_exists();
        we_can_create_group_if_not_exists();
        we_can_check_and_then_add_user_to_group();

        Optional<GitGroupReference> groupRef = this.gitLabGroups.getGroupByPath(GROUP_PATH);
        Optional<GitUserReference> userRef = this.gitLabUsers.getByEmail("test@klustr.io");
        assertThat(groupRef.isPresent()).isTrue();
        assertThat(userRef.isPresent()).isTrue();

        Optional<GitLabGroupMembershipProvider.GroupMember> membership = this.gitLabMembership.getGroupMemberByEmail(groupRef.get(), userRef.get());
        assertThat(membership.get().access_level.getLevel()).isEqualTo(AccessLevel.DEVELOPER.getLevel());

        // set no access
        this.gitLabMembership.setUserAccessLevel(groupRef.get(), userRef.get(), AccessLevel.OWNER);
        membership = this.gitLabMembership.getGroupMemberByEmail(groupRef.get(), userRef.get());
        assertThat(membership.get().access_level.getLevel()).isEqualTo(AccessLevel.OWNER.getLevel());
    }

    @Test
    public void we_can_list_users() throws Exception {
        List<GitUserReference> users = gitLabUsers.listUsers(Pagination.all());
        assertThat(users).isNotEmpty();
        assertThat(users.get(0).getEmail()).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(users));
        // https://kratos-admin.dev.klustr.io/admin/identities/d2c23e1c-3b02-4bdf-8c3d-ae13de15aa5c
    }

    String userEmail = "test@klustr.io";

    @Test
    public void we_can_create_user_if_not_exists() throws Exception {

        Optional<GitUserReference> match = gitLabUsers.getByEmail(userEmail);
        if (match.isEmpty()) {
            GitUserReference result = gitLabUsers.createUser(new GitCreateUserRequest()
                    .withName("Test")
                    .withEmail(userEmail)
                    .withExternUid("test")
                    .withProvider("test")
                    .withOrganization("my_org")
                    .withUsername("test")
            );
            assertThat(result.getEmail()).isNotEmpty();
            assertThat(result.getId()).isNotNull();

            match = gitLabUsers.getByEmail(userEmail);
            assertThat(match.isEmpty()).isFalse();
        }
    }

    @Test
    public void we_can_update_the_avatar() throws Exception {
        we_can_create_user_if_not_exists();
        Optional<GitUserReference> user = gitLabUsers.getByEmail(userEmail);

        // png
        this.gitLabUsers.updateAvatar(user.get().getId().toString(),
                "https://cdn.dev.klustr.io/profiles/faces/0a7c4bc9a5ff33340b1e0c8340eaa126.png");
        Thread.sleep(2000);

        // webp
        this.gitLabUsers.updateAvatar(user.get().getId().toString(),
                "https://cdn.dev.klustr.io/klustr/3e84f483b113a9556cd347fd148c3669.webp");
    }

    @Test
    public void get_stats() throws Exception {
        Optional<GitProjectReference> web = this.gitLabProjects.tryGetProject("rize_website");
        assertThat(web.isPresent()).isTrue();
        assertThat(web.get().getStatistics()).isNotNull();
        assertThat(web.get().getStatistics().getLanguages().isEmpty()).isFalse();
    }

    @Test
    public void we_can_get_latest_pipeline() throws Exception {
        Optional<GitProjectReference> ref = this.gitLabProjects.tryGetProject("rize_website");
        Optional<GitPipelineReference> pipeline = this.gitLabPipelines.getLatestPipeline(ref.get());
        assertThat(pipeline.isPresent()).isTrue();
        assertThat(pipeline.get().getId()).isGreaterThan(0);
        assertThat(pipeline.get().getStatus()).isNotNull();
        System.out.println(Json.toJsonPrettyFormat(pipeline.get()));

        List<GitPipelineReference> pipelines = this.gitLabPipelines.getPipelines(ref.get());
        assertThat(pipelines.size()).isGreaterThan(0);


        List<GitPipelineStageReference> stages = this.gitLabPipelines.getPipelineDetails(pipeline.get());
        assertThat(stages).isNotEmpty();
        System.out.println(Json.toJsonPrettyFormat(stages));

        // get a log file
        String trace = this.gitLabPipelines.getPipelineRawStepLogFile(ref.get(), stages.get(0).getJobs().get(0));
        System.out.println(trace);
    }

    @Test
    public void we_can_delete_a_user_from_group() throws Exception {
        // ensure setup data
        we_can_create_user_if_not_exists();
        we_can_create_group_if_not_exists();
        we_can_check_and_then_add_user_to_group();

        // references
        Optional<GitGroupReference> groupRef = this.gitLabGroups.getGroupByPath(GROUP_PATH);
        Optional<GitUserReference> userRef = this.gitLabUsers.getByEmail("test@klustr.io");
        assertThat(groupRef.isPresent()).isTrue();
        assertThat(userRef.isPresent()).isTrue();

        this.gitLabMembership.removeGroupMember(groupRef.get(), userRef.get());

        Optional<GitLabGroupMembershipProvider.GroupMember> membership = this.gitLabMembership.getGroupMemberByEmail(groupRef.get(), userRef.get());
        assertThat(membership.isEmpty()).isTrue();
    }

    @Test
    public void full_end_2_end() throws Exception {
        String user_id = UUID.randomUUID().toString();
        GitUserReference result = gitLabUsers.createUser(new GitCreateUserRequest()
                .withName("Test")
                .withEmail("test-" + user_id + "@klustr.io")
                .withExternUid(user_id)
                .withProvider("test")
                .withOrganization("my_org")
                .withUsername(user_id)
        );
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCanCreateGroup()).isFalse();
        assertThat(result.getCanCreateProject()).isFalse();
    }
}
