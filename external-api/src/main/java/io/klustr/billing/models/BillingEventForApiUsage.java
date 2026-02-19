package io.klustr.billing.models;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

public class BillingEventForApiUsage extends BillingEvent {

    private String code;

    @Deprecated
    public BillingEventForApiUsage() {
    }

    public BillingEventForApiUsage(Context ctx) {
        this.properties.put("http_requests_total", ctx.total);
        this.properties.put("operation_type", "add");
        this.properties.put("api_name", ctx.api_code);
        this.properties.put("project_id", ctx.project_id);
        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(ctx.timestamp.getMillis());
        this.code = ctx.api_code;
        DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm");
        this.transaction_id = yyyyMMdd.print(new DateTime(ctx.timestamp)) + "_" + ctx.api_code + "_" + ctx.project_id;
        this.external_subscription_id = ctx.project_id;
    }

    @Override
    public String getLagoMappedBillingCode() {
        return code;
    }

    public static Context builder() {
        return new Context();
    }

    public static class Context {
        private Integer total;
        private String api_code;
        private String project_id;
        private DateTime timestamp;

        private Context() {

        }

        public Context withTotal(int total) {
            this.total = total;
            return this;
        }

        public Context withApiCode(String api_code) {
            this.api_code = api_code;
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



        public BillingEventForApiUsage build() {
            if (StringUtils.isBlank(this.api_code)) {
                throw new RuntimeException("Invalid API code");
            }
            if (StringUtils.isBlank(this.project_id)) {
                throw new RuntimeException("Invalid project ID");
            }

            if (this.total == null) {
                throw new RuntimeException("Invalid total");
            }
            if (this.timestamp == null) {
                throw new RuntimeException("Invalid timestamp");
            }
            return new BillingEventForApiUsage(this);
        }
    }
}
