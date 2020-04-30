package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private final AdminClient adminClient;

    public KafkaEnvironmentSetUp(AdminClient adminClient) {
        LOGGER.info("setting up the kafka environment manager");

        this.adminClient = adminClient;
    }

    public boolean isTopicExists(String topic)
            throws ExecutionException, InterruptedException {
        LOGGER.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(topic);
    }

    public void createTopic(String topicName, int topicNumPartitions, short topicReplicationFactor,
                            int topicCreationTimeInSeconds) throws ExecutionException,
            InterruptedException, TimeoutException {
        LOGGER.debug("started creating the new topic");

        NewTopic newTopic = new NewTopic(topicName, topicNumPartitions, topicReplicationFactor);
        final CreateTopicsResult createTopicsResult =
                adminClient.createTopics(Collections.singleton(newTopic));
        createTopicsResult.values().get(topicName).get(
                topicCreationTimeInSeconds, TimeUnit.SECONDS);
    }
}
