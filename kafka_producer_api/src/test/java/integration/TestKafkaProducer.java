package integration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaConsumer;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaProducer;
import producer.api.KafkaProducer;
import utils.TestsConstants;

@EmbeddedKafka(brokerProperties = {TestsConstants.KAFKA_LOG_DIRECTORY_PROPERTY})
class TestKafkaProducer {
    @Test
    void testProducerIntegrationWithConsumer(@EmbeddedKafkaProducer(keySerializerClass = StringSerializer.class
            , valueSerializerClass = StringSerializer.class) Producer<String, String> producer
            , @EmbeddedKafkaConsumer(properties = {TestsConstants.EMBEDDED_CONSUMER_GROUP_ID_PROPERTY
            , TestsConstants.EMBEDDED_CONSUMER_AUTO_OFFSET_RESET_PROPERTY}
            , keyDeserializerClass = StringDeserializer.class
            , topics = TestsConstants.TEST_TOPIC
            , valueDeserializerClass = StringDeserializer.class) Consumer<String, String> consumer) {
        KafkaProducer<String> kafkaProducer = new KafkaProducer<String>(producer);
        kafkaProducer.sendMessage(TestsConstants.TEST_TOPIC, TestsConstants.TEST_MESSAGE_CONTENT);
        assertConsumerMessagePolling(consumer, TestsConstants.TEST_MESSAGE_CONTENT);
    }

    private void assertConsumerMessagePolling(Consumer<String, String> consumer, String expectedMessageContent) {
        ConsumerRecords<String, String> consumedRecords = consumer.poll(
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS);
        ConsumerRecord<String, String> record = consumedRecords.iterator().next();
        Assertions.assertEquals(expectedMessageContent, record.value());
    }
}
