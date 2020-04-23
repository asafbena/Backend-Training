public class Main {
    public static void main(String args[]) {

        final String KAFKA_SERVER_URL = "localhost";
        final int KAFKA_SERVER_PORT = 9092;
        final String CLIENT_ID = "MySampleProducer";
        final String topicName = "MyTopic";

        SampleConsumer mySampleProducer = new SampleConsumer(KAFKA_SERVER_URL, KAFKA_SERVER_PORT, CLIENT_ID);
        mySampleProducer.subscribe(topicName);
        mySampleProducer.startConsumeLoop();
    }
}
