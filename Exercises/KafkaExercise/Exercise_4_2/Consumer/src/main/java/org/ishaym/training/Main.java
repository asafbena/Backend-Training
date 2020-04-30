package org.ishaym.training;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.ConstantBuilds;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.TopicProperties;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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

            LOGGER.debug("setting up topic for consumption of data");
            if (!kafkaEnvironmentSetUp.isTopicExists(topicProperties.getName())) {
                kafkaEnvironmentSetUp.createTopic(topicProperties.getName(),
                        topicProperties.getNumPartitions(),
                        (short) topicProperties.getReplicationFactor(),
                        topicProperties.getCreationTimeoutSeconds());
            }

            LOGGER.debug("creating the messages consumer");
            MessagesConsumer consumer = new MessagesConsumer(
                    ConstantBuilds.buildConsumerFromConstants());

            LOGGER.debug("subscribing to the requested topic");
            consumer.subscribe(topicProperties.getName());

            LOGGER.debug("creating a new thread that uses the consumer");
            Thread thread = new Thread(consumer);

            LOGGER.debug("starting the consumption of data");
            thread.start();

        } catch (InterruptedException e) {
            LOGGER.fatal(e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | TimeoutException | IOException e) {
            LOGGER.fatal(e);
            System.exit(-1);
        } catch (RuntimeException e) {
            LOGGER.fatal("other runtime error");
            LOGGER.fatal(e);
            System.exit(-1);
        }
    }

}
