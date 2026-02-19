import com.google.common.collect.Lists;
import io.klustr.schemas.console.orgs.Org;
import io.klustr.schemas.console.orgs.OrgContact;
import io.klustr.schemas.console.orgs.OrgUnit;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class OrgTest {

    @Test
    public void we_can_model() {
        Org google = new Org()
                .withName("Google")
                .withContact(new OrgContact()
                        .withEmail("super@google.com")
                )
                .withId(UUID.randomUUID().toString())
                .withCreationDate(DateTime.now())
                .withModifiedDate(DateTime.now());

        OrgUnit sales = new OrgUnit()
                .withName("Sales")
                .withId("sales");

        sales.getChildren().add(new OrgUnit()
                .withName("GCS")
                .withId("gcs-sales")
                .withChildren(Lists.newArrayList(
                        new OrgUnit()
                                .withId("terrance.snyder")
                ))
        );

        google.getChildren().add(
                sales
        );

        System.out.println(U.toJsonPrettyFormat(google));
    }
}
