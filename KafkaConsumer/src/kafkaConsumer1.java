import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import java.util.Collections;
import java.util.Properties;
import java.io.*;

public class kafkaConsumer1 implements Runnable{
    private  String TOPIC;
    private  String BOOTSTRAP_SERVERS;
    private Properties props;
    private String filename;

    public kafkaConsumer1(String file_name){
        TOPIC = "test";
        BOOTSTRAP_SERVERS = "localhost:9092";
        filename = file_name;
    }

    Properties createConsumerProperties(){
        Properties tmpProps = new Properties();
        tmpProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        tmpProps.put("group.id", "recordConsumerGroup");
        tmpProps.put("max.poll.records", "10");
        tmpProps.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        tmpProps.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        return tmpProps;
    }

    private Consumer<Long, String> createConsumer() {
        props=createConsumerProperties();
        final Consumer<Long, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(TOPIC));
        return consumer;
    }

    void runConsumer(){
        try{
            FileWriter writer = new FileWriter(filename);
            BufferedWriter lineWriter = new BufferedWriter(writer);
            final Consumer<Long, String> consumer = createConsumer();
            while (true) {
                final ConsumerRecords<Long, String> consumerRecords = consumer.poll(1000);
                consumerRecords.forEach(record -> {
                    try {
                        lineWriter.write(record.value()+"\n");
                        lineWriter.flush();
                    }
                    catch(Exception e){
                        System.out.println("Error happened: "+e.getMessage());
                    }
                });
                consumer.commitAsync();
            }
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }

    public void run(){
        runConsumer();
    }
}
