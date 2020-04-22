package consumer.api;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.Constants;

public class KafkaAvroConsumer extends KafkaConsumer<SpecificRecord> {
    private String schemaRegistryUrl;

    public KafkaAvroConsumer(String broker, String schemaRegistryUrl) {
        super(broker);
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeConsumer();
    }

    @Override
    public void initializeConsumer() {
        super.initializeConsumer();
        this.consumerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, this.schemaRegistryUrl);
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.AVRO_DESERIALIZER_PATH);
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, SpecificRecord>(this.consumerProperties);
    }
}
