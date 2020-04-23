package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.Constants;

public class KafkaStringConsumer extends KafkaConsumer<String> {
    public KafkaStringConsumer(String broker) {
        super(broker);
        initializeConsumer();
        this.subscribe_to(Constants.SIMPLE_CONSUMED_MESSAGES_TOPIC);
    }

    @Override
    protected void initializeConsumerProperties() {
        super.initializeConsumerProperties();
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
    }
}
