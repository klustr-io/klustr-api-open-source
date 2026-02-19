package io.klustr.consent.prompts.impl.consent.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import io.klustr.SemanticVersion;
import io.klustr.consent.prompts.impl.AbstractPrompt;
import io.klustr.consent.prompts.impl.consent.AgreementConsentPrompt;
import io.klustr.console.agreements.AgreementVersionLogic;
import io.klustr.schemas.console.agreements.Agreement;
import io.klustr.schemas.console.agreements.AgreementReference;
import io.klustr.schemas.console.agreements.AgreementVersion;
import io.klustr.schemas.console.apps.ConsentScope;
import io.klustr.schemas.console.consent.types.UserAgreementConsent;
import io.klustr.storage.DocumentResult;
import io.klustr.utils.U;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class AgreementLogic {
    public static List<AbstractPrompt> populateAgreementsToSign(List<Agreement> latestAgreements,
                                                                DocumentResult<UserAgreementConsent> existingSignedAgreements) {
        List<AbstractPrompt> result = Lists.newArrayList();
        // for all published agreements check if the user has signed
        latestAgreements.forEach(agreement -> {

            // for this agreement what is the minimum version required to be signed, if any?
            Optional<AgreementVersion> min = AgreementVersionLogic.getMinimumVersionRequired(agreement);
            // no minimum version exists, odd edge case
            if (min.isEmpty()) {
                return;
            }

            SemanticVersion minVersionRequired = new SemanticVersion(min.get().getVersionNumber());

            Set<UserAgreementConsent> allSignedVersions = existingSignedAgreements.docs.stream()
                    .filter(record -> {
                        return record.getAgreementId().equalsIgnoreCase(agreement.getId());
                    })
                    .collect(Collectors.toSet());

            Optional<UserAgreementConsent> hasRequiredAgreementVersion =
                    allSignedVersions.stream().filter(record -> {
                        SemanticVersion signedVersion = new SemanticVersion(record.getVersionNumber());
                        return record.getVersionNumber() != null && signedVersion.isGreaterThanOrEqualTo(minVersionRequired);
                    }).findFirst();

            if (hasRequiredAgreementVersion.isPresent()) {
                return;
            }

            AgreementConsentPrompt promo = new AgreementConsentPrompt();
            promo.agreement = U.fromJson(U.toJson(agreement), AgreementReference.class);

            Optional<AgreementVersion> version = AgreementVersionLogic.getLatestVersion(agreement);
            promo.agreement.withVersion(version.get());
            promo.resign = !allSignedVersions.isEmpty();

            // ensure the version
            if (version.get().getScopes() != null) {
                promo.scopes = promo.scopes != null ? promo.scopes : Sets.newHashSet();
                promo.scopes.addAll(version.get().getScopes().stream().map(ConsentScope::getId).toList());
            }
            promo.opted_in = false;
            promo.skip = false;
            result.add(promo);
        });
        return result;
    }
}
