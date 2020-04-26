import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Properties;

public class SampleConsumer implements Runnable {

    private Logger logger = Logger.getLogger(String.valueOf(this));
    private KafkaConsumer<Long, MyIdentity> consumer;

    public SampleConsumer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID) {
        initConsumer();
    }

    private void initConsumer() {
        consumer = new KafkaConsumer<>(getConsumerProperties());
    }

    public void subscribe(String topicName) {
        consumer.subscribe(Collections.singleton(topicName));
    }

    private Properties getConsumerProperties() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", Constants.KAFKA_SERVER_URL + ":" + Constants.KAFKA_SERVER_PORT);
        properties.put("client.id", Constants.CLIENT_ID);
        properties.put("group.id", Constants.GROUP_ID);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
        properties.put("value.deserializer", "io.confluent.kafka.serializers.KafkaAvroDeserializer");
        properties.put("schema.registry.url", "http://" + Constants.SCHEMA_REGISTRY_HOST + ":" + Constants.SCHEMA_REGISTRY_PORT);
        properties.put(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, true);
        return properties;
    }

    @Override
    public void run() {
        while (true) {
            ConsumerRecords<Long, MyIdentity> records = consumer.poll(Constants.CONSUMER_WAITING_DURATION);
            for (ConsumerRecord<Long, MyIdentity> record : records) {
                logger.debug("recieved the messege:" + record.value());
            }
        }
    }
}
