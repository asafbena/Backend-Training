package consumer.api;

import backend.training.ExtendedIdentification;
import backend.training.Identification;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.AvroMessagesExpander;
import utils.Constants;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;

public class KafkaAvroConsumer extends KafkaConsumer<Identification> {
    public KafkaAvroConsumer(String broker) {
        super(broker, Constants.AVRO_DESERIALIZER_PATH, Constants.AVRO_MESSAGES_TOPIC);
    }

    @Override
    protected void additionalRecordHandling(Identification identification) {
        ExtendedIdentification extendedIdentification = AvroMessagesExpander.expandToExtendedIdentification(identification);
        LOGGER.info("The created extended identification message is: {}", extendedIdentification);
    }

    @Override
    protected void initializeConsumerProperties(String valueDeserializerPath) {
        super.initializeConsumerProperties(valueDeserializerPath);
        consumerProperties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG,
                Constants.SPECIFIC_AFRO_DESERIALIZATION);
        consumerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, Constants.SCHEMA_REGISTRY_URL);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.AVRO_DESERIALIZER_PATH);
    }
}
