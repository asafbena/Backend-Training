package producer.api;

import org.apache.avro.specific.SpecificRecord;
import utils.Constants;

public class KafkaAvroProducer extends KafkaProducer<SpecificRecord> {
    public KafkaAvroProducer(String broker) {
        super(broker, Constants.AVRO_SERIALIZER_PATH);
    }

    @Override
    protected void initializeProducerProperties(String valueDeserializerPath) {
        super.initializeProducerProperties(valueDeserializerPath);
        producerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, Constants.SCHEMA_REGISTRY_URL);
    }
}
