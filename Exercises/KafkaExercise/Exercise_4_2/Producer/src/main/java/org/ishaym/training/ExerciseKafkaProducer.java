package org.ishaym.training;

import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Common;
import org.ishaym.training.constants.KafkaProperties;

import java.io.IOException;
import java.util.Properties;

public class ExerciseKafkaProducer {
    private static final Logger LOGGER = LogManager.getLogger(ExerciseKafkaProducer.class);

    private Producer<Integer, Person> producer;

    private Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the producer properties object");

        KafkaProperties propertiesFromFile = Common.getKafkaPropertiesFromFile();

        Properties props = new Properties();
        props.put("bootstrap.servers", propertiesFromFile.getBootstrapServer());
        props.put("schema.registry.url", propertiesFromFile.getSchemaRegistryUrl());
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", KafkaAvroSerializer.class.getName());

        return props;
    }

    public ExerciseKafkaProducer() throws IOException {
        LOGGER.debug("started creating the kafka producer");

        producer = new KafkaProducer<>(createKafkaProperties());
    }

    public void close() {
        producer.close();
    }

    public void sendMessage(String topic, Integer messageKey, Person messageValue) {
        LOGGER.debug("sending data");

        producer.send(new ProducerRecord<>(topic, messageKey, messageValue));
    }
}
