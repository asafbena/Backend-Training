package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Properties;

public class ExerciseKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExerciseKafkaConsumer.class);

    private static final String CONSUMER_GROUP_ID = "string-messages-consumer-group-id";
    private static final int POLLING_TIMEOUT = 100;

    private Consumer<Integer, String> consumer;

    public ExerciseKafkaConsumer() {
        LOGGER.debug("started creating the kafka producer");

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", CONSUMER_GROUP_ID);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(props);
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singleton(topic));
    }

    public void consume() {
        while (!Thread.currentThread().isInterrupted()) {
            ConsumerRecords<Integer, String> records = consumer.poll(POLLING_TIMEOUT);
            for (ConsumerRecord<Integer, String> record : records) {
                String output = MessageFormat.format(
                        "message key: {0} , message value: {1}", record.key(), record.value());
                LOGGER.info(output);
            }
        }
    }
}
