package consumer.api;

import backend.training.ExtendedIdentification;
import backend.training.Identification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.AvroConverter;
import utils.Constants;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class KafkaAvroConsumer extends KafkaConsumer<Identification> {
    private String schemaRegistryUrl;

    public KafkaAvroConsumer(String broker, String schemaRegistryUrl) {
        super(broker);
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeConsumer();
        this.subscribe_to(Constants.CONSUMED_AVRO_MESSAGES_TOPIC);
    }

    @Override
    protected void additionalRecordHandling(Identification identification) {
        ExtendedIdentification extendedIdentification = AvroConverter.convertToExtendedIdentification(identification);
        LOGGER.info("The created extended identification message is: {}", extendedIdentification);
    }

    @Override
    protected void initializeConsumerProperties() {
        super.initializeConsumerProperties();
        this.consumerProperties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
                Constants.SPECIFIC_AFRO_DESERIALIZATION);
        this.consumerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, this.schemaRegistryUrl);
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.AVRO_DESERIALIZER_PATH);
    }
}
