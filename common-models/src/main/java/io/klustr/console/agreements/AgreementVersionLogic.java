package io.klustr.console.agreements;

import io.klustr.SemanticVersion;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.agreements.AgreementVersionType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AgreementVersionLogic {

    private static List<AgreementVersion> getEnabledVersions(Agreement agreement) {
        return agreement.getVersions().stream()
                // is approved
                .filter(x -> {
                    return x.getStatus() != null && x.getStatus() == AgreementVersion.Status.PUBLISHED;
                })
                // is active now
                .filter(x -> {
                    return x.getEffectiveDate() != null && x.getEffectiveDate().isBeforeNow();
                })
                // any version
                .filter(x -> {
                    return x.getVersionType() != null;
                }).toList();
    }

    public static Optional<AgreementVersion> getLatestVersion(Agreement agreement) {
        List<AgreementVersion> v = getEnabledVersions(agreement);
        if (v.isEmpty()) return Optional.empty();

        return v.stream().max((o1, o2) -> {
            SemanticVersion o2v = new SemanticVersion(o2.getVersionNumber());
            SemanticVersion o1v = new SemanticVersion(o1.getVersionNumber());
            return o1v.compareTo(o2v);
        });
    }

    public static Optional<AgreementVersion> getMinimumVersionRequired(Agreement agreement) {
        List<AgreementVersion> majorVersions = getEnabledVersions(agreement).stream()
                // is a major version
                .filter(x -> {
                    return x.getVersionType() != null && x.getVersionType() == AgreementVersionType.MAJOR;
                }).toList();

        return majorVersions.stream().max((o1, o2) -> {
            SemanticVersion o2v = new SemanticVersion(o2.getVersionNumber());
            SemanticVersion o1v = new SemanticVersion(o1.getVersionNumber());
            return o1v.compareTo(o2v);
        });
    }
}
