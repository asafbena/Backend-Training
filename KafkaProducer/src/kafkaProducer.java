
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.io.*;
import java.util.Scanner;

public class kafkaProducer{

    private String TOPIC;
    private String BOOTSTRAP_SERVERS;
    private Properties props;
    private Scanner scanner;

    Properties createProducerProperties(){
        Properties tmpProps = new Properties();
        tmpProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        tmpProps.put("acks", scanner.next());
        tmpProps.put("compression.type", scanner.next());
        tmpProps.put("retries", Integer.parseInt(scanner.next()));
        tmpProps.put("batch.size", Integer.parseInt(scanner.next()));
        tmpProps.put("linger.ms", Integer.parseInt(scanner.next()));
        tmpProps.put("buffer.memory", Integer.parseInt(scanner.next()));
        tmpProps.put("key.serializer", scanner.next());
        tmpProps.put("value.serializer", scanner.next());
        return tmpProps;
    }

    private Producer<String, String> createProducer() {
        props=createProducerProperties();
        return new KafkaProducer<>(props);
    }

    public kafkaProducer(){
        try {
            scanner = new Scanner(new File("constants.txt"));
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
        scanner = new Scanner(scanner.nextLine());
        TOPIC = scanner.next();
        BOOTSTRAP_SERVERS = scanner.next();

    }

    void runProducer(String message){
        ProducerRecord<String, String> record=null;
        try{
            final Producer<String,String> producer = createProducer();
            record = new ProducerRecord<>(TOPIC, message);
            producer.send(record);
            producer.flush();
            producer.close();
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
            return;
        }
    }
}


