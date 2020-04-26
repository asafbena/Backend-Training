import consumer.api.KafkaAvroConsumer;
import consumer.api.KafkaConsumer;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaConsumer<String> kafkaStringConsumer = new KafkaConsumer<String>(Constants.BROKER,
                Constants.STRINGS_DESERIALIZER_PATH, Constants.STRING_MESSAGES_TOPIC);
        KafkaAvroConsumer kafkaAvroConsumer = new KafkaAvroConsumer(Constants.BROKER);

        kafkaStringConsumer.run();
        kafkaAvroConsumer.run();
    }
}
