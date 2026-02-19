import io.klustr.schemas.console.features.*;
import io.klustr.schemas.console.features.core.ProjectFeatureConstraints;
import io.klustr.schemas.console.features.core.ProjectFeatureUISpec;
import io.klustr.schemas.console.features.credentials.ProjectFeatureBasicCredentialsConfiguration;
import io.klustr.schemas.console.features.credentials.ProjectFeatureCredentialsConfiguration;
import io.klustr.schemas.console.features.database.postgres.ProjectPostgresFeature;
import io.klustr.schemas.console.features.dns.ProjectFeatureDnsConfiguration;
import io.klustr.schemas.console.features.dns.ProjectFeatureRequiredConfiguration;
import io.klustr.utils.Json;
import org.assertj.core.util.Lists;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

public class ProjectFeatureTest {

    @Test
    public void data_model_makes_sense() {
        ProjectPostgresFeature projectPostgresFeature = new ProjectPostgresFeature()
                .withId("postgres")
                .withName("PostgreSQL Feature")
                .withDocumentationUrl("https://docs.klustr.io/feature/postgresql")
                .withDescription("An amazing postgreSQL database added to your project")
                .withCategory(Category.DATABASE)
                .withStatus(FeatureStatus.ACTIVE)
                .withUi(new ProjectFeatureUISpec()
                        .withUiIcon("postgresql.png")
                        .withUiModule("postgre")
                        .withUiLabel("Postgres")
                )
                .withCompany(new CompanyReference()
                        .withCompanyId("1")
                        .withCompanyName("Google")
                        .withCompanyType(CompanyType.FIRST_PARTY)
                )
                // how to link to lago billing and invoicing.
                // plans (basic, free, bronze, silver, gold)
                .withPricing(new FeaturePricing()
                        .withPlans(Lists.newArrayList(
                                new FeaturePlan()
                                        .withName("Bronze")
                                        .withTier(PlanTier.FREE)
                                        .withSubscriptionPrices(Lists.newArrayList(
                                                new SubscriptionPrice()
                                                        .withDescription("Something Monthly")
                                                        .withPriceUsd(10D)
                                                        .withLabel("Monthly Charge")
                                        ))
                                        .withVolumePricing(Lists.newArrayList(
                                                new VolumePrice()
                                                        .withRage(Lists.newArrayList(
                                                                new VolumePriceRange()
                                                                        .withStart(0d)
                                                                        .withStop(10d)
                                                                        .withPerUnitPriceUsd(0d),
                                                                new VolumePriceRange()
                                                                        .withStart(11d)
                                                                        .withStop(100d)
                                                                        .withPerUnitPriceUsd(0.01d)
                                                        ))
                                        ))
                        ))
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

        String json = Json.toJsonPrettyFormat(projectPostgresFeature);
        System.out.println(json);

        AbstractProjectFeature result = Json.parse(json, AbstractProjectFeature.class);
        assertThat(result).isInstanceOf(ProjectPostgresFeature.class);
    }
}
