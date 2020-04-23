package producer.api;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.Constants;

public class KafkaAvroProducer extends KafkaProducer<SpecificRecord> {
    private String schemaRegistryUrl;

    public KafkaAvroProducer(String broker, String schemaRegistryUrl) {
        super(broker);
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeProducer();
    }

    public void sendMessage(String messageTopic, SpecificRecord avroMessageContent) {
        LOGGER.info("Sending the following avro message with topic {}: {}", messageTopic, avroMessageContent);
        this.producer.send(new ProducerRecord<String, SpecificRecord>(messageTopic, avroMessageContent));
    }

    @Override
    protected void initializeProducerProperties() {
        super.initializeProducerProperties();
        this.producerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, this.schemaRegistryUrl);
        this.producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, Constants.AVRO_SERIALIZER_PATH);
    }
}
