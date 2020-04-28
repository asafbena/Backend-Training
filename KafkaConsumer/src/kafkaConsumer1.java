import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;


public class kafkaConsumer1 extends consumerConstants implements Runnable {
    private Logger logger;
    private Consumer<Long, String> consumer;
    private consumerConstants constants;
    private Properties props;

    private void setProps(){
        props = new Properties();
        props.put("bootstrap.servers", constants.BOOTSTRAP_SERVERS);
        props.put("max.poll.records",constants.max);
        props.put("group.id",constants.group);
        props.put("key.deserializer",constants.key);
        props.put("value.deserializer",constants.value);
    }

    public kafkaConsumer1(){
        constants = new consumerConstants("constants.yaml");
        setProps();
        createConsumer();
        logger = Logger.getLogger("String");
    }

    private Consumer<Long, String> createConsumer() {
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(constants.TOPIC));
        return consumer;
    }

    private void writeToLog(){
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(constants.poll_time);
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
            while (true) {
                writeToLog();
            }
        }
            catch(Exception e){
            System.out.println("Error happened: "+e.getMessage());
        }
    }
}

