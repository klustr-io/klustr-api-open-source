package io.klustr.consent.responses;

import io.klustr.schemas.console.AppBrand;
import io.klustr.schemas.console.AppLinks;
import io.klustr.schemas.console.apps.AppContactInformation;
import io.klustr.schemas.console.consent.types.UserApplicationConsent;
import org.joda.time.DateTime;

import java.util.List;

public class UserConsent360ResponseBody {
    public UserApplicationConsent consent;
    public List<ExperimentDetails> experiments;
    public List<ConsentScopeDetails> scopes;
    public AppMetadata metadata;
    public DateTime first_seen_date;
    public DateTime last_seen_date;

    public static class AppMetadata {
        public AppBrand brand;
        public AppLinks links;
        public AppContactInformation contact;
    }
}