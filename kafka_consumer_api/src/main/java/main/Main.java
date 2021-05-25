package main;

import backend.training.Identification;
import consumer.api.KafkaAvroConsumer;
import consumer.api.KafkaConsumer;
import utils.Constants;
import utils.ConsumerPropertiesBuilder;

import java.util.Properties;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        Properties consumerProperties = ConsumerPropertiesBuilder.buildConsumerProperties(
                Constants.STRINGS_DESERIALIZER_PATH, UUID.randomUUID().toString());
        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> innerConsumer =
                new org.apache.kafka.clients.consumer.KafkaConsumer<>(consumerProperties);
        Properties avroConsumerProperties = ConsumerPropertiesBuilder.buildAvroConsumerProperties(
                Constants.AVRO_DESERIALIZER_PATH, UUID.randomUUID().toString());
        org.apache.kafka.clients.consumer.KafkaConsumer<String, Identification> innerAvroConsumer =
                new org.apache.kafka.clients.consumer.KafkaConsumer<>(avroConsumerProperties);

        KafkaConsumer<String> kafkaStringConsumer = new KafkaConsumer<>(innerConsumer,
                Constants.STRING_MESSAGES_TOPIC, Constants.POLLING_TIMEOUT_MS, Boolean.FALSE);
        KafkaAvroConsumer kafkaAvroConsumer = new KafkaAvroConsumer(innerAvroConsumer, Constants.POLLING_TIMEOUT_MS,
                Boolean.FALSE);

        new Thread(kafkaStringConsumer).start();
        new Thread(kafkaAvroConsumer).start();
    }
}
