package io.klustr.permissions.kafka;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.permissions.*;
import io.klustr.permissions.dsl.Permissions;
import io.klustr.utils.Json;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;

import java.util.Set;

public class KafkaWrapedPermissionProvider implements PermissionProvider {

    private final PermissionProvider impl;

    private final KafkaTemplate<PermissionRecord> kafka;

    public KafkaWrapedPermissionProvider(@Value("${db-adapter.kafka.brokers}") String brokers,
                                         PermissionProvider impl, KafkaMetrics metrics) {
        this.impl = impl;
        this.kafka = new KafkaTemplate<PermissionRecord>(new KafkaTopic("klustr", "permissions"), new KafkaClientOptions().withBootstrapServers(brokers), metrics) {
            @Override
            protected String serialize(PermissionRecord value) {
                return Json.toJson(value);
            }

            @Override
            protected PermissionRecord deserialize(String value) {
                try {
                    return Json.parseGenericType(value, PermissionRecord.class);
                } catch (Exception ex) {
                    throw new RuntimeException(PermissionRecord.class + " --> " + value, ex);
                }
            }
        };
    }

    @Override
    public void grant(SubjectKey subject, Scope scope, Target object) {
        this.impl.grant(subject, scope, object);
        PermissionRecord msg = PermissionRecord.builder()
                .withAction(PermissionAction.Grant)
                .withNamespace(scope.namespace())
                .withSubject(subject.value())
                .withRelation(scope.relation())
                .withTimestamp(DateTime.now())
                .withId(object.value())
                .build();
        // messages for this should be ordinated on the object
        this.kafka.send(subject.value(), msg);
    }

    @Override
    public void revoke(SubjectKey subject, Scope scope, Target object) {
        this.impl.revoke(subject, scope, object);
        PermissionRecord msg = PermissionRecord.builder()
                .withAction(PermissionAction.Revoke)
                .withNamespace(scope.namespace())
                .withSubject(subject.value())
                .withRelation(scope.relation())
                .withTimestamp(DateTime.now())
                .withId(object.value())
                .build();
        // messages for this should be ordinated on the object
        this.kafka.send(subject.value(), msg);
    }

    @Override
    public void revokeAll(SubjectKey subject, Target object) {
        this.impl.revokeAll(subject);
        PermissionRecord msg = PermissionRecord.builder()
                .withAction(PermissionAction.RevokeAll)
                .withSubject(subject.value())
                .withTimestamp(DateTime.now())
                .withId(object.value())
                .build();
        // messages for this should be ordinated on the object
        this.kafka.send(subject.value(), msg);
    }

    @Override
    public void revokeAll(SubjectKey subject) {
        this.impl.revokeAll(subject);
        PermissionRecord msg = PermissionRecord.builder()
                .withAction(PermissionAction.RevokeAll)
                .withSubject(subject.value())
                .withTimestamp(DateTime.now())
                .build();
        // messages for this should be ordinated on the object
        this.kafka.send(subject.value(), msg);
    }

    @Override
    public boolean check(SubjectKey subject, Scope scope, Target object) {
        return this.impl.check(subject,scope,object);
    }

    @Override
    public Set<Relation> listRelationsForObject(Target object) {
        return this.impl.listRelationsForObject(object);
    }

    @Override
    public Set<Relation> listRelations(SubjectKey subject, String namespace, Target target) {
        return this.impl.listRelations(subject, namespace, target);
    }

    @Override
    public Permissions iam() {
        return this.impl.iam();
    }

    @Override
    public Health health() {
        return this.kafka.health();
    }
}
