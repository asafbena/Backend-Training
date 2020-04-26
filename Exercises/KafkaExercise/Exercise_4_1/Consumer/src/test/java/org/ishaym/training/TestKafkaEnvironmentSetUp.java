package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.KafkaFuture;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TestKafkaEnvironmentSetUp {

    private static String topicName;
    private static int topicNumPartitions;
    private static short topicReplicationFactor;
    private static int topicCreationTimeInSeconds;

    private AdminClient mockAdminClient;
    private CreateTopicsResult mockCreateTopicsResult;


    @BeforeAll
    public static void setUpClass() {
        topicName = "test-topic";
        topicNumPartitions = 1;
        topicReplicationFactor = 1;
        topicCreationTimeInSeconds = 10;
    }

    @BeforeEach
    public void setUp() throws ExecutionException, InterruptedException {
        mockAdminClient = Mockito.mock(AdminClient.class);
        Mockito.when(mockAdminClient.listTopics()).
                thenReturn(Mockito.mock(ListTopicsResult.class));
        Mockito.when(mockAdminClient.listTopics().names()).
                thenReturn(Mockito.mock(KafkaFuture.class));
        Mockito.when(mockAdminClient.listTopics().names().get()).
                thenReturn(Mockito.mock(Set.class));

        mockCreateTopicsResult = Mockito.mock(CreateTopicsResult.class);

        Mockito.when(mockCreateTopicsResult.values()).thenReturn(Mockito.mock(Map.class));
        Mockito.when(mockCreateTopicsResult.values().get(topicName)).
                thenReturn(Mockito.mock(KafkaFuture.class));
        Mockito.when(mockAdminClient.createTopics(Mockito.any(Collection.class)))
                .thenReturn(mockCreateTopicsResult);
    }

    @Test
    public void testIsTopicExistingWhenItDoesExistShouldReturnTrue() {
        try {
            Mockito.when(mockAdminClient.listTopics().names().get().
                    contains(topicName)).thenReturn(true);

            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            Assertions.assertTrue(kafkaEnvironmentSetUp.isTopicExists(topicName),
                    "error, should return true if topic exist");
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testIsTopicExistingWhenItDoesNotExistShouldReturnFalse() {
        try {
            Mockito.when(mockAdminClient.listTopics().names().get().
                    contains(topicName)).thenReturn(false);

            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            Assertions.assertFalse(kafkaEnvironmentSetUp.isTopicExists(topicName),
                    "error, should return false if topic doesn't exist");
        } catch (InterruptedException | ExecutionException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testCreateNewTopicShouldCreateNewTopicOnAdminClient() {
        try {
            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            kafkaEnvironmentSetUp.createTopic(topicName, topicNumPartitions, topicReplicationFactor,
                    topicCreationTimeInSeconds);

            Mockito.verify(mockAdminClient, Mockito.times(1)).createTopics(Mockito.any(
                    Collection.class));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Assertions.fail(e);
        }
    }

    @Test
    public void testCreateNewTopicShouldHandleCreationProcessInGivenTime() {
        try {
            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            kafkaEnvironmentSetUp.createTopic(topicName, topicNumPartitions, topicReplicationFactor,
                    topicCreationTimeInSeconds);

            Mockito.verify(mockAdminClient.createTopics(
                    Collections.singleton(new NewTopic(topicName, topicNumPartitions,
                            topicReplicationFactor))).values().get(topicName),
                    Mockito.times(1)
            ).get(topicCreationTimeInSeconds, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            Assertions.fail(e);
        }
    }
}
