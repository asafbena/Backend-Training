package producer.api;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;

import java.util.Properties;

public class KafkaProducer<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private String broker;
    protected Producer<String, T> producer;
    protected Properties producerProperties;

    public KafkaProducer(String broker, String valueDeserializerPath) {
        this.broker = broker;
        initializeProducer(valueDeserializerPath);
    }

    public void initializeProducer(String valueDeserializerPath) {
        initializeProducerProperties(valueDeserializerPath);
        producer = new org.apache.kafka.clients.producer.KafkaProducer<String, T>(producerProperties);
    }

    public void sendMessage(String messageTopic, T messageContent) {
        LOGGER.info("Sending the following message with topic {}: {}", messageTopic, messageContent);
        producer.send(new ProducerRecord<String, T>(messageTopic, messageContent));
    }

    public void closeProducer() {
        LOGGER.info("Stopping the producer activity.");
        producer.close();
    }

    protected void initializeProducerProperties(String valueDeserializerPath) {
        producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueDeserializerPath);
    }
}
