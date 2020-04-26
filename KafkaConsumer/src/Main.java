public class Main{
    public static void main(String[] args) throws Exception {
        kafkaConsumer1 consumer = new kafkaConsumer1("output.txt");
        Thread thread = new Thread(consumer);
        thread.start();
    }
}