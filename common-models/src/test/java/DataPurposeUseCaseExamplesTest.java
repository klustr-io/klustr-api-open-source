import com.google.common.collect.Lists;
import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLogo;
import io.klustr.schemas.console.EntityReference;
import io.klustr.schemas.console.agreements.*;
import io.klustr.schemas.console.apps.App;
import io.klustr.schemas.console.apps.ConsentGroup;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public class DataPurposeUseCaseExamplesTest {

    @Test
    public void nike() {


        ArrayList<EntityReference> scopes = Lists.newArrayList(
                new EntityReference().withId( "profile"),
                new EntityReference().withId( "fitness.heartrate.read"),
                new EntityReference().withId( "fitness.weight.read"),
                new EntityReference().withId( "fitness.steps.read"));

        Agreement agreement = new Agreement()
                .withId("agreement1234")
                .withDateCreated(DateTime.now().withDayOfMonth(1))
                .withDateModified(DateTime.now())
                .withStatus(Agreement.Status.PUBLISHED)
                .withDescription("Agreement to share data with nike that expires in a month.")
                .withName("Nike+ Experiment")
                .withVersions(
                        Lists.newArrayList(
                                new AgreementVersion()
                                        .withId(UUID.randomUUID().toString())
                                        .withVersionType(AgreementVersionType.MAJOR)
                                        .withVersionNumber("1.0.0")
                                        .withScopes(Lists.newArrayList(
                                                        new ConsentScope()
                                                                .withId("fitness.read")
                                                                .withPurpose(Lists.newArrayList(
                                                                        DataPurpose.AGGREGATION,
                                                                        DataPurpose.PERSONALIZATION
                                                                ))
                                                )
                                        )
                                        .withContent(
                                                Lists.newArrayList(
                                                        new AgreementContent()
                                                                .withDateCreated(DateTime.now())
                                                                .withUrl("https://www.fake.com")
                                                                .withLocale("jp")
                                                                .withDateModified(DateTime.now()),
                                                        new AgreementContent()
                                                                .withDateCreated(DateTime.now())
                                                                .withUrl("https://www.fake.com")
                                                                .withLocale("en")
                                                                .withDateModified(DateTime.now())
                                                )
                                        )
                        )
                );

        App app = new App()
                .withId("my_fitness_app")
                .withBrand(new AppBrand()
                        .withLogo(new AppLogo()
                                .withUrl(URI.create("https://.../logo.png"))
                        )
                        .withName("My Fitness Pal")
                )
                .withConsent(
                        new ConsentGroup()
                                .withScopes(Lists.newArrayList(
                                        new ConsentScope()
                                                .withId("fitness.activity.read")
                                                .withPurpose(
                                                        Lists.newArrayList(
                                                                DataPurpose.PERSONALIZATION,
                                                                DataPurpose.AGGREGATION
                                                        )
                                                ),
                                        new ConsentScope()
                                                .withId("fitness.activity.write")
                                                .withPurpose(
                                                        Lists.newArrayList(
                                                                DataPurpose.COLLECTION,
                                                                DataPurpose.STORAGE
                                                        )
                                                )
                                ))
                );

        System.out.println(U.toJsonPrettyFormat(app));

        System.out.println(U.toJsonPrettyFormat(agreement));
    }
}
