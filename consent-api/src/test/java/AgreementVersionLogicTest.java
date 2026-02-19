

import com.google.common.collect.Lists;
import io.klustr.SemanticVersion;
import io.klustr.console.agreements.AgreementVersionLogic;
import io.klustr.schemas.console.agreements.*;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.consent.ConsentAttribute;
import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;

public class AgreementVersionLogicTest {


    public Agreement mockAgreement() {
        return new Agreement()
                .withNamespace(Agreement.AgreementScope.ORG)
                .withId("b16392282b81") // agreement.id
                .withOrgId("woven")
                .withStatus(Agreement.Status.PUBLISHED)
                .withDescription("A fake agreement to test with")
                .withName("Agreement Title")
                .withOwner(new AgreementOwner().withEmail("terrance.a.snyder@gmail.com"))
                .withVersions(Lists.newArrayList(
                        // version 1.0.0 (original agreement)
                        version1_0_0(),
                        // version 1.0.1 (most recent 1.0.1 version)
                        version1_0_1(),
                        // version 1.0.2 (most recent 1.0.0 version, but is not active yet)
                        version1_0_2(),
                        // version 2.0.0 (most recent major version)
                        version2_0_0(),
                        // version 2.0.1 (not active but published)
                        version2_0_1()
                ));
    }

    private AgreementVersion buildVersion(String version, String ... locales) {
        SemanticVersion v = SemanticVersion.parse(version);
        return  new AgreementVersion()
                .withId(UUID.randomUUID().toString()) // version.id
                .withScopes(Lists.newArrayList(
                        new ConsentScope()
                                .withId("biometric.faceid.read")
                                .withPurpose(Lists.newArrayList(DataPurpose.PROCESSING))
                                .withSensitivity(ConsentAttribute.Sensitivity.HIGH.value())
                ))
                .withVersionType(v.isMajor() ? AgreementVersionType.MAJOR : AgreementVersionType.MINOR)
                .withVersionNumber(v.string()) // key for version number
                .withEffectiveDate(DateTime.now().minusDays(7))
                .withStatus(AgreementVersion.Status.PUBLISHED)
                .withContent(Arrays.stream(locales).map(locale -> {
                    return
                            new AgreementContent()
                                    .withLocale(locale)
                                    .withUrl("https://foo/" + locale + ".md");
                }).collect(Collectors.toList()));
    }

    private AgreementVersion version1_0_0() {
        return buildVersion("1.0.0", "en-us", "ja-jp");
    }

    private AgreementVersion version1_0_1() {
        // Use Case:
        //  * A new version of 1.0.0 document that is active now
        return buildVersion("1.0.1", "en-us")   // just english changed
                .withEffectiveDate(DateTime.now().minusDays(1))
                .withModifiedDate(DateTime.now().minusDays(3));
    }

    private AgreementVersion version1_0_2() {
        // Use Case:
        //  * Agreement was signed by a user at 1.0.0, and it has a minor update that should be visible
        //    to them as it still applies to them.
        return buildVersion("1.0.2", "en-us", "ja-jp")   // english and jp changed
                .withStatus(AgreementVersion.Status.PUBLISHED)
                .withEffectiveDate(DateTime.now().plusDays(2));
    }

    // Use Case:
    //  * A new major version is available of a signed agreement
    private AgreementVersion version2_0_0() {
        // VERSION 2.0.0
        return buildVersion("2.0.0", "en-us", "ja-jp")
                .withVersionNumber("2.0.0")
                .withModifiedDate(DateTime.now().minusDays(1));
    }

    // Use Case:
    //  * A new major version is updated but it will go into effect in the future
    //    so the newest agreement is still 2.0.0 until the future date
    private AgreementVersion version2_0_1() {
        // VERSION 2.0.0
        return buildVersion("2.0.1", "en-us", "ja-jp")
                .withVersionNumber("2.0.1")
                .withEffectiveDate(DateTime.now().plusDays(1));  // future
    }

    @Test
    public void we_can_get_the_latest_version_of_agreement() {
        Optional<AgreementVersion> latestVersion = AgreementVersionLogic.getLatestVersion(mockAgreement());
        assertThat(latestVersion.isPresent());

        // 2.0.0 should be the agreement version becauase its the most recent major version
        // that is published and effective, the 2.0.1 version is not ready as its tomorrow
        assertThat(latestVersion.get().getVersionNumber()).isEqualToIgnoringCase("2.0.0");
    }

    @Test
    public void we_handle_dates_correctly() {
        // This checks if we change the 2.0.1 to start today instead of tomorrow we correctly
        // indentify the minor version as the "latest version".
        Agreement agreement = mockAgreement();

        // find 2.0.1 and change the date to today
        agreement.getVersions()
                .stream()
                .filter(x -> x.getVersionNumber().equalsIgnoreCase("2.0.1")).
                findFirst().get()
                .setEffectiveDate(DateTime.now().withTimeAtStartOfDay());

        Optional<AgreementVersion> latestVersion = AgreementVersionLogic.getLatestVersion(agreement);
        assertThat(latestVersion.isPresent());

        // 2.0.0 should be the agreement version becauase its the most recent major version
        // that is published and effective, the 2.0.1 version is not ready as its tomorrow
        assertThat(latestVersion.get().getVersionNumber()).isEqualToIgnoringCase("2.0.1");
    }

    @Test
    public void we_can_get_the_minimum_version_required() {
        // for this case, we use this to check if a user has signed 2.0.0 but there is a 2.0.1 available
        // its still ok the user has the minimum version, this basically means
        // ... "Find all active versions, that are published and effective"
        // ... "Find the latest MAJOR version"
        //  .... Return as we found the minimum required version a user must sign
        // We use this when a user signs in or we need to check if a user must resign
        Agreement agreement = mockAgreement();
        Optional<AgreementVersion> minVersion = AgreementVersionLogic.getMinimumVersionRequired(agreement);
        assertThat(minVersion.isPresent());
        assertThat(minVersion.get().getVersionNumber()).isEqualToIgnoringCase("2.0.0");
    }

    @Test
    public void json_our_model() {
        System.out.println(U.toJsonPrettyFormat(mockAgreement()));
    }
}
