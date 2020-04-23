
public class Main {
    public static void main(String args[]) {

        final String KAFKA_SERVER_URL = "localhost";
        final int KAFKA_SERVER_PORT = 9092;
        final String CLIENT_ID = "MySampleProducer";
        final String topicName = "MyTopic";

        SampleProducer mySampleProducer = new SampleProducer(KAFKA_SERVER_URL, KAFKA_SERVER_PORT, CLIENT_ID);

        while (true) {
            int key = (int)System.currentTimeMillis();
            mySampleProducer.sendMessage(topicName, key, "Hello");

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
