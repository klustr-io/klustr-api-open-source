package io.klustr.compute;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.projects.*;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MockProjectTemplateRepository implements ProjectTemplateProvider {

    public Optional<ProjectTemplate> getTemplate(String templateId) {
        return this.getTemplates().stream().filter(x -> x.getId().equalsIgnoreCase(templateId)).findFirst();
    }

    public List<ProjectTemplate> getTemplates() {

        ProjectTemplate helloWorld = new ProjectTemplate()
                .withId("hello-world")
                .withName("Example Website")
                .withDescription("A reactjs, nodejs project to bootstrap your development.")
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withRequiresImage(true)
                .withEndpointUrl("https://{projectId}.dev.klustr.io/")
                .withDocumentationUrl("https://docs.klustr.io/template/hello-world")
                .withManifestFilename("simple.docker.compose.yaml")
                .withType(ProjectTemplate.Type.WEBSITE)
                .withDefaultMachineType("m1.small")
                .withPort(3000)
                .withResolver(ProjectTemplate.Resolver.HTTP_HOSTNAME_HEADER)
                .withProtocol(ProjectTemplate.Protocol.HTTP)
                .withAutoscale(true)
                .withRequiredFeatures(new RequiredFeatures()
                        .withGit(new TemplateGitConfiguration().withGitUrl("https://gitlab.dev.klustr.io/rize.fit/lucky-canidae-1d5c99"))
                        .withDns(new Dns().withZone("dev.klustr.io.").withName("{projectId}.dev.klustr.io."))
                );

        // nginxdemos/hello:latest

        ProjectTemplate mongo = new ProjectTemplate()
                .withId("hello-mongo")
                .withName("MongoDB")
                .withDescription("Will provision a mongodb project for use with your projects.")
                .withRequiresImage(false)
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withEndpointUrl("mongodb://root:password@{projectId}.dev.klustr.io:27017/example?authSource=admin&tls=true&tlsAllowInvalidHostnames=false")
                .withDocumentationUrl("https://docs.klustr.io/templates/hello-mongo")
                .withManifestFilename("docker.mongodb.compose.yaml")
                .withType(ProjectTemplate.Type.DATA_STORAGE)
                .withDefaultMachineType("m1.small")
                .withResolver(ProjectTemplate.Resolver.SSL_FC_SNI)
                .withProtocol(ProjectTemplate.Protocol.TCP)
                .withPort(27017)
                .withAutoscale(false)
                .withO11y(
                        new O11y().withEndpoints(
                                Lists.newArrayList(
                                        new O11yEndpoint()
                                                .withUrl("http://{projectId}-mongo-metrics:9216/metrics")
                                                .withLabels(Lists.newArrayList(
                                                        new O11yEndpointLabel().withKey("workload").withValue("mongodb")
                                                )))))
                .withRequiredFeatures(new RequiredFeatures()
                        .withDns(new Dns().withZone("dev.klustr.io.").withName("{projectId}.dev.klustr.io."))
                        .withCredentials(new TemplateCredentialsConfiguration()
                                .withBasicCredentials(new TemplateCredentialsBasicConfiguration()
                                        .withEnvVarUsername("MONGO_USERNAME")
                                        .withEnvVarPassword("MONGO_PASSWORD")
                                        .withPasswordLength(18)
                                        .withLabel("Mongo Admin")
                                        .withId("mongo_admin_credential")
                                )
                        )
                );

        ProjectTemplate postgres = new ProjectTemplate()
                .withId("hello-postgres")
                .withName("PostgreSQL")
                .withDescription("Will provision and activate a postgresSQL instance for you to use.")
                .withRequiresImage(false)
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withEndpointUrl("postgres://<uid>:<pwd>@{projectId}.dev.klustr.io:5432/{projectId}?sslmode=require")
                .withDocumentationUrl("https://docs.klustr.io/templates/hello-postgres")
                .withManifestFilename("postgres.docker.compose.yaml")
                .withType(ProjectTemplate.Type.DATA_STORAGE)
                .withDefaultMachineType("m1.small")
                .withResolver(ProjectTemplate.Resolver.REQ_SSL_SNI)
                .withProtocol(ProjectTemplate.Protocol.TCP)
                .withPort(5432)
                .withAutoscale(false)
                .withO11y(
                        new O11y().withEndpoints(
                                Lists.newArrayList(
                                        new O11yEndpoint()
                                                .withUrl("http://{projectId}-metrics:9187/metrics")
                                                .withLabels(Lists.newArrayList(
                                                        new O11yEndpointLabel().withKey("workload").withValue("postgres")
                                                )))))
                .withRequiredFeatures(new RequiredFeatures()
                        .withDns(new Dns().withZone("dev.klustr.io.").withName("{projectId}.dev.klustr.io."))
                        .withCredentials(new TemplateCredentialsConfiguration()
                                .withBasicCredentials(new TemplateCredentialsBasicConfiguration()
                                        .withEnvVarUsername("POSTGRES_USERNAME")
                                        .withEnvVarPassword("POSTGRES_PASSWORD")
                                        .withPasswordLength(18)
                                        .withLabel("Postgres Admin")
                                        .withId("postgres_admin_credential")
                                )
                        )
                );

        ProjectTemplate postgresShared = new ProjectTemplate()
                .withId("postgres-database")
                .withName("Postgres Database")
                .withDescription("Will provision a database and use existing server compute fabric. This will not be an isolated instance and does not offer a full pql version, but is good enough for many scenarios.")
                .withRequiresImage(false)
                .withCreationDate(DateTime.now())
                .withProvisioner(ProjectTemplate.Provisioner.POSTGRES)
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withEndpointUrl("postgres://<uid>:<pwd>@{projectId}.dev.klustr.io:5432/{projectId}?sslmode=require")
                .withDocumentationUrl("https://docs.klustr.io/templates/postgres-database")
                .withType(ProjectTemplate.Type.DATA_STORAGE)
                .withRequiredFeatures(new RequiredFeatures()
                        .withCredentials(new TemplateCredentialsConfiguration()
                                .withBasicCredentials(new TemplateCredentialsBasicConfiguration()
                                        .withEnvVarUsername("USERNAME")
                                        .withEnvVarPassword("PASSWORD")
                                        .withPasswordLength(18)
                                        .withLabel("DB")
                                        .withId("db")
                                )
                        )
                );

        ProjectTemplate helloAuth = new ProjectTemplate()
                .withId("hello-auth")
                .withName("Website w/ Auth")
                .withDescription("A website that enables you to sign in and authenticate a user.")
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withRequiresImage(true)
                .withEndpointUrl("https://{projectId}.dev.klustr.io/")
                .withDocumentationUrl("https://docs.klustr.io/template/hello-auth")
                .withManifestFilename("simple.docker.compose.yaml")
                .withType(ProjectTemplate.Type.WEBSITE)
                .withDefaultMachineType("m1.small")
                .withPort(5000)
                .withResolver(ProjectTemplate.Resolver.HTTP_HOSTNAME_HEADER)
                .withProtocol(ProjectTemplate.Protocol.HTTP)
                .withAutoscale(true)
                .withO11y(
                        new O11y().withEndpoints(
                                Lists.newArrayList(
                                        new O11yEndpoint()
                                                .withUrl("http://{projectId}:5000/metrics")
                                                .withLabels(Lists.newArrayList(
                                                        new O11yEndpointLabel().withKey("workload").withValue("nodejs")
                                                )))))
                .withRequiredFeatures(new RequiredFeatures()
                        .withGit(new TemplateGitConfiguration().withGitUrl("https://gitlab.dev.klustr.io/klustr.io/template-hello-auth"))
                        .withDns(new Dns().withZone("dev.klustr.io.").withName("{projectId}.dev.klustr.io."))
                        .withApp(new TemplateAppConfiguration()
                                .withName("Hello Auth!")
                                .withDomains(Lists.newArrayList(
                                        "http://localhots:5000",
                                        "https://{projectId}.dev.klustr.io/"
                                ))
                                .withLogoUrl("https://cdn.dev.klustr.io/klustr/demo.png")
                                .withScopes(Lists.newArrayList("profile offline email openid".split(" ")))
                        )
                        .withOauthClient(new TemplateOauthConfiguration()
                                .withName("hello-auth-app")
                                .withAuthorizedDomains(Lists.newArrayList("https://{projectId}.dev.klustr.io/",
                                        "http://localhots:5000"))
                                .withAuthorizedRedirectUris(Lists.newArrayList("https://{projectId}.dev.klustr.io/auth/oidc/redirect",
                                        "http://localhots:5000/auth/oidc/redirect"))
                                .withTokenAuthMethod("client_secret_basic")
                        )
                );

        ProjectTemplate o11ypush = new ProjectTemplate()
                .withId("o11y-o11ypush")
                .withImage("registry.dev.klustr.io/klustr.io/klustr-o11ypush/main")
                .withName("O11Y Gateway")
                .withScope(ProjectTemplate.Scope.ORG)
                .withDescription("Enables your organization to push metrics adding this will automatically enabling scraping of metrics and activate the o11y settings for projects.")
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withRequiresImage(false)
                .withEndpointUrl("https://{orgId}-o11y.dev.klustr.io/")
                .withDocumentationUrl("https://docs.klustr.io/templates/pushgateway")
                .withManifestFilename("o11y-gateway.docker.compose.yaml")
                .withType(ProjectTemplate.Type.WEBSITE)
                .withDefaultMachineType("m1.small")
                .withPort(9091)
                .withResolver(ProjectTemplate.Resolver.HTTP_HOSTNAME_HEADER)
                .withProtocol(ProjectTemplate.Protocol.HTTP)
                .withAutoscale(false)
                .withRestrictions(new ProjectTemplateRestrictions()
                        .withLimitOnePerOrg(true)
                );

        ProjectTemplate devmachine = new ProjectTemplate()
                .withId("dev-machine")
                .withName("Developer Machine")
                .withDescription("A machine used to connect with visual studio and do remote development.")
                .withRequiresImage(false)
                .withCreationDate(DateTime.now())
                .withStatus(ProjectTemplate.Status.ACTIVE)
                .withEndpointUrl("ssh {projectId}@{projectId}.dev.klustr.io -p {port}")
                .withDocumentationUrl("https://docs.klustr.io/templates/dev-machine")
                .withManifestFilename("devmachine.docker.compose.yaml")
                .withType(ProjectTemplate.Type.TOOL)
                .withDefaultMachineType("m1.small")
                .withResolver(ProjectTemplate.Resolver.REQ_SSL_SNI)
                .withProtocol(ProjectTemplate.Protocol.TCP)
                .withExposePort(true)
                .withAutoscale(false)
                .withRequiredFeatures(new RequiredFeatures()
                        .withDns(new Dns().withZone("dev.klustr.io.").withName("{projectId}.dev.klustr.io."))
                );

        return Lists.newArrayList(helloWorld, helloAuth, postgres, postgresShared, mongo, o11ypush, devmachine);
    }
}
