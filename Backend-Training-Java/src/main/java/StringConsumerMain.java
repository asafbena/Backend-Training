public class StringConsumerMain {

    public static void main(String[] args) {
        StringConsumer stringConsumer = new StringConsumer(Constants.BOOTSTRAP_SERVER);
        stringConsumer.subscribe();

        Thread stringConsumerThread = new Thread() {
            @Override
            public void run() {
                stringConsumer.runConsumer();
            }
        };

        stringConsumerThread.start();
    }

}
