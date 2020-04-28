package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.TestsConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestKafkaConsumer {
    private org.apache.kafka.clients.consumer.KafkaConsumer<String, String> mockKafkaConsumer;

    public TestKafkaConsumer() {
        mockKafkaConsumer = Mockito.mock(org.apache.kafka.clients.consumer.KafkaConsumer.class);
    }

    @Test
    public void testCloseConsumer() {
        KafkaConsumer<String> kafkaConsumer = new KafkaConsumer<String>(mockKafkaConsumer, TestsConstants.TEST_TOPIC,
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS, TestsConstants.IS_TESTING_MODE);
        kafkaConsumer.closeConsumer();
        Mockito.verify(mockKafkaConsumer).close();
    }

    @Test
    public void testSubscribeTo() {
        KafkaConsumer<String> kafkaConsumer = new KafkaConsumer<String>(mockKafkaConsumer, TestsConstants.TEST_TOPIC,
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS, TestsConstants.IS_TESTING_MODE);
        kafkaConsumer.subscribe_to(TestsConstants.ADDITIONAL_TEST_TOPIC);
        Mockito.verify(mockKafkaConsumer).subscribe(Collections.singleton(TestsConstants.ADDITIONAL_TEST_TOPIC));
    }

    @Test
    public void testConsumerRunMethod() {
        KafkaConsumer<String> kafkaConsumer = new KafkaConsumer<String>(mockKafkaConsumer, TestsConstants.TEST_TOPIC,
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS, TestsConstants.IS_TESTING_MODE);
        ConsumerRecords<String, String> expectedConsumerRecords = getExpectedConsumedRecords();
        Mockito.when(mockKafkaConsumer.poll(TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS))
                .thenReturn(expectedConsumerRecords);
        kafkaConsumer.run();
        Mockito.verify(mockKafkaConsumer).poll(TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS);
    }

    @Test
    public void testPollConsumedRecords() {
        KafkaConsumer<String> kafkaConsumer = new KafkaConsumer<String>(mockKafkaConsumer, TestsConstants.TEST_TOPIC,
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS, TestsConstants.IS_TESTING_MODE);
        ConsumerRecords<String, String> expectedConsumerRecords = getExpectedConsumedRecords();
        Mockito.when(mockKafkaConsumer.poll(TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS))
                .thenReturn(expectedConsumerRecords);
        Assertions.assertEquals(expectedConsumerRecords, kafkaConsumer.pollConsumedRecords());
    }

    private ConsumerRecords<String, String> getExpectedConsumedRecords() {
        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new HashMap<>();
        records.put(TestsConstants.TOPIC_PARTITION, Collections.singletonList(TestsConstants.CONSUMER_RECORD));
        return new ConsumerRecords<>(records);
    }
}
