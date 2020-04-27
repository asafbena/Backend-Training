package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.ishaym.training.common.Constants;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.config.ConsumerProperties;
import org.ishaym.training.config.KafkaProperties;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Properties;

public class MessagesConsumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessagesConsumer.class);

    private final Consumer<Integer, String> consumer;

    private static Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the consumer properties object");

        KafkaProperties kafkaProperties = Constants.getKafkaProperties();
        ConsumerProperties consumerProperties = Constants.getConsumerProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("group.id", consumerProperties.getGroupId());
        props.put("key.deserializer", consumerProperties.getKeyDeserializer());
        props.put("value.deserializer", consumerProperties.getValueDeserializer());

        return props;
    }

    private void logMessages(ConsumerRecords<Integer, String> records) {
        for (ConsumerRecord<Integer, String> record : records) {
            String output = MessageFormat.format("message key: {0} , message value: {1}",
                    record.key(), record.value());
            LOGGER.info(output);
        }
    }

    public MessagesConsumer() throws IOException {
        this(new KafkaConsumer<>(createKafkaProperties()));
    }

    public MessagesConsumer(Consumer<Integer, String> consumer) throws IOException {
        LOGGER.debug("started creating the kafka consumer");

        this.consumer = consumer;
        consumer.subscribe(Collections.singleton(Constants.getTopicProperties().getName()));
    }

    @Override
    public void run() {
        LOGGER.debug("running the thread");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                logMessages(consumer.poll(Constants.getConsumerProperties().
                        getPollingTimeoutInMilliSeconds()));
            } catch (IOException e) {
                LOGGER.fatal(e);
                System.exit(-1);
            }

        }
    }
}
