public class Constants {
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String CLIENT_ID = "Producer-4.2";
    public static final String TOPIC_NAME = "Topic-4.2";
    public static final MyIdentity IDENTITY_EXAMPLE = new MyIdentity("Ariel", "Berkovich", "Israel", 23, "Rexi");
    public static final int SCHEMA_REGISTRY_PORT = 8081;
    public static final String SCHEMA_REGISTRY_HOST = "localhost";
    public static final String KEY_SERIALIZER = "org.apache.kafka.common.serialization.LongSerializer";
    public static final String VALUE_SERIALIZER= "io.confluent.kafka.serializers.KafkaAvroSerializer";

}
