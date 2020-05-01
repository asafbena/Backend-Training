package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.KafkaFuture;
import org.ishaym.training.exception.TopicNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class TestKafkaEnvironmentSetUp {

    private static String topicName;
    private static int topicNumOfAttempts;
    private static int topicTimeBetweenAttemptsInMillis;

    private AdminClient mockAdminClient;


    @BeforeAll
    public static void setUpClass() throws IOException {
        topicName = "test-topic";
        topicNumOfAttempts = 5;
        topicTimeBetweenAttemptsInMillis = 1000;
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
    }

    @Test
    public void testWaitForTopicThatDoesNotExistShouldThrowException() {
        try {
            Mockito.when(mockAdminClient.listTopics().names().get().
                    contains(topicName)).thenReturn(false);

            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            Assertions.assertThrows(TopicNotFoundException.class, () ->
                    kafkaEnvironmentSetUp.waitForTopic(topicName,
                            topicNumOfAttempts,
                            topicTimeBetweenAttemptsInMillis));


        } catch (ExecutionException | InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testWaitForTopicThatDoesNotExistShouldCheckTopicAccordingToGivenAttempts() {
        try {
            Mockito.when(mockAdminClient.listTopics().names().get().
                    contains(topicName)).thenReturn(false);

            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            kafkaEnvironmentSetUp.waitForTopic(topicName,
                    topicNumOfAttempts,
                    topicTimeBetweenAttemptsInMillis);

            Assertions.fail();
        } catch (TopicNotFoundException e) {
            Assertions.assertDoesNotThrow(() ->
                    Mockito.verify(mockAdminClient.listTopics().names().get(), Mockito.times(
                            topicNumOfAttempts)).
                            contains(topicName));
        } catch (ExecutionException | InterruptedException e) {
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testWaitForTopicThatDoesExistShouldFindTopic() {
        try {
            Mockito.when(mockAdminClient.listTopics().names().get().
                    contains(topicName)).thenReturn(true);

            KafkaEnvironmentSetUp kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp(
                    mockAdminClient);

            kafkaEnvironmentSetUp.waitForTopic(topicName,
                    topicNumOfAttempts,
                    topicTimeBetweenAttemptsInMillis);

            Mockito.verify(mockAdminClient.listTopics().names().get(), Mockito.atLeastOnce()).
                    contains(topicName);

        } catch (ExecutionException | InterruptedException | TopicNotFoundException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
