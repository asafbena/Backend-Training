package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configurations {

    @JsonProperty("kafkaProperties")
    private KafkaProperties kafkaProperties;

    @JsonProperty("topicProperties")
    private TopicProperties topicProperties;

    @JsonProperty("consumerProperties")
    private ConsumerProperties consumerProperties;

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
        return
                "Configurations{" +
                        "kafkaProperties = '" + kafkaProperties + '\'' +
                        ",topicProperties = '" + topicProperties + '\'' +
                        ",consumerProperties = '" + consumerProperties + '\'' +
                        "}";
    }
}