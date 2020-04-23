package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Configurations {
    private final KafkaProperties kafkaProperties;
    private final TopicProperties topicProperties;

    @JsonCreator
    public Configurations(@JsonProperty("kafkaProperties") final KafkaProperties kafkaProperties,
                          @JsonProperty("topicProperties") final TopicProperties topicProperties) {
        this.kafkaProperties = kafkaProperties;
        this.topicProperties = topicProperties;
    }

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }

    public TopicProperties getTopicProperties() {
        return topicProperties;
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "kafkaProperties=" + kafkaProperties +
                ", topicProperties=" + topicProperties +
                '}';
    }
}
