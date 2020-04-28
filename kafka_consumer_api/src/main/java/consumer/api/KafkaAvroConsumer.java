package consumer.api;

import backend.training.ExtendedIdentification;
import backend.training.Identification;
import utils.AvroMessagesExpander;
import utils.Constants;

public class KafkaAvroConsumer extends KafkaConsumer<Identification> {
    public KafkaAvroConsumer(org.apache.kafka.clients.consumer.KafkaConsumer<String, Identification> consumer,
           Long pollingMessageTimeoutMs, Boolean isTestingMode) {
        super(consumer, Constants.AVRO_MESSAGES_TOPIC, pollingMessageTimeoutMs, isTestingMode);
    }

    @Override
    protected void additionalRecordHandling(Identification identification) {
        ExtendedIdentification extendedIdentification = AvroMessagesExpander.expandToExtendedIdentification(identification);
        LOGGER.info("The created extended identification message is: {}", extendedIdentification);
    }
}
