import consumer.api.KafkaAvroConsumer;
import consumer.api.KafkaStringConsumer;
import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaStringConsumer kafkaStringConsumer = new KafkaStringConsumer(Constants.BROKER);
        KafkaAvroConsumer kafkaAvroConsumer = new KafkaAvroConsumer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        ConsumerRecord<String, String> message = kafkaStringConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaStringConsumer.closeConsumer();
        if (message != null) {
            System.out.println(message.value());
        }

        kafkaAvroConsumer.subscribe_to(Constants.CONSUMED_TOPIC);
        ConsumerRecord<String, SpecificRecord> avroMessage = kafkaAvroConsumer.pollMessage(Constants.POLLING_TIMEOUT_MS);
        kafkaAvroConsumer.closeConsumer();
        System.out.println(avroMessage);
    }
}
