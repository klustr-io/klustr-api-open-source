package io.klustr.console.jobs;

import io.klustr.consent.ConsentStorage;
import io.klustr.consent.UserConsentProvider;
import io.klustr.console.jobs.billing.BillingScheduledTask;
import io.klustr.console.jobs.consent.SetupConsentScopesFromCsvTask;
import io.klustr.console.jobs.hydra.SetupImportHydraClientsIntoProjectsTask;
import io.klustr.console.jobs.kong.SetupDefaultServicesInKongFromServicesYamlTask;
import io.klustr.console.jobs.kong.SyncProjectsToKongScheduledTask;
import io.klustr.console.jobs.lago.SyncOrgsAndProjectsToLagoScheduledTask;
import io.klustr.console.org.OrganizationDefaultRoles;
import io.klustr.console.security.OrganizationSecurityPolicy;
import io.klustr.console.storage.Storage;
import io.klustr.events.EventStorage;
import io.klustr.exceptions.StandardResponseExceptionHandler;
import io.klustr.integrations.cdn.MinioCdnProvider;
import io.klustr.integrations.gitlab.*;
import io.klustr.integrations.growthbook.GrowthBookApi;
import io.klustr.integrations.kong.KongGatewayConsumerProvider;
import io.klustr.integrations.kong.KongGatewayPluginProvider;
import io.klustr.integrations.kong.KongGatewayServiceProvider;
import io.klustr.integrations.kong.KongServiceRegistry;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.integrations.mailtrap.EmailService;
import io.klustr.integrations.mailtrap.MailTrapApi;
import io.klustr.integrations.minio.DefaultStorageProviderResolver;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.integrations.svix.SvixWebhookProvider;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.dbadapter.DbAdapterKafkaDbAdapterBroadcasterFactory;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.notifications.NotificationStorage;
import io.klustr.permissions.exceptions.MissingPermissionsHandler;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.ekyc.EkycStorage;
import io.klustr.setup.SetupRunner;
import io.klustr.spring.OpaqueTokenSecurityConfiguration;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.json.SpringJacksonConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
})
@ComponentScan(basePackageClasses = {

        // db
        RethinkDbConnectionPool.class,
        DbAdapterKafkaDbAdapterBroadcasterFactory.class,
        DefaultStorageProviderResolver.class,

        // security
        StandardSecurityConfiguration.class,
        OpaqueTokenSecurityConfiguration.class,
        MissingPermissionsHandler.class,
        StandardResponseExceptionHandler.class,

        // defaults
        OrganizationDefaultRoles.class,
        OrganizationSecurityPolicy.class,

        // jobs
        BillingScheduledTask.class,

        // kong
        KongServiceRegistry.class,
        KongGatewayConsumerProvider.class,
        KongGatewayPluginProvider.class,
        KongGatewayServiceProvider.class,

        // Prometheus
        PrometheusApi.class,

        // email
        EmailService.class,
        MailTrapApi.class,

        // lago
        LagoBillingAdapter.class,

        // experiments
        GrowthBookApi.class,
        // hydra
        HydraApi.class,
        // webhooks
        SvixWebhookProvider.class,

        // storage
        UserConsentProvider.class,
        Storage.class,
        EventStorage.class,
        ConsentStorage.class,
        EventStorage.class,
        PersonStorage.class,
        NotificationStorage.class,
        EkycStorage.class,

        KongServiceRegistry.class,

        // setups
        SetupRunner.class,
        SetupDefaultServicesInKongFromServicesYamlTask.class,
        SetupConsentScopesFromCsvTask.class,
        SetupImportHydraClientsIntoProjectsTask.class,

        // scheduled tasks
        BillingScheduledTask.class,
        SyncOrgsAndProjectsToLagoScheduledTask.class,
        SyncProjectsToKongScheduledTask.class,

        KafkaMetrics.class,
        RethinkDbDocumentDatabaseFactory.class,
        MinioCdnProvider.class,

        GitLabProjectResourceProvider.class,
        GitLabGroupResourceProvider.class,
        GitLabGroupMembershipProvider.class,
        GitLabUserResourceProvider.class,
        GitLabStaticConfigurationProvider.class,
        GitLabProjectPipelineResourceProvider.class,

        SpringJacksonConfiguration.class,

        // container
        SyncServer.class},
        excludeFilters =  @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                MongoAutoConfiguration.class,
                MongoDataAutoConfiguration.class}
        )
)
@EnableConfigurationProperties({KafkaClientOptions.class})
@EnableScheduling
@ImportResource("classpath:spring.xml")
public class SyncServer {
    private static final Logger log = LoggerFactory.getLogger(SyncServer.class);

    @Autowired
    public SyncServer(SetupRunner runner) {
        runner.run();
    }

    public static void main(String[] args) {
        try {
            log.info("Starting sync server...");
            SpringApplication.run(SyncServer.class, args);
        } catch (Exception e) {
            log.error("Exception", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
