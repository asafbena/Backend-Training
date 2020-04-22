package producer.api;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;

import java.util.Properties;

public class KafkaProducer<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private String broker;
    protected Producer<String, T> producer;
    protected Properties producerProperties;

    public KafkaProducer(String broker) {
        this.broker = broker;
    }

    public void initializeProducer() {
        this.producerProperties = new Properties();
        this.producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, this.broker);
        this.producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
    }

    public void closeProducer() {
        LOGGER.info("Stopping the producer activity.");
        producer.close();
    }
}
