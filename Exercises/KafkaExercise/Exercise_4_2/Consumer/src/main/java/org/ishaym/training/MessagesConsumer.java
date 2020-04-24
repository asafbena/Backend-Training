package org.ishaym.training;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;
import org.ishaym.training.runnable.ConsumerAction;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;

public class MessagesConsumer {
    private static final Logger LOGGER = LogManager.getLogger(MessagesConsumer.class);

    private Consumer<Integer, Person> consumer;

    private Properties createKafkaProperties() throws IOException {
        LOGGER.debug("started creating the consumer properties object");

        Constants constants = Constants.genInstance();

        Properties props = new Properties();
        props.put("bootstrap.servers", constants.getKafkaProperties().getBootstrapServer());
        props.put("schema.registry.url", constants.getKafkaProperties().getSchemaRegistryUrl());
        props.put("group.id", constants.getConsumerProperties().getGroupId());
        props.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        props.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        props.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);

        return props;
    }

    public MessagesConsumer() throws IOException {
        LOGGER.debug("started creating the kafka consumer");

        consumer = new KafkaConsumer<>(createKafkaProperties());
    }

    public void subscribe(String topic) {
        consumer.subscribe(Collections.singleton(topic));
    }

    public void consume() {
        Thread thread = new Thread(new ConsumerAction(consumer));
        thread.start();
    }
}
