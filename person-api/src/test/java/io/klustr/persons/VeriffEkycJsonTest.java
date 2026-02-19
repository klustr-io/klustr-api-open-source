package io.klustr.persons;

import io.klustr.schemas.persons.veriff.VeriffNotificationPayload;
import io.klustr.utils.U;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import static org.assertj.core.api.Assertions.*;

public class VeriffEkycJsonTest {

    @Test
    public void verify_we_can_deserialize() {
        String json = U.getResourceAsString("veriff.json", this);
        VeriffNotificationPayload notification = U.fromJson(json, VeriffNotificationPayload.class);
        assertThat(notification).isNotNull();
        assertThat(notification.getStatus()).isNotEmpty();
        assertThat(notification.getData().getVerification()).isNotNull();
        assertThat(notification.getData().getVerification().getDocument()).isNotNull();
        assertThat(notification.getData().getVerification().getDocument().getType()).isNotNull();

        System.out.println(U.toJsonPrettyFormat(notification));
    }
}
