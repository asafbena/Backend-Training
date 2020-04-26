package org.ishaym.training;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.ishaym.training.common.TestConstants;
import org.ishaym.training.tests.TestItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafka;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaConsumer;
import org.unbrokendome.embedded.kafka.junit5.EmbeddedKafkaProducer;

import java.io.IOException;
import java.util.Collections;

@EmbeddedKafka
public class TestMessagesProducer {

    private static final String TEST_BOOTSTRAP_SERVER = "localhost:9092";

    private static final String TEST_TOPIC_NAME = "test-topic";

    private static final String TEST_CONSUMER_AUTO_OFFSET_RESET = "earliest";
    private static final String TEST_CONSUMER_GROUP_ID = "test-group";

    private static final int TEST_CONSUMER_POLLING_TIME = 10000;

    private TestItem test;

    @BeforeEach
    public void setUpClass() throws IOException {
        test = TestConstants.getTestsItem();
    }

    @Test
    public void testProducerSendOnlyOneMessageShouldGetOneMessage(
            @EmbeddedKafkaProducer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER
                    },
                    keySerializerClass = IntegerSerializer.class,
                    valueSerializerClass = StringSerializer.class
            ) Producer<Integer, String> producer,
            @EmbeddedKafkaConsumer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER,
                            "auto.offset.reset=" + TEST_CONSUMER_AUTO_OFFSET_RESET,
                            "group.id=" + TEST_CONSUMER_GROUP_ID
                    },
                    topics = TEST_TOPIC_NAME,
                    keyDeserializerClass = IntegerDeserializer.class,
                    valueDeserializerClass = StringDeserializer.class
            ) Consumer<Integer, String> consumer) {

        consumer.subscribe(Collections.singleton(TEST_TOPIC_NAME));
        MessagesProducer myProducer = new MessagesProducer(producer);
        myProducer.sendMessage(TEST_TOPIC_NAME, test.getKey(), test.getValue());

        Assertions.assertEquals(1, consumer.poll(TEST_CONSUMER_POLLING_TIME).count(),
                "wrong number of messages");
    }

    @Test
    public void testProducerSendOnlyTwoMessageShouldGetTwoMessages(
            @EmbeddedKafkaProducer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER
                    },
                    keySerializerClass = IntegerSerializer.class,
                    valueSerializerClass = StringSerializer.class
            ) Producer<Integer, String> producer,
            @EmbeddedKafkaConsumer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER,
                            "auto.offset.reset=" + TEST_CONSUMER_AUTO_OFFSET_RESET,
                            "group.id=" + TEST_CONSUMER_GROUP_ID
                    },
                    topics = TEST_TOPIC_NAME,
                    keyDeserializerClass = IntegerDeserializer.class,
                    valueDeserializerClass = StringDeserializer.class
            ) Consumer<Integer, String> consumer) {

        consumer.subscribe(Collections.singleton(TEST_TOPIC_NAME));
        MessagesProducer myProducer = new MessagesProducer(producer);
        myProducer.sendMessage(TEST_TOPIC_NAME, test.getKey(), test.getValue());
        myProducer.sendMessage(TEST_TOPIC_NAME, test.getKey(), test.getValue());

        Assertions.assertEquals(2, consumer.poll(TEST_CONSUMER_POLLING_TIME).count(),
                "wrong number of messages");
    }

    @Test
    public void testProducerSendMessageShouldSendCorrectKey(
            @EmbeddedKafkaProducer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER
                    },
                    keySerializerClass = IntegerSerializer.class,
                    valueSerializerClass = StringSerializer.class
            ) Producer<Integer, String> producer,
            @EmbeddedKafkaConsumer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER,
                            "auto.offset.reset=" + TEST_CONSUMER_AUTO_OFFSET_RESET,
                            "group.id=" + TEST_CONSUMER_GROUP_ID
                    },
                    topics = TEST_TOPIC_NAME,
                    keyDeserializerClass = IntegerDeserializer.class,
                    valueDeserializerClass = StringDeserializer.class
            ) Consumer<Integer, String> consumer) {

        consumer.subscribe(Collections.singleton(TEST_TOPIC_NAME));
        MessagesProducer myProducer = new MessagesProducer(producer);
        myProducer.sendMessage(TEST_TOPIC_NAME, test.getKey(), test.getValue());

        for (ConsumerRecord<Integer, String> record : consumer.poll(
                TEST_CONSUMER_POLLING_TIME)) {
            Assertions.assertEquals(Integer.valueOf(test.getKey()), record.key(), "wrong key");
            return;
        }
        Assertions.fail("the consumer did not consumer the data");
    }

    @Test
    public void testProducerSendMessageShouldSendCorrectValue(
            @EmbeddedKafkaProducer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER
                    },
                    keySerializerClass = IntegerSerializer.class,
                    valueSerializerClass = StringSerializer.class
            ) Producer<Integer, String> producer,
            @EmbeddedKafkaConsumer(
                    properties = {
                            "bootstrap.servers=" + TEST_BOOTSTRAP_SERVER,
                            "auto.offset.reset=" + TEST_CONSUMER_AUTO_OFFSET_RESET,
                            "group.id=" + TEST_CONSUMER_GROUP_ID
                    },
                    topics = TEST_TOPIC_NAME,
                    keyDeserializerClass = IntegerDeserializer.class,
                    valueDeserializerClass = StringDeserializer.class
            ) Consumer<Integer, String> consumer) {

        consumer.subscribe(Collections.singleton(TEST_TOPIC_NAME));
        MessagesProducer myProducer = new MessagesProducer(producer);
        myProducer.sendMessage(TEST_TOPIC_NAME, test.getKey(), test.getValue());

        for (ConsumerRecord<Integer, String> record : consumer.poll(TEST_CONSUMER_POLLING_TIME)) {
            Assertions.assertEquals(test.getValue(), record.value(), "wrong value");
            return;
        }
        Assertions.fail("the consumer did not consumer the data");
    }

    @Test
    public void testProducerCloseShouldActivateClose() {
        Producer<Integer, String> mockProducer = Mockito.mock(Producer.class);
        MessagesProducer myProducer = new MessagesProducer(mockProducer);
        myProducer.close();
        Mockito.verify(mockProducer, Mockito.times(1)).close();
    }
}


