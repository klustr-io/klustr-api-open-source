package integrations;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.klustr.integrations.mailtrap.MailTrapApi;
import io.klustr.schemas.integrations.mailtrap.EmailPayload;
import io.klustr.schemas.integrations.mailtrap.EmailReference;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class EmailApiTest {

    private final MailTrapApi emails = new MailTrapApi("e71d5b59a84b45102a9b926be647e893");

    @Test
    public void sendTextEmail() {
        Map<String, String> props = Maps.newConcurrentMap();
        props.put("org_name", "RIZE");
        props.put("cta_link", "https://console.dev.klustr.io?id=1234");

        EmailPayload payload = new EmailPayload()
                .withFrom(new EmailReference()
                        .withEmail("mailtrap@demomailtrap.com")
                        .withName("Mailtrap Test")
                )
                .withTo(
                        Lists.newArrayList(
                                new EmailReference()
                                        .withEmail("terrance.snyder@gymotion.io")
                                        .withName("Terrance Snyder")
                        )
                )
                .withTemplateUuid("0f58ecf1-874f-4001-af23-dbd540024aef")
                .withTemplateVariables(props);

    }
}
