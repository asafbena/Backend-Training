import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Constants;

public class KafkaStringProducer extends KafkaProducer<String> {

    public KafkaStringProducer(String broker, String schemaRegistryUrl) {
        super(broker, schemaRegistryUrl);
    }

    @Override
    public void initializeProducer() {
        super.initializeProducer();
        this.producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<String, String>(this.producerProperties);
    }

    public void sendMessage(String messageTopic, String sentMessage) {
        LOGGER.info("Sending the following string message with topic {}: {}", messageTopic, sentMessage);
        this.producer.send(new ProducerRecord<String, String>(messageTopic, sentMessage));
    }
}
