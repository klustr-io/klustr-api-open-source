package io.klustr.permissions.kafka;

import org.joda.time.DateTime;

public class PermissionRecord {
    public PermissionAction action;
    public String namespace;
    public String relation;
    public String subject;
    public String id;
    public DateTime timestamp;

    public static PermissionRecordBuilder builder() {
        return new PermissionRecordBuilder();
    }

    public static final class PermissionRecordBuilder {
        private PermissionAction action;
        private String namespace;
        private String relation;
        private String subject;
        private String id;
        private DateTime timestamp;

        private PermissionRecordBuilder() {
        }

        public PermissionRecordBuilder withAction(PermissionAction action) {
            this.action = action;
            return this;
        }

        public PermissionRecordBuilder withNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public PermissionRecordBuilder withRelation(String relation) {
            this.relation = relation;
            return this;
        }

        public PermissionRecordBuilder withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        public PermissionRecordBuilder withId(String id) {
            this.id = id;
            return this;
        }

        public PermissionRecordBuilder withTimestamp(DateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public PermissionRecord build() {
            PermissionRecord permissionRecord = new PermissionRecord();
            permissionRecord.action = this.action;
            permissionRecord.id = this.id;
            permissionRecord.subject = this.subject;
            permissionRecord.timestamp = this.timestamp;
            permissionRecord.namespace = this.namespace;
            permissionRecord.relation = this.relation;
            return permissionRecord;
        }
    }
}
