import java.time.Duration;

public class Constants {
    public static final String KAFKA_SERVER_URL = "localhost";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final String CLIENT_ID = "consumer-4.3";
    public static final String TOPIC_NAME = "Topic-4.3";
    public static final int SCHEMA_REGISTRY_PORT = 8081;
    public static final String SCHEMA_REGISTRY_HOST = "localhost";
    public static final String GROUP_ID = "ConsumerGroup-4.3";
    public static final Duration CONSUMER_WAITING_DURATION = Duration.ofMillis(100);
}
