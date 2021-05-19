import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import utils.Constants;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class KafkaConsumer<T> {
    private String broker;
    private String schemaRegistryUrl;
    protected org.apache.kafka.clients.consumer.KafkaConsumer<String, T> consumer;
    protected Properties consumerProperties;

    public KafkaConsumer(String broker, String schemaRegistryUrl) {
        this.broker = broker;
        this.schemaRegistryUrl = schemaRegistryUrl;
        initializeConsumer();
    }

    public void initializeConsumer() {
        this.consumerProperties = new Properties();
        this.consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.broker);
        this.consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_NUMBER_OF_POLLED_MESSAGES);
        this.consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        this.consumerProperties.put("schema.registry.url", this.schemaRegistryUrl);
        this.consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
    }

    public void subscribe_to(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
    }

    public ConsumerRecord<String, T> pollMessage(long pollingTimeoutMilliSeconds) {
        ConsumerRecords<String, T> records = consumer.poll(pollingTimeoutMilliSeconds);
        if (records.count() == 0) {
            return null;
        }
        return records.iterator().next();
    }

    public void closeConsumer() {
        consumer.close();
    }
}
