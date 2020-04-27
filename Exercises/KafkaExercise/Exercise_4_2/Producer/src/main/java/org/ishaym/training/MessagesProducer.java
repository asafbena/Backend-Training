package org.ishaym.training;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.config.ProducerProperties;

import java.io.IOException;
import java.util.Properties;

public class MessagesProducer {
    private static final Logger LOGGER = LogManager.getLogger(MessagesProducer.class);

    private final Producer<Integer, Person> producer;

    private static Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the producer properties object");

        KafkaProperties kafkaProperties = Constants.getKafkaProperties();
        ProducerProperties producerProperties = Constants.getProducerProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("schema.registry.url", kafkaProperties.getSchemaRegistryUrl());
        props.put("key.serializer", producerProperties.getKeySerializer());
        props.put("value.serializer", producerProperties.getValueSerializer());

        return props;
    }

    public MessagesProducer() throws IOException {
        this(new KafkaProducer<>(createKafkaProperties()));
    }

    public MessagesProducer(Producer<Integer, Person> producer) {
        LOGGER.debug("started creating the kafka producer");

        this.producer = producer;
    }

    public void close() {
        producer.close();
    }

    public void sendMessage(String topic, Integer messageKey, Person messageValue) {
        LOGGER.debug("sending data");

        producer.send(new ProducerRecord<>(topic, messageKey, messageValue));
    }
}
