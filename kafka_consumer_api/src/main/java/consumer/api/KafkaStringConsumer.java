package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import utils.Constants;

public class KafkaStringConsumer extends KafkaConsumer<String> {
    public KafkaStringConsumer(String broker) {
        super(broker);
        initializeConsumer();
    }

    @Override
    public void initializeConsumer() {
        super.initializeConsumer();
        this.consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(this.consumerProperties);
    }
}
