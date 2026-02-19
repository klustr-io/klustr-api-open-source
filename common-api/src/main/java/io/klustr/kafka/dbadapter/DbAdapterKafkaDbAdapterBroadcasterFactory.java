package io.klustr.kafka.dbadapter;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.storage.DbAdapterBroadcasterFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * An implementation of the {@link DbAdapterBroadcasterFactory} which
 * will take mutations and forward them to Kafka.
 */
@Component
@ConditionalOnProperty(name = "db-adapter.kafka.brokers")
public class DbAdapterKafkaDbAdapterBroadcasterFactory implements DbAdapterBroadcasterFactory {

    private final KafkaClientOptions opts;
    private final KafkaMetrics metrics;

    public DbAdapterKafkaDbAdapterBroadcasterFactory(DbAdapterKafkaConfiguration configuration, KafkaMetrics metrics) {
        this.opts = configuration.getConfiguration();
        this.metrics = metrics;
    }

    public <T> DbAdapterKafkaBroadcaster<T> create(Class<T> clazz, String database, String table) {
        table = table.toLowerCase().trim();
        if (table.length() > 64) {
            throw new RuntimeException("Table name length invalid.");
        }
        return new DbAdapterKafkaBroadcaster<T>(clazz, database, table, opts, metrics);
    }
}
