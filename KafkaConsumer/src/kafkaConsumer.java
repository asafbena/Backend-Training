import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.consumer.Consumer;
import java.util.Collections;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;


public class kafkaConsumer implements Runnable {
    private Logger logger;
    private Consumer<Long, String> consumer;
    private consumerConstants constants;
    private Properties props;

    private void setProps(){
        props = new Properties();
        props.put("bootstrap.servers", constants.BOOTSTRAP_SERVERS);
        props.put("max.poll.records",constants.MAX);
        props.put("group.id",constants.GROUP);
        props.put("key.deserializer",constants.KEY);
        props.put("value.deserializer",constants.VALUE);
    }

    public kafkaConsumer(){
        constants = new consumerConstants("constants.yaml");
        setProps();
        createConsumer();
    }

    private void createConsumer() {
        consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(constants.TOPIC));
    }

    public void run(){
        logger = Logger.getLogger("String");
        ConsumerRecords<Long, String> consumerRecords = consumer.poll(constants.POLL_TIME);
        consumerRecords.forEach(record -> {
            logger.log(Level.INFO,record.value());
        });
        consumer.commitAsync();
    }
}

