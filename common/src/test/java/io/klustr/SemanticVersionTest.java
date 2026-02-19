package io.klustr;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class SemanticVersionTest {

    @Test
    public void we_can_detect_major_versions() {
        SemanticVersion version = SemanticVersion.parse("1.0.1");
        assertThat(version.isMajor()).isFalse();

        version.increment(SemanticVersion.Version.Minor);
        assertThat(version.string()).isEqualToIgnoringCase("1.0.2");

        version.increment(SemanticVersion.Version.Minor);
        assertThat(version.string()).isEqualToIgnoringCase("1.0.3");

        version.increment(SemanticVersion.Version.Major);
        assertThat(version.string()).isEqualToIgnoringCase("2.0.0");
    }
}
