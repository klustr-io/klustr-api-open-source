package io.klustr.kafka;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * A pair of the groupId and the topic to ensure we can subscribe to
 * a topic with multiple groups and create streams within a single JVM
 * for the same topic.
 */
class KafkaSubscription {
    private final Pair<String, KafkaTopic> pair;

    KafkaSubscription(String groupId, KafkaTopic topic) {
        this.pair = new ImmutablePair<>(groupId, topic);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof KafkaSubscription)) return false;
        Pair<String, KafkaTopic> b = ((KafkaSubscription) obj).pair;
        return pair.equals(b);
    }

    @Override
    public int hashCode() {
        return this.pair.hashCode();
    }
}
