package org.ishaym.training;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ExerciseKafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(ExerciseKafkaProducer.class);

    private Producer<Integer, String> producer;

    public ExerciseKafkaProducer() {
        logger.debug("started creating the kafka producer");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<>(props);
    }

    public void close() {
        producer.close();
    }

    public void sendMessage(String topic, Integer messageKey, String messageValue) {
        logger.debug("sending data");

        producer.send(new ProducerRecord<>(topic, messageKey, messageValue));
    }
}
