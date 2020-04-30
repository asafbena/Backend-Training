public class HumanConsumerMain {
    public static void main(String[] args) {
        HumanConsumer humanConsumer = new HumanConsumer(Constants.BOOTSTRAP_SERVER);
        humanConsumer.subscribe();

        Thread humanConsumerThread = new Thread() {
            @Override
            public void run() {
                humanConsumer.runConsumer();
            }
        };

        humanConsumerThread.start();
    }
}
