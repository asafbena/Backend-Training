package org.ishaym.training.runnable;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.common.Constants;

import java.io.IOException;
import java.text.MessageFormat;

public class ConsumerAction implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ConsumerAction.class);

    private final Consumer<Integer, String> consumer;

    public ConsumerAction(Consumer<Integer, String> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void run() {
        LOGGER.debug("running the thread");

        while (!Thread.currentThread().isInterrupted()) {
            try {
                ConsumerRecords<Integer, String> records = consumer.poll(Constants.genInstance()
                        .getConsumerProperties().getPollingTimeoutInMilliSeconds());
                for (ConsumerRecord<Integer, String> record : records) {
                    String output = MessageFormat.format("message key: {0} , message value: {1}",
                            record.key(), record.value());
                    LOGGER.info(output);
                }
            } catch (IOException e) {
                LOGGER.fatal(e);
                System.exit(-1);
            }

        }
    }
}
