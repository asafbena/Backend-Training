package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.Configurations;
import org.ishaym.training.config.ConsumerProperties;
import org.ishaym.training.config.KafkaProperties;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class MessagesConsumer {
    private static final Logger LOGGER = LogManager.getLogger(MessagesConsumer.class);

    private Consumer<Integer, Person> consumer;

    private Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the consumer properties object");

        Configurations configurations = Constants.genInstance().getConfigurations();
        KafkaProperties kafkaProperties = configurations.getKafkaProperties();
        ConsumerProperties consumerProperties = configurations.getConsumerProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
        props.put("group.id", consumerProperties.getGroupId());
        props.put("key.deserializer", consumerProperties.getKeyDeserializer());
        props.put("value.deserializer", consumerProperties.getValueDeserializer());
        props.put("specific.avro.reader", consumerProperties.isSpecificAvroReader());

        return props;
    }

    public MessagesConsumer() throws IOException {
        LOGGER.debug("started creating the kafka consumer");

        consumer = new KafkaConsumer<>(createKafkaProperties());
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singleton(topic));
    }

    public Thread consume() {
        return new Thread(new org.ishaym.training.runnable.KafkaConsumer(consumer));
    }
}
