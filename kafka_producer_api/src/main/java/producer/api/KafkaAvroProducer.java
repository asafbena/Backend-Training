package producer.api;

import backend.training.Identification;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Constants;

public class KafkaAvroProducer extends KafkaProducer<SpecificRecord> {
    public KafkaAvroProducer(String broker, String schemaRegistryUrl) {
        super(broker, schemaRegistryUrl);
    }

    @Override
    public void initializeProducer() {
        super.initializeProducer();
        this.producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.AVRO_SERIALIZER_PATH);
        this.producer = new org.apache.kafka.clients.producer.KafkaProducer<String, SpecificRecord>(this.producerProperties);
    }

    public void sendMessage(String messageTopic, Identification identificationMessage) {
        LOGGER.info("Sending the following avro message with topic {}: {}", messageTopic, identificationMessage);
        this.producer.send(new ProducerRecord<String, SpecificRecord>(messageTopic, identificationMessage));
    }
}
