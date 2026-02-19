package io.klustr.billing.models;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

public class BillingEventForGitLabStorageUsage extends BillingEvent {

    @Deprecated
    public BillingEventForGitLabStorageUsage() {
    }

    public BillingEventForGitLabStorageUsage(Context ctx) {
        this.properties.put("bytes", ctx.bytes);
        this.properties.put("operation_type", "add");
        this.properties.put("project_id", ctx.project_id);
        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(ctx.timestamp.getMillis());
        DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm");
        this.transaction_id = yyyyMMdd.print(new DateTime(ctx.timestamp)) + "_" + ctx.project_id;
        this.external_subscription_id = ctx.project_id;
    }

    @Override
    public String getLagoMappedBillingCode() {
        return "gitlab_storage_bytes";
    }

    public static Context builder() {
        return new Context();
    }

    public static class Context {
        private Integer bytes;
        private String project_id;
        private DateTime timestamp;

        private Context() {

        }

        public Context withTotal(int bytes) {
            this.bytes = bytes;
            return this;
        }

        public Context withProjectId(String project_id) {
            this.project_id = project_id;
            return this;
        }

        public Context withTimestamp(DateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public BillingEventForGitLabStorageUsage build() {
            if (StringUtils.isBlank(this.project_id)) {
                throw new RuntimeException("Invalid project ID");
            }

            if (this.bytes == null) {
                throw new RuntimeException("Invalid bytes");
            }
            if (this.timestamp == null) {
                throw new RuntimeException("Invalid timestamp");
            }
            return new BillingEventForGitLabStorageUsage(this);
        }
    }
}
