package utils;

import backend.training.Identification;

public class Constants {
    public static final String SCHEMA_REGISTRY_URL_COMPONENT_NAME = "schema.registry.url";
    public static final String BROKER = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://192.168.1.14:8081";
    public static final String AVRO_SERIALIZER_PATH = "io.confluent.kafka.serializers.KafkaAvroSerializer";
    public static final String STRINGS_SERIALIZER_PATH = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String SENT_SIMPLE_MESSAGES_TOPIC = "simple-test";
    public static final String SENT_AVRO_MESSAGES_TOPIC = "avro-test";
    public static final String STRING_MESSAGE_CONTENT = "simple message";
    public static final Identification IDENTIFICATION = Identification.newBuilder()
            .setFirstName("William")
            .setLastName("Smith")
            .setAge(42)
            .setPetName("Rex")
            .build();
}
