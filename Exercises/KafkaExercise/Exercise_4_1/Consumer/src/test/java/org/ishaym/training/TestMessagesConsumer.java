package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.ishaym.training.common.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class TestMessagesConsumer {

    private static String topicName;

    private Consumer<Integer, String> mockConsumer;

    @BeforeAll
    public static void setUpClass() {
        topicName = "test-topic";
    }

    @BeforeEach
    public void setUpTest() {
        mockConsumer = Mockito.mock(Consumer.class);
    }

    @Test
    public void testIsSubscribeOfMockConsumerBeingUsedShouldBeUsed() {
        MessagesConsumer myConsumer = new MessagesConsumer(mockConsumer);
        myConsumer.subscribe(topicName);
        Mockito.verify(mockConsumer, Mockito.times(1)).subscribe(
                Collections.singleton(topicName));

    }

    @Test
    public void testIsPollOfMockConsumerBeingUsedShouldBeUsedAtLeastOnce() {
        try {
            MessagesConsumer myConsumer = new MessagesConsumer(mockConsumer);
            myConsumer.subscribe(topicName);
            Thread t = new Thread(myConsumer);
            t.start();
            TimeUnit.SECONDS.sleep(5);
            t.interrupt();
            t.join();
            Mockito.verify(mockConsumer, Mockito.atLeastOnce()).poll(
                    Constants.getConsumerProperties().getPollingTimeoutInMilliSeconds());
        } catch (InterruptedException | IOException e) {
            Assertions.fail(e.getMessage());
        }
    }
}
