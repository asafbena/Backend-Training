public class StringConsumerMain {

    public static void main(String[] args) {
        StringConsumer stringConsumer = new StringConsumer(Constants.BOOTSTRAP_SERVER);
        stringConsumer.runConsumer();
    }

}
