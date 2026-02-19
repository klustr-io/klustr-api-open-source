package io.klustr.billing.models;

import io.klustr.utils.U;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class BillingEventForComputeInstanceUsage extends BillingEvent {

    @Deprecated
    public BillingEventForComputeInstanceUsage() {
    }

    @Override
    public String getLagoMappedBillingCode() {
        return "compute_usage_minutes";
    }

    private static final DateTimeFormatter yyyyMMdd = DateTimeFormat.forPattern("yyyy-MM-dd-HH:mm");

    public BillingEventForComputeInstanceUsage(Context ctx) {
        this.properties.put("minutes", ctx.minutes);
        this.properties.put("operation_type", "add");
        this.properties.put("instance_type", ctx.instance_type);
        this.properties.put("project_id", ctx.project_id);
        this.properties.put("instance_id", ctx.instance_id);

        String key = ctx.instance_type + "_" + ctx.instance_id + "_" + ctx.project_id;
        String uniqueID = yyyyMMdd.print(new DateTime(ctx.timestamp)) + "_" + ctx.project_id + "_" + U.md5(key);

        this.timestamp = TimeUnit.MILLISECONDS.toSeconds(ctx.timestamp.getMillis());
        this.transaction_id = uniqueID;
        this.external_subscription_id = ctx.project_id;
    }

    public static Context builder() {
        return new Context();
    }

    public static class Context {
        private Integer minutes;
        private String project_id;
        private String instance_type;
        private String instance_id;
        private DateTime timestamp;

        public Context() {

        }

        public Context withTotal(int minutes) {
            this.minutes = minutes;
            return this;
        }

        public Context withInstanceType(String instance_type) {
            this.instance_type = instance_type;
            return this;
        }

        public Context withInstanceId(String instance_id) {
            this.instance_id = instance_id;
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


        public BillingEventForComputeInstanceUsage build() {
            if (StringUtils.isBlank(this.instance_type)) {
                throw new RuntimeException("Invalid instance type");
            }
            if (StringUtils.isBlank(this.project_id)) {
                throw new RuntimeException("Invalid project ID");
            }

            if (this.minutes == null) {
                throw new RuntimeException("Invalid minutes");
            }
            if (this.timestamp == null) {
                throw new RuntimeException("Invalid timestamp");
            }
            return new BillingEventForComputeInstanceUsage(this);
        }
    }
}
