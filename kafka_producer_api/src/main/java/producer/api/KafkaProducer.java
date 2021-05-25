package producer.api;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KafkaProducer<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    protected Producer<String, T> producer;

    public KafkaProducer(Producer<String, T> producer) {
        this.producer = producer;
    }

    public void sendMessage(String messageTopic, T messageContent) {
        LOGGER.info("Sending the following message with topic {}: {}", messageTopic, messageContent);
        producer.send(new ProducerRecord<>(messageTopic, messageContent));
    }

    public void closeProducer() {
        LOGGER.info("Stopping the producer activity.");
        producer.close();
    }
}
