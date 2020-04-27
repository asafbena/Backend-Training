import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class StringProducer {
    private Producer<String, String> producer;

    public StringProducer(String server) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.producer = new KafkaProducer<String, String>(props);
    }

    public void sendMessage(String topicName, String msg) {
        this.producer.send(new ProducerRecord<>(topicName, msg));
    }

    public void close() {
        this.producer.close();
    }
}
