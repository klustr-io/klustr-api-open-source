package io.klustr.features;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.features.*;
import io.klustr.schemas.console.features.core.*;
import io.klustr.schemas.console.features.credentials.ProjectFeatureBasicCredentialsConfiguration;
import io.klustr.schemas.console.features.credentials.ProjectFeatureCredentialsConfiguration;
import io.klustr.schemas.console.features.database.mongo.ProjectMongoDbFeature;
import io.klustr.schemas.console.features.database.postgres.ProjectPostgresFeature;
import io.klustr.schemas.console.features.dns.ProjectFeatureDnsConfiguration;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;
import io.klustr.schemas.console.features.docker.ProjectDockerFeature;
import io.klustr.schemas.console.features.minio.ProjectMinioFeature;
import org.springframework.stereotype.Component;


import java.util.HashMap;
import java.util.List;

@Component
public class MockProjectFeatureRespository implements ProjectFeatureProvider {

    public MockProjectFeatureRespository() {

    }

    public List<AbstractProjectFeature> getAvailableFeatures(String org_id) {
        return Lists.newArrayList(postgres(), mongo(), docker(), minio());
    }

    private AbstractProjectFeature iota() {
        return new ProjectPostgresFeature();
    }

    private AbstractProjectFeature burr() {
        // to add burr, needs multi-tenent?
        // how to onboard into burr
        // "Tell us your resident information?"
        // "Do you want to turn on sync?"
        // "Provision a tenent identifier?"
        // "ACLS?"
        return new ProjectPostgresFeature();
    }

    private AbstractProjectFeature minio() {
        return new ProjectMinioFeature()
                .withId("minio")
                .withConfiguration(new ProjectFeatureRequiredConfiguration()
                        .withDns(new ProjectFeatureDnsConfiguration()
                                .withTemplateValue("{projectid}.dev.klustr.io")
                        )
                )
                .withDescription("Minio Stoage")
                .withConstraints(new ProjectFeatureConstraints()
                        .withLimitOnePerProject(true)
                )
                .withStatus(FeatureStatus.ACTIVE)
                .withCategory(Category.STORAGE);
    }

    private AbstractProjectFeature docker() {
        return new ProjectDockerFeature()
                .withId("docker")
                .withName("Docker")
                .withDescription("Deployment for docker")
                .withCategory(Category.COMPUTE)
                .withStatus(FeatureStatus.ACTIVE)
                .withUi(new ProjectFeatureUISpec()
                        .withUiIcon("docker.png")
                        .withUiModule("docker")
                        .withUiLabel("Docker")
                )
                .withNetworking(new NetworkingSpec()
                        .withEndpointTemplate("x")
                        .withProtocol(NetworkingSpec.Protocol.HTTP)
                        .withInternalPort(3000)
                )
                .withProvisioning(new ProvisioningSpec()
                        .withImage("image")
                        .withDefaultMachineType("m1.small")
                        .withVolumes(Lists.newArrayList(
                                new ProvisioningVolumeSpec().withReadOnly(false).withSource("foo").withTarget("bar`")
                        ))
                        .withManifestFilename("somemanigest.yaml")
                )
                .withObservability(new ObservabilitySpec()
                        .withEndpoints(Lists.newArrayList(new Endpoint()
                                .withUrl("https://foo.dev.klustr.io/health")
                        ))
                );
    }

    private AbstractProjectFeature mongo() {
        return new ProjectMongoDbFeature()
                .withId("mongo")
                .withName("MongoDB")
                .withDescription("A mongodb instance for your project")
                .withCategory(Category.DATABASE)
                .withStatus(FeatureStatus.ACTIVE)
                .withUi(
                        new ProjectFeatureUISpec()
                                .withUiIcon("mongo.png")
                                .withUiModule("mongo")
                                .withUiLabel("mongodb")
                )
                .withConstraints(new ProjectFeatureConstraints()
                        .withLimitOnePerProject(false)
                )
                .withConfiguration(new ProjectFeatureRequiredConfiguration()
                        .withCredentials(new ProjectFeatureCredentialsConfiguration()
                                .withBasicCredentials(new ProjectFeatureBasicCredentialsConfiguration()
                                        .withId("mongo_credential")
                                        .withEnvVarNameUsername("MONGODB_USERNAME")
                                        .withEnvVarNamePassword("MONGODB_PASSWORD")
                                        .withRequiredPasswordLength(12)
                                        .withLabel("MongoDB UID/Password")
                                )
                        )
                );
    }

    private AbstractProjectFeature postgres() {
        return new ProjectPostgresFeature()
                .withId("postgres")
                .withName("PostgreSQL Feature")
                .withDocumentationUrl("https://docs.klustr.io/feature/postgresql")
                .withDescription("An amazing postgreSQL database added to your project")
                .withCategory(Category.DATABASE)
                .withStatus(FeatureStatus.ACTIVE)
                .withUi(new ProjectFeatureUISpec()
                        .withUiIcon("postgresql.png")
                        .withUiModule("postgres")
                        .withUiLabel("Postgres")
                )
                .withConstraints(new ProjectFeatureConstraints()
                        .withLimitOnePerProject(true)
                )
                .withConfiguration(new ProjectFeatureRequiredConfiguration()
                        .withCredentials(new ProjectFeatureCredentialsConfiguration()
                                .withBasicCredentials(new ProjectFeatureBasicCredentialsConfiguration()
                                        .withId("postgre_credential")
                                        .withEnvVarNamePassword("POSTGRESQL_USERID")
                                        .withEnvVarNameUsername("POSTGRESQL_PASSWORD")
                                        .withLabel("Credential")
                                        .withRequiredPasswordLength(18)
                                )
                        )
                        .withDns(new ProjectFeatureDnsConfiguration()
                                .withTemplateValue("{projectid}.klustr.io")
                        )
                )
                .withMetadata(new HashMap<>());
    }
}
