package io.klustr.consent;

import com.google.common.collect.Lists;
import io.klustr.schemas.console.consent.UserConsentScope;
import org.joda.time.DateTime;

import java.util.List;
import java.util.Optional;

public class ConsentScopeMergeStrategy {
    public static List<UserConsentScope> merge(List<UserConsentScope> _new, List<UserConsentScope> _old) {
        List<UserConsentScope> result = Lists.newArrayList();

        // in new but not in old?
        _new.forEach(n -> {
            Optional<UserConsentScope> oldVersion = _old.stream().filter(o -> o.getId().equalsIgnoreCase(n.getId())).findFirst();
            if (oldVersion.isEmpty()) {
                // brand new
                result.add(n);
            } else {
                // not brand new, was it expired, if so we switch to active
                // but keep orginal date created
                if (oldVersion.get().getStatus() != n.getStatus()) {
                    n.withDateCreated(oldVersion.get().getDateCreated())
                            .withDateModified(DateTime.now());
                }
                result.add(n);
            }
        });

        // not in new
        _old.forEach(o -> {
            Optional<UserConsentScope> isInNew = _new.stream().filter(n -> n.getId().equalsIgnoreCase(o.getId())).findFirst();
            if (isInNew.isEmpty()) {
                // not in new so was removed, revoked
                o.withDateModified(DateTime.now())
                        .withStatus(UserConsentScope.Status.REVOKED)
                        .withDateRevoked(DateTime.now());
                result.add(o);
            }
        });

        return result;
    }
}
