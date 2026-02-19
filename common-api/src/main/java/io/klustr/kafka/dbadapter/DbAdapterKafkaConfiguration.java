package io.klustr.kafka.dbadapter;

import io.klustr.kafka.KafkaClientOptions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Configuration of the DB adapter kafka adapter that will
 * enable any mutation of a record to be broadcast to kafka.
 */
@Component
public class DbAdapterKafkaConfiguration {
    private final KafkaClientOptions configuration;

    /**
     * Initialize the new kafka configuration.
     *
     * @param brokers The kafka brokers to initialize this with.
     */
    public DbAdapterKafkaConfiguration(@Value("${db-adapter.kafka.brokers}") String brokers) {
        if (StringUtils.isBlank(brokers)) {
            throw new RuntimeException("Could not setup " + this.getClass().getName() + " due to missing broker configuration.");
        }
        this.configuration = new KafkaClientOptions()
                .withBootstrapServers(brokers);
    }

    /**
     * The configuration to use and leverage with communicating
     * with kafka.
     *
     * @return The {@link KafkaClientOptions}
     */
    public KafkaClientOptions getConfiguration() {
        return this.configuration;
    }
}
