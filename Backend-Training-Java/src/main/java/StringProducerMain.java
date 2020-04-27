public class StringProducerMain {

    public static void main(String[] args) {
        StringProducer stringProducer = new StringProducer(Constants.BOOTSTRAP_SERVER);
        stringProducer.sendMessage(Constants.STRING_TEST_TOPIC, "Hello");
        stringProducer.close();
    }

}
