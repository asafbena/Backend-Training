import org.apache.kafka.clients.producer.*;
import java.util.Properties;


public class kafkaProducer{
    private Properties props;
    private Producer<String,String> producer;
    private producerConstants constants;
    private ProducerRecord<String, String> record=null;

    private void setProps(){
        props = new Properties();
        props.put("bootstrap.servers", constants.BOOTSTRAP_SERVERS);
        props.put("acks",constants.ACKS);
        props.put("compression.type",constants.COMPRESSION);
        props.put("retries",constants.RETRIES);
        props.put("batch.size",constants.BATCH);
        props.put("linger.ms",constants.LINGER);
        props.put("buffer.memory",constants.BUFFER);
        props.put("key.serializer",constants.KEY);
        props.put("value.serializer",constants.VALUE);
    }

    public kafkaProducer(String message){
        constants = new producerConstants("constants.yaml");
        setProps();
        producer = new KafkaProducer<>(props);
        record = new ProducerRecord<>(constants.TOPIC, message);
    }

    public void runProducer(){
        producer.send(record);
        producer.flush();
        producer.close();
    }
}


