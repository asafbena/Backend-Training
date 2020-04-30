import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.Human;

import java.util.Properties;

public abstract class GenericProducer<K, V> {
    private Producer<K, V> producer;

    public GenericProducer(String server) {
        Properties props = setUpProperties(server);
        producer = new KafkaProducer<K, V>(props);
    }

    public void sendRecord(String topicName, V v) {
        producer.send(new ProducerRecord<K, V>(topicName, v));
    }

    public void close() {
        producer.close();
    }

    protected abstract Properties setUpProperties(String server);
}
