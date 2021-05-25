package producer.api;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.TestsConstants;

class TestKafkaProducer {
    private org.apache.kafka.clients.producer.Producer<String, String> mockKafkaProducer;

    public TestKafkaProducer() {
        mockKafkaProducer = Mockito.mock(org.apache.kafka.clients.producer.Producer.class);
    }

    @Test
    void testCloseProducer() {
        KafkaProducer<String> kafkaProducer = new KafkaProducer<String>(mockKafkaProducer);
        kafkaProducer.closeProducer();
        Mockito.verify(mockKafkaProducer).close();
    }

    @Test
    void testSendMessage() {
        KafkaProducer<String> kafkaProducer = new KafkaProducer<String>(mockKafkaProducer);
        kafkaProducer.sendMessage(TestsConstants.TEST_TOPIC, TestsConstants.TEST_MESSAGE_CONTENT);
        ProducerRecord<String, String> expectedRecord = new ProducerRecord<String, String>(TestsConstants.TEST_TOPIC,
                TestsConstants.TEST_MESSAGE_CONTENT);
        Mockito.verify(mockKafkaProducer).send(expectedRecord);
    }
}
