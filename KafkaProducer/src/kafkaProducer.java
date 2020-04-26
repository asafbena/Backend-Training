
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;
import java.io.*;

public class kafkaProducer implements Runnable{

    private String TOPIC;
    private String BOOTSTRAP_SERVERS;
    private Properties props;
    private String filename;

    Properties createProducerProperties(){
        Properties tmpProps = new Properties();
        tmpProps.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        tmpProps.put("acks", "all");
        tmpProps.put("compression.type", "snappy");
        tmpProps.put("retries", 1);
        tmpProps.put("batch.size", 16384);
        tmpProps.put("linger.ms", 5);
        tmpProps.put("buffer.memory", 33554432);
        tmpProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        tmpProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return tmpProps;
    }

    private Producer<String, String> createProducer() {
        props=createProducerProperties();
        return new KafkaProducer<>(props);
    }

    public kafkaProducer(String file_name){
        TOPIC = "test";
        BOOTSTRAP_SERVERS = "localhost:9092";
        filename=file_name;
    }

    void runProducer(){
        String message;
        ProducerRecord<String, String> record=null;
        try{
            FileReader reader = new FileReader(filename);
            BufferedReader line_reader=new BufferedReader(reader);
            final Producer<String,String> producer = createProducer();
            while((message = line_reader.readLine()) != null) {
                record = new ProducerRecord<>(TOPIC, message);
                producer.send(record);
            }
            producer.flush();
            producer.close();
        }
        catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
            return;
        }
    }
        public void run(){
            runProducer();
        }
}


