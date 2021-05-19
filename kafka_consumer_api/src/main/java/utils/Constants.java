package utils;

public class Constants {
    public static final String BROKER = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://192.168.1.14:8081";
    public static final int MAX_NUMBER_OF_POLLED_MESSAGES = 1;
    public static final String AVRO_DESERIALIZER_PATH = "io.confluent.kafka.serializers.KafkaAvroDeserializer";
    public static final String STRINGS_DESERIALIZER_PATH = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String CONSUMED_TOPIC = "test";
    public static final long POLLING_TIMEOUT_MS = 10000;
}
