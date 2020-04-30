package org.ishaym.training;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class MessagesProducer {
    private static final Logger LOGGER = LogManager.getLogger(MessagesProducer.class);

    private final Producer<Integer, String> producer;

    public MessagesProducer(Producer<Integer, String> producer) {
        LOGGER.info("setting up the messages producer");

        this.producer = producer;
    }

    public void close() {
        LOGGER.info("closing the messages producer");

        producer.close();
    }

    public void sendMessage(String topic, Integer messageKey, String messageValue) {
        LOGGER.info("sending data");

        producer.send(new ProducerRecord<>(topic, messageKey, messageValue));
    }
}
