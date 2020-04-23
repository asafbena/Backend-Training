import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class SampleConsumer {
    private Logger logger = Logger.getLogger(String.valueOf(this));
    private KafkaConsumer<Long, MyIdentity> consumer;
    private Properties properties;

    public SampleConsumer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID, String SCHEMA_REGISTRY_HOST, int SCHEMA_REGISTRY_PORT) {
        properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("group.id", "ConsumerGroup1");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        properties.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        properties.put("schema.registry.url", "http://" + SCHEMA_REGISTRY_HOST + ":" + SCHEMA_REGISTRY_PORT);

        consumer = new KafkaConsumer<>(properties);
    }

    public void subscribe(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
    }

    public void startConsumeLoop() {
        while (true) {
            ConsumerRecords<Long, MyIdentity> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<Long, MyIdentity> record : records) {
                handleMessege(record);
            }
        }
    }

    public void handleMessege(ConsumerRecord<Long, MyIdentity> record) {
        logger.debug("recieved an Identity:" + record.value());

    }
}