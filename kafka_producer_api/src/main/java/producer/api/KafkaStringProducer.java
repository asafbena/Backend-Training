package producer.api;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Constants;

public class KafkaStringProducer extends KafkaProducer<String> {

    public KafkaStringProducer(String broker) {
        super(broker);
        initializeProducer();
    }

    public void sendMessage(String messageTopic, String sentMessage) {
        LOGGER.info("Sending the following string message with topic {}: {}", messageTopic, sentMessage);
        this.producer.send(new ProducerRecord<String, String>(messageTopic, sentMessage));
    }

    @Override
    public void initializeProducerProperties() {
        super.initializeProducerProperties();
        this.producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
    }
}
