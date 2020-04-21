import backend.training.Identification;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import utils.Constants;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        KafkaStringConsumer kafkaStringConsumer = new KafkaStringConsumer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);
        KafkaAvroConsumer kafkaAvroConsumer = new KafkaAvroConsumer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        ConsumerRecord<String, String> message = kafkaStringConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaStringConsumer.closeConsumer();
        System.out.println(message.value());

        kafkaAvroConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        ConsumerRecord<String, Identification> avroMessage = kafkaAvroConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaAvroConsumer.closeConsumer();
        System.out.println(avroMessage);
    }
}
