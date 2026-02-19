package io.klustr.console.kafka;

import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTopic;
import io.klustr.schemas.persons.PersonVerification;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.notifications.firebase.ContextAwareFirebaseNotificationProvider;
import io.klustr.notifications.firebase.Notification;
import io.klustr.utils.U;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KafkaEkycVerificationListener {

    private final ContextAwareFirebaseNotificationProvider firebase;
    private static final Logger log = LoggerFactory.getLogger(KafkaEkycVerificationListener.class);

    public KafkaEkycVerificationListener(ContextAwareFirebaseNotificationProvider firebase,
                                         @Value(value = "${ekyc.verification.listener.kafka.brokers}") String bootstrapAddress,
                                         @Value(value = "${ekyc.verification.listener.kafka.consumer}") String groupId,
                                         KafkaMetrics metrics) {
        log.info("Starting kafka ekyc listener");
        this.firebase = firebase;

        KafkaClientOptions opts = new KafkaClientOptions()
                .withBootstrapServers(bootstrapAddress);
        KafkaTemplate<PersonVerification> k = new KafkaTemplate<PersonVerification>(new KafkaTopic("klustr", "ekyc.verifications"), opts, metrics) {
            @Override
            protected String serialize(PersonVerification value) {
                return U.toJson(value);
            }

            @Override
            protected PersonVerification deserialize(String value) {
                return U.fromJson(value, PersonVerification.class);
            }
        };
        k.listen(groupId).progress(this::handle);
    }

    public void handle(ConsumerRecord<String, PersonVerification> record) {

        Notification notification = new Notification();
        notification.body = "We've updated your verification status to " + record.value().getStatus();
        notification.channelId = "default";
        notification.clickAction = "Go";
        notification.send = DateTime.now();
        notification.projectId = "?";
        notification.clickAction = "verification";
        notification.title = "Verification Update: " + record.value().getStatus();
        notification.tag = "verification";
        notification.userId = record.key();
        notification.type = "awesome";
        notification.defaultButtonText = "Details";
        notification.dismissButtonText = "Skip";
        notification.route = "/verification";

        firebase.send(notification);
    }
}
