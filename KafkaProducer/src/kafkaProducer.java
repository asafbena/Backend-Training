import org.apache.kafka.clients.producer.*;
import java.util.Properties;


public class kafkaProducer{
    private Properties props;
    private Producer<String,String> producer;
    private producerConstants constants;

    private void setProps(){
        props = new Properties();
        props.put("bootstrap.servers", constants.BOOTSTRAP_SERVERS);
        props.put("acks",constants.acks);
        props.put("compression.type",constants.compression);
        props.put("retries",constants.retries);
        props.put("batch.size",constants.batch);
        props.put("linger.ms",constants.linger);
        props.put("buffer.memory",constants.buffer);
        props.put("key.serializer",constants.key);
        props.put("value.serializer",constants.value);
    }

    public kafkaProducer(){
        constants = new producerConstants("constants.yaml");
        setProps();
        producer = new KafkaProducer<>(props);
    }

    public void runProducer(String message){
        ProducerRecord<String, String> record=null;
        try{
            record = new ProducerRecord<>(constants.TOPIC, message);
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


