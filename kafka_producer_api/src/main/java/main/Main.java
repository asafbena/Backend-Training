package main;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.Producer;
import producer.api.KafkaAvroProducer;
import producer.api.KafkaProducer;
import utils.Constants;
import utils.ProducerPropertiesBuilder;

import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties producerProperties = ProducerPropertiesBuilder.buildProducerProperties(
                Constants.STRINGS_SERIALIZER_PATH);
        Producer<String, String> innerProducer = new org.apache.kafka.clients.producer.KafkaProducer<>(
                producerProperties);
        Properties avroProducerProperties = ProducerPropertiesBuilder.buildAvroProducerProperties(
                Constants.AVRO_SERIALIZER_PATH);
        Producer<String, SpecificRecord> innerAvroProducer =
                new org.apache.kafka.clients.producer.KafkaProducer<>(avroProducerProperties);

        KafkaProducer<String> kafkaStringProducer = new KafkaProducer<>(innerProducer);
        KafkaAvroProducer kafkaAvroProducer = new KafkaAvroProducer(innerAvroProducer);

        kafkaStringProducer.sendMessage(Constants.STRING_MESSAGES_TOPIC, Constants.STRING_MESSAGE_CONTENT);
        kafkaStringProducer.closeProducer();

        kafkaAvroProducer.sendMessage(Constants.AVRO_MESSAGES_TOPIC, Constants.IDENTIFICATION);
        kafkaAvroProducer.closeProducer();
    }
}
