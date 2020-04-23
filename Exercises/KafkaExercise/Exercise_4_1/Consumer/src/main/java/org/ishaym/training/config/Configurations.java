package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Configurations {
    private final KafkaProperties kafkaProperties;
    private final TopicProperties topicProperties;
    private final ConsumerProperties consumerProperties;

    @JsonCreator
    public Configurations(@JsonProperty("kafkaProperties") final KafkaProperties kafkaProperties,
                          @JsonProperty("topicProperties") final TopicProperties topicProperties,
                          @JsonProperty("consumerProperties") final ConsumerProperties
                                  consumerProperties) {
        this.kafkaProperties = kafkaProperties;
        this.topicProperties = topicProperties;
        this.consumerProperties = consumerProperties;
    }

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }

    public TopicProperties getTopicProperties() {
        return topicProperties;
    }

    public ConsumerProperties getConsumerProperties() {
        return consumerProperties;
    }

    @Override
    public String toString() {
        return "Configurations{" +
                "kafkaProperties=" + kafkaProperties +
                ", topicProperties=" + topicProperties +
                ", consumerProperties=" + consumerProperties +
                '}';
    }
}
