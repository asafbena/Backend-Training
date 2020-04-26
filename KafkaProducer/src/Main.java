public class Main{
    public static void main(String[] args){
        kafkaProducer producer = new kafkaProducer("input.txt");
        Thread thread = new Thread(producer);
        thread.start();
    }
}