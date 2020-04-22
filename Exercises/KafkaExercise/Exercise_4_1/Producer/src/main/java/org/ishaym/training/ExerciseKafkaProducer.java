package org.ishaym.training;

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

    private Producer<Integer, String> producer;

    private Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the producer properties object");

        KafkaProperties propertiesFromFile = Common.getKafkaPropertiesFromFile();

        Properties props = new Properties();
        props.put("bootstrap.servers", propertiesFromFile.getBootstrapServer());
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        return props;
    }

    public ExerciseKafkaProducer() throws IOException {
        LOGGER.debug("started creating the kafka producer");

        producer = new KafkaProducer<>(createKafkaProperties());
    }

    public void close() {
        producer.close();
    }

    public void sendMessage(String topic, Integer messageKey, String messageValue) {
        LOGGER.debug("sending data");

        producer.send(new ProducerRecord<>(topic, messageKey, messageValue));
    }
}
