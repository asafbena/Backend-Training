public class Main{
    public static void main(String[] args){
        kafkaProducer producer = new kafkaProducer();
        if(args.length==0)
            producer.runProducer("No message specified");
        else
            producer.runProducer(args[0]);
    }
}