import backend.training.Identification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.Constants;

public class KafkaAvroConsumer extends KafkaConsumer<Identification> {
    public KafkaAvroConsumer(String broker, String schemaRegistryUrl) {
        super(broker, schemaRegistryUrl);
    }

    @Override
    public void initializeConsumer() {
        super.initializeConsumer();
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.AVRO_DESERIALIZER_PATH);
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, Identification>(this.consumerProperties);
    }
}
