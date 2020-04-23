import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Properties;

public class SampleConsumer implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(this));
    private KafkaConsumer<Integer, String> consumer;

    public SampleConsumer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID) {
        initConsumer();
        subscribe(Constants.TOPIC_NAME);
    }

    private void initConsumer() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Constants.KAFKA_SERVER_URL + ":" + Constants.KAFKA_SERVER_PORT);
        properties.put("client.id", Constants.CLIENT_ID);
        properties.put("group.id", Constants.GROUP_ID);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        consumer = new KafkaConsumer<>(properties);
    }

    private void subscribe(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
    }

    public void startConsumeLoop() {
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Constants.CONSUMER_WAITING_DURATION);
            for (ConsumerRecord<Integer, String> record : records) {
                logger.debug("recieved the messege:" + record.value());
            }
        }
    }

    @Override
    public void run() {
        startConsumeLoop();
    }
}
