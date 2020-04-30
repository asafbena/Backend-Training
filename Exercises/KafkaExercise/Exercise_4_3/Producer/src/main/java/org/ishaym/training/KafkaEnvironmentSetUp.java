package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.exception.TopicNotFoundException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private final AdminClient adminClient;

    public KafkaEnvironmentSetUp(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    private boolean isTopicExists(String topic)
            throws ExecutionException, InterruptedException {
        LOGGER.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(topic);
    }

    public void waitForTopic(String topic, int numberOfTopicExistenceCheckAttempts,
                             int timeBetweenChecksInMillis)
            throws ExecutionException,
            InterruptedException, TopicNotFoundException {
        LOGGER.debug("starting to wait for topic creation");

        for (int i = 0; i < numberOfTopicExistenceCheckAttempts; i++) {
            if (isTopicExists(topic)) {
                return;
            }
            TimeUnit.MILLISECONDS.sleep(
                    timeBetweenChecksInMillis);
        }
        throw new TopicNotFoundException();
    }

}
