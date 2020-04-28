package integration;

import consumer.api.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaConsumer;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaProducer;
import utils.TestsConstants;

@EmbeddedKafka(brokerProperties = {TestsConstants.KAFKA_LOG_DIRECTORY_PROPERTY})
public class TestKafkaConsumer {
    @Test
    public void testConsumerIntegrationWithProducer(@EmbeddedKafkaProducer(keySerializerClass = StringSerializer.class
            , valueSerializerClass = StringSerializer.class) Producer<String, String> producer
            , @EmbeddedKafkaConsumer(properties = {TestsConstants.EMBEDDED_CONSUMER_GROUP_ID_PROPERTY
            , TestsConstants.EMBEDDED_CONSUMER_AUTO_OFFSET_RESET_PROPERTY}
            , keyDeserializerClass = StringDeserializer.class
            , valueDeserializerClass = StringDeserializer.class)
                                                            org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer) {
        KafkaConsumer<String> kafkaConsumer = new KafkaConsumer<String>(consumer, TestsConstants.TEST_TOPIC,
                TestsConstants.EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS, TestsConstants.IS_TESTING_MODE);
        producer.send(new ProducerRecord<String, String>(TestsConstants.TEST_TOPIC,
                TestsConstants.TEST_MESSAGE_CONTENT));
        ConsumerRecords<String, String> consumerRecords = kafkaConsumer.pollConsumedRecords();
        Assertions.assertEquals(TestsConstants.TEST_MESSAGE_CONTENT, consumerRecords.iterator().next().value());
    }
}
