import backend.training.Identification;
import consumer.api.KafkaAvroConsumer;
import consumer.api.KafkaStringConsumer;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaStringConsumer kafkaStringConsumer = new KafkaStringConsumer(Constants.BROKER);
        KafkaAvroConsumer<Identification> kafkaAvroConsumer = new KafkaAvroConsumer<Identification>(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringConsumer.run();
        kafkaAvroConsumer.run();
    }
}
