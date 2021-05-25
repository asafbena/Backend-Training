package utils;

public class Constants {
    public static final String SCHEMA_REGISTRY_URL_COMPONENT_NAME = "schema.registry.url";
    public static final String BROKER = "localhost:9092";
    public static final String SCHEMA_REGISTRY_URL = "http://localhost:8081";
    public static final int MAX_NUMBER_OF_POLLED_MESSAGES = 1;
    public static final String AVRO_DESERIALIZER_PATH = "io.confluent.kafka.serializers.KafkaAvroDeserializer";
    public static final String STRINGS_DESERIALIZER_PATH = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String STRING_MESSAGES_TOPIC = "string-messages-test-topic";
    public static final String AVRO_MESSAGES_TOPIC = "avro-messages-test-topic";
    public static final String SPECIFIC_AVRO_READER_CONFIG = "specific.avro.reader";
    public static final long POLLING_TIMEOUT_MS = 200;
    public static final boolean SPECIFIC_AFRO_DESERIALIZATION = true;

    private Constants() {
    }
}
