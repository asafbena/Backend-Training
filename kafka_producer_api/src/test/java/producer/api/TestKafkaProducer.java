package producer.api;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import utils.TestsConstants;

public class TestKafkaProducer {
    private org.apache.kafka.clients.producer.Producer<String, String> mockKafkaProducer;

    public TestKafkaProducer() {
        mockKafkaProducer = Mockito.mock(org.apache.kafka.clients.producer.Producer.class);
    }

    @Test
    public void testCloseProducer() {
        KafkaProducer<String> kafkaProducer = new KafkaProducer<String>(mockKafkaProducer);
        kafkaProducer.closeProducer();
        Mockito.verify(mockKafkaProducer).close();
    }

    @Test
    public void testSendMessage() {
        KafkaProducer<String> kafkaProducer = new KafkaProducer<String>(mockKafkaProducer);
        kafkaProducer.sendMessage(TestsConstants.TEST_TOPIC, TestsConstants.TEST_MESSAGE_CONTENT);
        ProducerRecord<String, String> expectedRecord = new ProducerRecord<String, String>(TestsConstants.TEST_TOPIC,
                TestsConstants.TEST_MESSAGE_CONTENT);
        Mockito.verify(mockKafkaProducer).send(expectedRecord);
    }
}
