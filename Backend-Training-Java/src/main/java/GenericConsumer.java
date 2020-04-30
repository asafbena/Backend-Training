import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.Human;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public abstract class GenericConsumer<K, V> {
    private Consumer<K, V> consumer;

    public GenericConsumer(String server) {
        Properties props = setUpProperties(server);
        consumer = new KafkaConsumer<K, V>(props);
    }

    public void subscribe() {
        consumer.subscribe(Collections.singletonList(Constants.STRING_TEST_TOPIC));
    }

    public void runConsumer() {
        while(true) {
            handleRecords(consumer.poll(Duration.ZERO));
        }
    }

    protected abstract void handleSingleRecord(ConsumerRecord<K, V> record);

    protected abstract Properties setUpProperties(String server);

    private void handleRecords(ConsumerRecords<K, V> records) {
        records.forEach(record -> {
            handleSingleRecord(record);
        });
    }

}
