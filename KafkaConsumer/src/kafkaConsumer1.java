import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Collections;
import java.util.Properties;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class kafkaConsumer1 implements Runnable{
    private  String TOPIC;
    private  String BOOTSTRAP_SERVERS;
    private Properties props;
    private String filename;
    private Scanner scanner;

    public kafkaConsumer1(String file_name){
        try {
            scanner = new Scanner(new File("constants.txt"));
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
        scanner = new Scanner(scanner.nextLine());
        TOPIC = scanner.next();
        BOOTSTRAP_SERVERS = scanner.next();
        filename = file_name;
    }

    Properties createConsumerProperties(){
        Properties tmpProps = new Properties();
        tmpProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        tmpProps.put("group.id", scanner.next());
        tmpProps.put("max.poll.records", scanner.next());
        tmpProps.put("key.deserializer", scanner.next());
        tmpProps.put("value.deserializer", scanner.next());
        return tmpProps;
    }

    private Consumer<Long, String> createConsumer() {
        props=createConsumerProperties();
        final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    private void writeToFile(final Consumer<Long, String> consumer,Logger logger){
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
        consumerRecords.forEach(record -> {
            try {
                logger.log(Level.INFO,record.value());
            }
            catch(Exception e){
                System.out.println("Error happened: "+e.getMessage());
            }
        });
        consumer.commitAsync();
    }

    public void run(){
        try {

            Logger logger = Logger.getLogger("String");
            final Consumer<Long, String> consumer = createConsumer();
            while (true) {
                writeToFile(consumer,logger);
            }
        }
            catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}

