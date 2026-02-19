package io.klustr.kafka.dbadapter;

import io.klustr.kafka.KafkaClientOptions;
import io.klustr.kafka.metrics.KafkaMetrics;
import io.klustr.kafka.KafkaTemplate;
import io.klustr.kafka.KafkaTopic;
import io.klustr.storage.DbAdapterListener;
import io.klustr.utils.Json;

public class DbAdapterKafkaBroadcaster<T> implements DbAdapterListener<T> {

    private final KafkaTemplate<T> kafka;

    public DbAdapterKafkaBroadcaster(Class<T> clazz, String db, String table, KafkaClientOptions opts, KafkaMetrics metrics) {
        String key = "database." + db + "." + table;
        this.kafka = new KafkaTemplate<>(new KafkaTopic("klustr", key), opts, metrics) {
            @Override
            protected String serialize(T value) {
                return Json.toJson(value);
            }

            @Override
            protected T deserialize(String value) {
                try {
                    return Json.parse(value, clazz);
                } catch (Exception ex) {
                    throw new RuntimeException(clazz + " --> " + value, ex);
                }
            }
        };
    }

    @Override
    public void onCreate(String id, T obj) {
        kafka.send(id + ":create", obj);
    }

    @Override
    public void onUpdate(String id, T obj) {
        kafka.send(id + ":update", obj);
    }

    @Override
    public void onDelete(String id, T obj) {
        kafka.send(id + ":delete", obj);
    }
}
