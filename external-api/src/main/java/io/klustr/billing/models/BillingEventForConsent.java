package io.klustr.billing.models;

import io.klustr.utils.U;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

public class BillingEventForConsent extends BillingEvent {

    @Deprecated
    public BillingEventForConsent() {
    }
    public BillingEventForConsent(String project_id, String user_id, DateTime timestamp) {
        // obuscate the user id for security
        user_id = U.md5(user_id);
        this.properties.put("user_id", user_id);
        this.properties.put("project_id", project_id);
        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(timestamp.getMillis());
        DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm");
        this.transaction_id = yyyyMMdd.print(new DateTime(timestamp)) + "_consent_" + project_id + "_" + U.md5(user_id);
    }

    @Override
    public String getLagoMappedBillingCode() {
        return "consent";
    }
}
