package io.klustr.console;

import io.klustr.compute.MockProjectTemplateRepository;
import io.klustr.compute.dns.impl.PowerDnsApiComputeDnsProvider;
import io.klustr.compute.impl.PrometheusComputeUsageProvider;
import io.klustr.compute.impl.MockComputeInstanceTypeProvider;
import io.klustr.compute.ingress.impl.DefaultLoadbalancerResolver;
import io.klustr.consent.admin.AppConsentService;
import io.klustr.consent.cms.AgreementService;
import io.klustr.consent.cms.AgreementVersionService;
import io.klustr.console.experiments.ExperimentsService;
import io.klustr.console.hosting.*;
import io.klustr.console.hosting.templates.ProjectDependencyDnsHydration;
import io.klustr.console.kafka.*;
import io.klustr.console.org.*;
import io.klustr.console.prometheus.PrometheusProxyService;
import io.klustr.integrations.cdn.MinioCdnProvider;
import io.klustr.integrations.gitlab.*;
import io.klustr.integrations.portainer.PortainerAuthentication;
import io.klustr.integrations.portainer.PortainerComputeResourceProvider;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.setup.SetupHistory;
import io.klustr.setup.SetupRunner;
import io.klustr.console.setup.SetupService;
import io.klustr.documents.CollectionService;
import io.klustr.exceptions.StandardResponseExceptionHandler;
import io.klustr.integrations.kong.KongGatewayConsumerProvider;
import io.klustr.integrations.kong.KongGatewayPluginProvider;
import io.klustr.integrations.kong.KongGatewayServiceProvider;
import io.klustr.integrations.kong.KongServiceRegistry;
import io.klustr.integrations.lago.LagoBillingAdapter;
import io.klustr.integrations.minio.DefaultStorageProviderResolver;
import io.klustr.integrations.prometheus.PrometheusApi;
import io.klustr.integrations.svix.SvixWebhookProvider;
import io.klustr.kafka.KafkaClientOptions;
import io.klustr.permissions.exceptions.MissingPermissionsHandler;
import io.klustr.persons.PersonAdminService;
import io.klustr.persons.PersonStorage;
import io.klustr.persons.UserService;
import io.klustr.persons.admin.AdminIdentityService;
import io.klustr.persons.ekyc.EkycStorage;
import io.klustr.persons.ekyc.veriff.EkycVeriffWebhookService;
import io.klustr.persons.ekyc.veriff.VerificationStorage;
import io.klustr.consent.*;
import io.klustr.consent.admin.UserConsentAdminService;
import io.klustr.consent.agreements.UserAgreementsAdminService;
import io.klustr.console.admin.OAuthTokenService;
import io.klustr.console.storage.Storage;
import io.klustr.setup.SetupStateStorage;
import io.klustr.storage.Pagination;
import io.klustr.storage.docs.rethinkdb.RethinkDbConnectionPool;
import io.klustr.events.EventLocationService;
import io.klustr.events.EventStorage;
import io.klustr.events.EventsService;
import io.klustr.events.kafka.KafkaApplicationEventConfiguration;
import io.klustr.events.kafka.KafkaLocationEventConfiguration;
import io.klustr.integrations.growthbook.GrowthBookApi;
import io.klustr.integrations.mailtrap.EmailService;
import io.klustr.integrations.mailtrap.MailTrapApi;
import io.klustr.integrations.ory.HydraApi;
import io.klustr.kafka.dbadapter.DbAdapterKafkaDbAdapterBroadcasterFactory;
import io.klustr.notifications.NotificationStorage;
import io.klustr.notifications.UserFirebaseChannelsService;
import io.klustr.spring.OpaqueTokenSecurityConfiguration;
import io.klustr.storage.docs.rethinkdb.RethinkDbDocumentDatabaseFactory;
import io.klustr.storage.query.Db;
import io.klustr.utils.Json;
import io.klustr.json.SpringJacksonConfiguration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@OpenAPIDefinition(
        info = @Info(
                title = "Klustr.io API",
                version = "1.0.0",
                description = "Documentation for manipulating and interacting with klustr.io console and related resources such as storage, credentials, teams, organization, and projects. Your metadata and base layer for building a nextgen platform for your developer teams in-house or in-cloud."
        )
)
@SpringBootApplication(exclude = {
        org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration.class,
        org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration.class
})
@ComponentScan(basePackageClasses = {
        OpaqueTokenSecurityConfiguration.class,
        // core
        RethinkDbConnectionPool.class,
        KafkaApplicationEventConfiguration.class,
        KafkaLocationEventConfiguration.class,
        KafkaUserApplicationConsentListener.class,
        EventLocationService.class,
        StandardSecurityConfiguration.class,
        UserFirebaseChannelsService.class,

        UserConsentProvider.class,

        // integrations

        // kong
        KongServiceRegistry.class,
        KongGatewayConsumerProvider.class,
        KongGatewayPluginProvider.class,
        KongGatewayServiceProvider.class,

        // email
        EmailService.class,
        MailTrapApi.class,
        // lago
        LagoBillingAdapter.class,
        // experiments
        GrowthBookApi.class,
        // hydra
        HydraApi.class,
        // veriff ekyc
        EkycVeriffWebhookService.class,
        ConsentAuditManagementService.class,
        OAuthTokenService.class,

        // directory
        OrgService.class,
        OrgUnitManagementService.class,
        UserRoleAssignmentService.class,

        // admin
        PersonAdminService.class,
        AdminIdentityService.class,
        UserConsentAdminService.class,
        UserAgreementsAdminService.class,

        // services
        UserService.class,
        ConsentScopeService.class,
        AccountService.class,
        ApiCatalogService.class,

        ProjectCredentialService.class,
        ProjectClientService.class,
        ProjectService.class,
        ProjectOrgService.class,
        ProjectApiSubscriptionServices.class,
        ProjectStorageBucketService.class,
        EventsService.class,
        CdnService.class,
        ConsentScopeService.class,
        AppConsentService.class,


        DbAdapterKafkaDbAdapterBroadcasterFactory.class,

        Storage.class,
        EventStorage.class,
        ConsentStorage.class,
        EventStorage.class,
        PersonStorage.class,
        NotificationStorage.class,
        EkycStorage.class,
        VerificationStorage.class,

        CollectionService.class,


        SetupService.class,

        DefaultStorageProviderResolver.class,
        MissingPermissionsHandler.class,



        KafkaEventInviteUserToOrganization.class,

        AgreementVersionService.class,
        AgreementService.class,

        StandardResponseExceptionHandler.class,

        PrometheusApi.class,

        OrgSectorsService.class,

        AppWebhooksService.class,
        SvixWebhookProvider.class,
        ExperimentsService.class,

        // container
        Server.class,
        SetupRunner.class,

        MinioCdnProvider.class,

        KafkaConsentAuditListener.class,
        KafkaEkycVerificationListener.class,
        KafkaExperimentConsentListener.class,
        KafkaKratosListener.class,
        KafkaUserApplicationConsentListener.class,

        ProjectGitService.class,
        GitLabUserResourceProvider.class,
        GitLabProjectResourceProvider.class,
        GitLabConfigurationProvider.class,
        GitLabGroupResourceProvider.class,
        GitLabGroupMembershipProvider.class,
        GitLabProjectPipelineResourceProvider.class,
        GitLabProjectDockerImageReferenceProvider.class,

        // compute infra (services)
        ProjectComputeService.class,
        ProjectComputeMachineTypesService.class,
        DefaultLoadbalancerResolver.class,
        PowerDnsApiComputeDnsProvider.class,
        // compute infra dependencies
        PortainerAuthentication.class,
        PortainerComputeResourceProvider.class,
        MockComputeInstanceTypeProvider.class,
        // compute usages
        ProjectComputeUsageService.class,
        PrometheusComputeUsageProvider.class,
        MockProjectTemplateRepository.class,
        DefaultLoadbalancerResolver.class,
        ComputeO11yDiscoveryService.class,
        ProjectDependencyDnsHydration.class,

        // new feature for picking templates to run to create a new project
        // this enables the platform to make tempaltes like ['started-website', 'redis', 'iota'] or
        // other types and deploy them into the environment
        ProjectTemplateService.class,
        // new feature added for 2.0 that enables us to notify people about changes to a project
        // and get status updates (via callback webhook)
        ProjectNotificationService.class,

        // grafana auth/tenent support via proxy
        PrometheusProxyService.class,


        SetupStateStorage.class,
        GrowthBookApi.class,
        KafkaMetrics.class,
        RethinkDbDocumentDatabaseFactory.class,

        SpringJacksonConfiguration.class
    },
    excludeFilters =  @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
            MongoAutoConfiguration.class,
            MongoDataAutoConfiguration.class}
    )
)
@EnableScheduling
@EnableConfigurationProperties({KafkaClientOptions.class})
@ImportResource("classpath:spring.xml")
public class Server {
    private static final Logger log = LoggerFactory.getLogger(Server.class);

    public Server(SetupStateStorage state, SetupRunner runner) {
        if (state.history().listObjectIds(Db.all(), Pagination.all()).docs.isEmpty()) {
            runner.run();
            state.history().insertObject(Json.toJsonNode(Json.toJson(new SetupHistory("init"))));
        }
    }

    public static void main(String[] args) {
        try {
            log.info("Starting application server...");
            SpringApplication.run(Server.class, args);
        } catch (Exception e) {
            log.error("Exception", e);
            throw new RuntimeException(e);
        }
    }
}
