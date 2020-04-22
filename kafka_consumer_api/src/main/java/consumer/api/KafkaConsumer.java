package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;

import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class KafkaConsumer<T> {
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private String broker;
    protected org.apache.kafka.clients.consumer.KafkaConsumer<String, T> consumer;
    protected Properties consumerProperties;

    public KafkaConsumer(String broker) {
        this.broker = broker;
    }

    public void initializeConsumer() {
        this.consumerProperties = new Properties();
        this.consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, this.broker);
        this.consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_NUMBER_OF_POLLED_MESSAGES);
        this.consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        this.consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
    }

    public void subscribe_to(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
        LOGGER.info("The consumer is now subscribed to {} topic", topicName);
    }

    public ConsumerRecord<String, T> pollMessage(long pollingTimeoutMilliSeconds) {
        LOGGER.info("Kafka consumer Attempting to poll a message within {} milliseconds", pollingTimeoutMilliSeconds);
        ConsumerRecords<String, T> records = consumer.poll(pollingTimeoutMilliSeconds);
        if (records.count() == 0) {
            LOGGER.error("The consumer could not find messages in the given time interval");
            return null;
        }
        ConsumerRecord<String, T> receivedMessage = records.iterator().next();
        LOGGER.debug("The consumer managed to poll the following message: {}", receivedMessage.value());
        return receivedMessage;
    }

    public void closeConsumer() {
        LOGGER.info("Stopping the consumer activity");
        consumer.close();
    }
}
