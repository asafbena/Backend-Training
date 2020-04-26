package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

public class KafkaConsumer<T> implements Runnable{
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private String broker;
    private Thread consumerThread;
    protected org.apache.kafka.clients.consumer.KafkaConsumer<String, T> consumer;
    protected Properties consumerProperties;

    public KafkaConsumer(String broker, String valueDeserializerPath, String subscribedTopic) {
        this.broker = broker;
        initializeConsumer(valueDeserializerPath);
        buildConsumerThread();
        subscribe_to(subscribedTopic);
    }

    public void initializeConsumer(String valueDeserializerPath) {
        initializeConsumerProperties(valueDeserializerPath);
        consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, T>(consumerProperties);
    }

    public void subscribe_to(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
        LOGGER.info("The consumer is now subscribed to {} topic", topicName);
    }

    public void run() {
        consumerThread.start();
    }

    public void closeConsumer() {
        LOGGER.info("Stopping the consumer activity");
        consumer.close();
    }

    private void buildConsumerThread() {
        consumerThread = new Thread(() -> {
            LOGGER.info("Kafka consumer Attempting to poll a message within {} milliseconds", Constants.POLLING_TIMEOUT_MS);
            while(true) {
                handleConsumedRecords();
            }
        });
    }

    private void handleConsumedRecords() {
        ConsumerRecords<String, T> records = consumer.poll(Duration.ofMillis(Constants.POLLING_TIMEOUT_MS));
        for (ConsumerRecord<String, T> record : records) {
            handleReceivedRecord(record);
        }
    }

    private void handleReceivedRecord(ConsumerRecord<String, T> record) {
        T recordContent = record.value();
        LOGGER.info("The consumer managed to poll the following message: {}", recordContent);
        additionalRecordHandling(recordContent);
    }

    protected void additionalRecordHandling(T consumedRecordContent) {
    }

    protected void initializeConsumerProperties(String valueDeserializerPath) {
        consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_NUMBER_OF_POLLED_MESSAGES);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerPath);
    }
}
