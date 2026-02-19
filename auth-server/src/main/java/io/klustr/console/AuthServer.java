package io.klustr.console;

import io.klustr.consent.ConsentStorage;
import io.klustr.consent.UserConsentProvider;
import io.klustr.consent.agreements.UserAgreementProvider;
import io.klustr.consent.repository.UserConsentRepository;
import io.klustr.console.internal.HydraTokenEnrichmentService;
import io.klustr.console.internal.KratosIntegrationService;
import io.klustr.console.storage.Storage;
import io.klustr.exceptions.StandardResponseExceptionHandler;
import io.klustr.integrations.lago.LagoProperties;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.integrations.ory.KetoAdminApi;
import io.klustr.integrations.ory.KetoReadApi;
import io.klustr.integrations.ory.KratosApi;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.dbadapter.DbAdapterKafkaConfiguration;
import io.klustr.kafka.dbadapter.DbAdapterKafkaDbAdapterBroadcasterFactory;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.permissions.exceptions.MissingPermissionsHandler;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.repositories.RethinkDbPersonRepository;
import io.klustr.spring.StandardizedApiConfigurer;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionProperties;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.json.SpringJacksonConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
})
@Import({

        // configure APIs
        StandardizedApiConfigurer.class,
        MissingPermissionsHandler.class,

        // required
        RethinkDbPersonRepository.class,
        PersonStorage.class,
        Storage.class,
        ConsentStorage.class,

        // core
        RethinkDbConnectionPool.class,

        // dependencies
        HydraApi.class,
        KratosApi.class,
        KetoReadApi.class,
        KetoAdminApi.class,
        UserConsentProvider.class,
        UserConsentRepository.class,
        UserAgreementProvider.class,

        // services
        HydraTokenEnrichmentService.class,
        KratosIntegrationService.class,
        DbAdapterKafkaConfiguration.class,

        StandardResponseExceptionHandler.class,


        // maintenance and cleaners
        DbAdapterKafkaDbAdapterBroadcasterFactory.class,
        RethinkDbDocumentDatabaseFactory.class,
        KafkaMetrics.class,

        SpringJacksonConfiguration.class
})
@EnableConfigurationProperties({RethinkDbConnectionProperties.class, LagoProperties.class, KafkaClientOptions.class})
@EnableScheduling
@ImportResource("classpath:auth-server-spring.xml")
public class AuthServer {
    private static final Logger log = LoggerFactory.getLogger(AuthServer.class);

    public static void main(String[] args) {
        try {
            log.info("Starting oauth integration server...");
            SpringApplication.run(AuthServer.class, args);
        } catch (Exception e) {
            log.error("Exception", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
