import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class StringConsumer {
    private Consumer<String, String> consumer;

    public StringConsumer(String server) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.STRING_CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        this.consumer = new KafkaConsumer<String, String>(props);
        this.consumer.subscribe(Collections.singletonList(Constants.STRING_TEST_TOPIC));
    }

    public void runConsumer() {
        while(true) {
            ConsumerRecords<String, String> records = this.consumer.poll(Long.MAX_VALUE);
            records.forEach(record -> {
                System.out.println("Consumer Record: " + record.value());
            });
        }
    }
}
