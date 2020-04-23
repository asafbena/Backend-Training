
public class Main {
    public static void main(String args[]) {
        org.apache.log4j.BasicConfigurator.configure();

        final String KAFKA_SERVER_URL = "localhost";
        final int KAFKA_SERVER_PORT = 9092;
        final String CLIENT_ID = "MySampleConsumer";
        final String topicName = "test";
        final String SCHEMA_REGISTRY_HOST = "localhost";
        final int SCHEMA_REGISTRY_PORT = 8081;

        SampleConsumer mySampleProducer = new SampleConsumer(KAFKA_SERVER_URL, KAFKA_SERVER_PORT, CLIENT_ID, SCHEMA_REGISTRY_HOST, SCHEMA_REGISTRY_PORT);
        mySampleProducer.subscribe(topicName);
        mySampleProducer.startConsumeLoop();

    }
}
