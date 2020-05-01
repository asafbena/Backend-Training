package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Configurations {

    @JsonProperty("kafkaProperties")
    private KafkaProperties kafkaProperties;

    @JsonProperty("topicProperties")
    private TopicProperties topicProperties;

    @JsonProperty("producerProperties")
    private ProducerProperties producerProperties;

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }

    public TopicProperties getTopicProperties() {
        return topicProperties;
    }

    public ProducerProperties getProducerProperties() {
        return producerProperties;
    }

    @Override
    public String toString() {
        return
                "Configurations{" +
                        "kafkaProperties = '" + kafkaProperties + '\'' +
                        ",topicProperties = '" + topicProperties + '\'' +
                        ",producerProperties = '" + producerProperties + '\'' +
                        "}";
    }
}