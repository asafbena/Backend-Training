public class Main {
    public static void main(String args[]) {
        SampleProducer mySampleProducer = new SampleProducer(Constants.KAFKA_SERVER_URL, Constants.KAFKA_SERVER_PORT, Constants.CLIENT_ID);
        Thread mySampleProducerThread = new Thread(mySampleProducer);
        mySampleProducerThread.start();
        try {
            mySampleProducerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
