package io.klustr.kafka.events;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public class KafkaRecordErrorEvent implements KafkaErrorEvent {
    private final ConsumerRecord<String, String> record;
    private final Exception ex;

    public KafkaRecordErrorEvent(ConsumerRecord<String, String> record, Exception ex) {
        this.record = record;
        this.ex = ex;
    }
}
