package integrations;

import io.klustr.integrations.kong.*;
import io.klustr.integrations.kong.models.KongConsumer;
import io.klustr.integrations.kong.models.KongConsumerRequest;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.*;

public class KongGatewayTest {

    private final KongGatewayConsumerProvider kong = new KongGatewayConsumerProvider("https://kong-admin.dev.klustr.io");
    // http://localhost:8444/consumers/

    @Test
    public void we_can_register_a_client() {
        KongConsumerRequest req = new KongConsumerRequest();
        req.username = "tsnyder";
        req.custom_id = "foo";
        req.tags.add("mytag_1234");
        req.tags.add("mytag_abcd");
        KongConsumer result = kong.createConsumer(req);
        assertThat(result.id).isNotEmpty();
        assertThat(result.username).isEqualTo("tsnyder");
        assertThat(result.created_at).isNotNull();
    }

    @Test
    public void we_can_fetch_a_client() {
        KongConsumer consumer = kong.getConsumer("tsnyder");
        assertThat(consumer).isNotNull();
        assertThat(consumer.id).isNotEmpty();
        assertThat(consumer.username).isEqualTo("tsnyder");
        assertThat(consumer.tags).isNotEmpty();
        assertThat(consumer.tags).contains("mytag_abcd");
        assertThat(consumer.tags).contains("mytag_1234");
    }


    @Test
    public void we_can_delete_a_client() {
        kong.deleteConsumer("tsnyder");

        AtomicBoolean exception_happened = new AtomicBoolean();
        try {
            KongConsumer consumer = kong.getConsumer("tsnyder");
            exception_happened.set(false);
        } catch (Exception ex) {
            exception_happened.set(true);
        }
        assertThat(exception_happened.get()).isTrue();
    }
}
