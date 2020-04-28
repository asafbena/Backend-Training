package consumer.api;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;

import java.util.Collections;

public class KafkaConsumer<T> implements Runnable{
    protected static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);

    private Boolean isTestingMode;
    private Long pollingMessageTimeoutMs;
    protected org.apache.kafka.clients.consumer.KafkaConsumer<String, T> consumer;

    public KafkaConsumer(org.apache.kafka.clients.consumer.KafkaConsumer<String, T> consumer,
           String subscribedTopic, Long pollingMessageTimeoutMs, Boolean isTestingMode) {
        this.isTestingMode = isTestingMode;
        this.pollingMessageTimeoutMs = pollingMessageTimeoutMs;
        this.consumer = consumer;
        subscribe_to(subscribedTopic);
    }

    public void subscribe_to(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
        LOGGER.info("The consumer is now subscribed to {} topic", topicName);
    }

    public void run() {
        LOGGER.info("Kafka consumer Attempting to poll a message within {} milliseconds", Constants.POLLING_TIMEOUT_MS);
        do {
            handleConsumedRecords();
        } while (isTestingMode == Boolean.FALSE);
    }

    public void closeConsumer() {
        LOGGER.info("Stopping the consumer activity");
        consumer.close();
    }

    public ConsumerRecords<String, T> pollConsumedRecords() {
        ConsumerRecords<String, T> records = consumer.poll(pollingMessageTimeoutMs);
        LOGGER.info("The consumer managed to poll {} records", records.count());
        return records;
    }

    private void handleConsumedRecords() {
        ConsumerRecords<String, T> records = pollConsumedRecords();
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
}
