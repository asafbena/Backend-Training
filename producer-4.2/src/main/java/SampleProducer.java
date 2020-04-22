import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SampleProducer {
    private KafkaProducer<Long, MyIdentity> producer;
    private Properties properties;

    public SampleProducer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID, String SCHEMA_REGISTRY_HOST, int SCHEMA_REGISTRY_PORT) {
        properties = new Properties();
        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);
        properties.put("client.id", CLIENT_ID);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        properties.put("schema.registry.url", "http://" + KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);

        producer = new KafkaProducer<>(properties);
    }

    public void sendMessage(String topicName, Long key, MyIdentity Identity) {
        producer.send(new ProducerRecord<>(topicName, key, Identity));
    }
}
