import backend.training.Identification;
import consumer.api.KafkaAvroConsumer;
import consumer.api.KafkaStringConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaStringConsumer kafkaStringConsumer = new KafkaStringConsumer(Constants.BROKER);
        KafkaAvroConsumer<Identification> kafkaAvroConsumer = new KafkaAvroConsumer<Identification>(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        kafkaStringConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaStringConsumer.closeConsumer();

        kafkaAvroConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        ConsumerRecord<String, Identification> avroMessage = kafkaAvroConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaAvroConsumer.closeConsumer();
    }
}
