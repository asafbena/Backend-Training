package utils;

public class Constants {
    public static final String SCHEMA_REGISTRY_URL_COMPONENT_NAME = "schema.registry.url";
    public static final String BROKER = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://192.168.1.14:8081";
    public static final String AVRO_SERIALIZER_PATH = "io.confluent.kafka.serializers.KafkaAvroSerializer";
    public static final String STRINGS_SERIALIZER_PATH = "org.apache.kafka.common.serialization.StringSerializer";
    public static final String SENT_MESSAGES_TOPIC = "test";
}
