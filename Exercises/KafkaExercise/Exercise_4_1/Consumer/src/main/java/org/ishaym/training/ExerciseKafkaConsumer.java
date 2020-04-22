package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.ishaym.training.common.Common;
import org.ishaym.training.constants.KafkaProperties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Properties;

public class ExerciseKafkaConsumer {
    private static final Logger LOGGER = LogManager.getLogger(ExerciseKafkaConsumer.class);

    private static final String CONSUMER_GROUP_ID = "string-messages-consumer-group-id";
    private static final int POLLING_TIMEOUT = 100;

    private Consumer<Integer, String> consumer;

    private Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the consumer properties object");

        KafkaProperties propertiesFromFile = Common.getKafkaPropertiesFromFile();

        Properties props = new Properties();
        props.put("bootstrap.servers", propertiesFromFile.getBootstrapServer());
        props.put("group.id", CONSUMER_GROUP_ID);
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        return props;
    }

    public ExerciseKafkaConsumer() throws IOException {
        LOGGER.debug("started creating the kafka consumer");

        consumer = new KafkaConsumer<>(createKafkaProperties());
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singleton(topic));
    }

    public void consume() {
        Thread thread = new Thread(
                () -> {
                    while (!Thread.currentThread().isInterrupted()) {
                        ConsumerRecords<Integer, String> records = consumer.poll(POLLING_TIMEOUT);
                        for (ConsumerRecord<Integer, String> record : records) {
                            String output = MessageFormat.format(
                                    "message key: {0} , message value: {1}", record.key(),
                                    record.value());
                            LOGGER.info(output);
                        }
                    }
                }
        );
        thread.start();
    }
}
