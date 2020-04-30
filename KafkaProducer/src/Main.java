public class Main{
    public static void main(String[] args){
        kafkaProducer producer;
        if(args.length==0)
            producer = new kafkaProducer("No message specified");
        else
            producer = new kafkaProducer(args[0]);
        producer.runProducer();
    }
}