import kafka.security.auth.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

public class SampleConsumer {

    private Logger logger = Logger.getLogger(String.valueOf(this));
    private KafkaConsumer<Integer, String> consumer;
    private Properties properties;


    public SampleConsumer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID) {
        properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("group.id", "ConsumerGroup1");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.IntegerDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumer = new KafkaConsumer<>(properties);
    }

    public void subscribe(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
    }


    public void startConsumeLoop() {
        while (true) {
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Integer, String> record : records) {
                handleMessege(record);
            }
        }
    }

    public void handleMessege(ConsumerRecord<Integer, String> record) {
        logger.debug("recieved the messege:" + record.value());
    }
}
