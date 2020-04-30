import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.Properties;

public class StringConsumer {
    private Consumer<String, String> consumer;

    public StringConsumer(String server) {
        Properties props = setUpProperties(server);
        consumer = new KafkaConsumer<String, String>(props);
    }

    public void subscribe() {
        consumer.subscribe(Collections.singletonList(Constants.STRING_TEST_TOPIC));
    }

    public void runConsumer() {
        while(true) {
            handleRecords(consumer.poll(Long.MAX_VALUE));
        }
    }

    private Properties setUpProperties(String server) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.STRING_CONSUMER_GROUP_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    private void handleRecords(ConsumerRecords<String, String> records) {
        records.forEach(record -> {
            handleSingleRecord(record);
        });
    }

    private void handleSingleRecord(ConsumerRecord<String, String> record) {
        System.out.println("Consumer Record: " + record.value());
    }
}
