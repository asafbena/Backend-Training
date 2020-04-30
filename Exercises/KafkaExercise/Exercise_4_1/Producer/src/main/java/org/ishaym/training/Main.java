package org.ishaym.training;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.ConstantBuilds;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.TopicCheckingProperties;
import org.ishaym.training.config.TopicProperties;
import org.ishaym.training.defaults.DefaultMessage;
import org.ishaym.training.exception.TopicNotFoundException;


import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            LOGGER.info("starting the program");

            LOGGER.debug("starting creating the admin client for environment setting");
            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    ConstantBuilds.buildAdminClientFromConstants());

            LOGGER.debug("fetching topic data");
            TopicProperties topicProperties = Constants.getTopicProperties();
            TopicCheckingProperties topicCheckingProperties = topicProperties.
                    getTopicCheckingProperties();

            LOGGER.debug("starting to check whether requested topic exists on kafka");
            kafkaEnvironmentSetUp.waitForTopic(topicProperties.getName(),
                    topicCheckingProperties.getNumOfAttempts(),
                    topicCheckingProperties.getTimeBetweenAttemptsInMillis());

            LOGGER.debug("starting to build the messages producer");
            MessagesProducer producer = new MessagesProducer(
                    ConstantBuilds.buildProducerFromConstants());

            LOGGER.debug("fetching message data");
            DefaultMessage defaultMessage = Constants.getDefaultMessage();

            LOGGER.debug("starting to send a message from the messages producer");
            producer.sendMessage(Constants.getTopicProperties().getName(),
                    defaultMessage.getKey(), defaultMessage.getValue());

            LOGGER.debug("message sent, closing the messages producer");
            producer.close();

            LOGGER.debug("producer closed and program finished");
        } catch (InterruptedException e) {
            LOGGER.fatal(e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | IOException | TopicNotFoundException e) {
            LOGGER.fatal(e);
            System.exit(-1);
        } catch (RuntimeException e) {
            LOGGER.fatal("another exception");
            LOGGER.fatal(e);
            System.exit(-1);
        }
    }
}
