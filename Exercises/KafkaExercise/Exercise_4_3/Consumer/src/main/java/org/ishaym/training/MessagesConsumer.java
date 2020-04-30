package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;


import java.io.IOException;
import java.text.MessageFormat;
import java.time.Duration;
import java.util.Collections;

public class MessagesConsumer implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(MessagesConsumer.class);

    private final Consumer<Integer, Person> consumer;

    private void logMessages(ConsumerRecords<Integer, Person> records) {
        for (ConsumerRecord<Integer, Person> record : records) {
            String output = MessageFormat.format("message key: {0} , message value: {1}",
                    record.key(), record.value());
            LOGGER.info(output);
        }
    }

    public MessagesConsumer(Consumer<Integer, Person> consumer) {
        LOGGER.info("started creating the kafka consumer");

        this.consumer = consumer;
    }

    public void subscribe(String topic) {
        LOGGER.info("subscribing to the given topic");

        consumer.subscribe(Collections.singleton(topic));
    }

    @Override
    public void run() {
        LOGGER.info("running the data consumption thread");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                logMessages(consumer.poll(Duration.ofMillis(Constants.getConsumerProperties().
                        getPollingTimeoutInMilliSeconds())));
            } catch (IOException e) {
                LOGGER.fatal(e);
                System.exit(-1);
            }

        }
    }
}
