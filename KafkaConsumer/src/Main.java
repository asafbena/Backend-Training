public class Main{
    public static void main(String[] args) throws Exception {
        kafkaConsumer consumer = new kafkaConsumer();
        Thread thread = new Thread(consumer);
        thread.start();
    }
}