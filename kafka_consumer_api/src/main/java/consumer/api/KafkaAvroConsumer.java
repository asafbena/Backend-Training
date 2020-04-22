package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.Constants;

public class KafkaAvroConsumer<S> extends KafkaConsumer<S> {
    private String schemaRegistryUrl;

    public KafkaAvroConsumer(String broker, String schemaRegistryUrl) {
        super(broker);
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeConsumer();
    }

    @Override
    protected void initializeConsumerProperties() {
        super.initializeConsumerProperties();
        this.consumerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, this.schemaRegistryUrl);
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.AVRO_DESERIALIZER_PATH);
    }
}
