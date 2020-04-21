import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import utils.Constants;

import java.util.Properties;

public class KafkaProducer<T> {
    private String broker;
    private String schemaRegistryUrl;
    protected Producer<String, T> producer;
    protected Properties producerProperties;

    public KafkaProducer(String broker, String schemaRegistryUrl) {
        this.broker = broker;
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeProducer();
    }

    public void initializeProducer() {
        this.producerProperties = new Properties();
        this.producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.broker);
        this.producerProperties.put("schema.registry.url", this.schemaRegistryUrl);
        this.producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
    }

    public void closeProducer() {
        producer.close();
    }
}
