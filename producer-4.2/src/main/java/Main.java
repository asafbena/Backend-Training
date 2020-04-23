
public class Main {
    public static void main(String args[]) {

        final String KAFKA_SERVER_URL = "localhost";
        final int KAFKA_SERVER_PORT = 9092;
        final String CLIENT_ID = "MySampleProducer";
        final String topicName = "MyTopic2";
        final String SCHEMA_REGISTRY_HOST = "localhost";
        final int SCHEMA_REGISTRY_PORT = 8081;

        SampleProducer mySampleProducer = new SampleProducer(KAFKA_SERVER_URL, KAFKA_SERVER_PORT, CLIENT_ID, SCHEMA_REGISTRY_HOST, SCHEMA_REGISTRY_PORT);
        MyIdentity myIdentityExample = new MyIdentity("Ariel", "Berkovich", "Israel", 23, "Rexi");

        while (true) {
            long key = System.currentTimeMillis();
            mySampleProducer.sendMessage(topicName, key, myIdentityExample);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
