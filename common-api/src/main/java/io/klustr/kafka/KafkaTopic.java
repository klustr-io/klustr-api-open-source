package io.klustr.kafka;

public record KafkaTopic (String orgId, String topicName) {

    public String canonicalId() {
        return orgId + "." + topicName;
    }
}
