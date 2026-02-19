package integrations;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.*;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.apps.AppContactInformation;
import io.klustr.schemas.console.apps.ConsentGroup;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.projects.Project;
import io.klustr.schemas.console.projects.ProjectOwner;
import io.klustr.schemas.integrations.ory.HydraClientRequest;
import io.klustr.integrations.ory.HydraApi;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.*;

@Tag("integration")
public class HyrdaApiIntegrationTest {

    HydraApi api = new HydraApi("https://hydra-admin.dev.klustr.io", "https://hydra.dev.klustr.io");

    @Test
    public void we_can_register_a_client() throws Exception {

        Project project = new Project()
                .withCreationDate(DateTime.now())
                .withId("nike")
                .withName("Nike")
                .withOwner(new ProjectOwner()
                        .withId("terrance.a.snyder@gmail.com"))
                .withDescription("Nike Example App");

        App app = new App()
                .withContact(new AppContactInformation()
                        .withSupportEmail("terrance.a.snyder@gmail.com")
                        .withDeveloperEmails(Lists.newArrayList(
                                "terrance.a.snyder@gmail.com"
                        )))
                .withId("foo")
                .withBrand(new AppBrand()
                        .withLogo(new AppLogo()
                                .withUrl(URI.create("https://static.nike.com/a/images/f_jpg,q_auto:eco/61b4738b-e1e1-4786-8f6c-26aa0008e80b/swoosh-logo-black.png"))
                                .withFilename("nike.png"))
                        .withName("foo"))
                .withConsent(
                        new ConsentGroup()
                                .withScopes(
                                        Lists.newArrayList(
                                                new ConsentScope().withId("profile"),
                                                new ConsentScope().withId("id"),
                                                new ConsentScope().withId("email")
                                        )
                                )
                )
                .withDomains(Lists.newArrayList("rize.fit", "klustr.io"))
                .withLinks(
                        new AppLinks()
                                .withHome(URI.create("http://www.klustr.io"))
                                .withPrivacy(URI.create("http://www.klustr.io/privacy"))
                                .withTos(URI.create("http://www.klustr.io/tos"))
                )
                .withRequestUris(
                        Lists.newArrayList(
                                "http://localhost:3000"
                        )
                );

        project.setApp(app);

        HydraClientRequest req = new HydraClientRequest()
                .withName("my name")
                .withTokenEndpointAuthMethod(TokenEndpointAuthMethod.CLIENT_SECRET_BASIC)
                .withAccessTokenStrategy(AccessTokenStrategy.OPAQUE)
                .withAuthorizedOrigins(Lists.newArrayList("http://localhost:3000"))
                .withAuthorizedOrigins(Lists.newArrayList("http://localhost:3000"));

        HydraApi.OryRegistration result = api.registerClient(
                project,
                req
        );

        assertThat(result.client_id).isNotEmpty();
        assertThat(result.client_secret).isNotEmpty();

    }
}
