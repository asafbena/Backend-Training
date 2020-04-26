import producer.api.KafkaAvroProducer;
import producer.api.KafkaProducer;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaProducer<String> kafkaStringProducer = new KafkaProducer<String>(Constants.BROKER,
                Constants.STRINGS_SERIALIZER_PATH);
        KafkaAvroProducer kafkaAvroProducer = new KafkaAvroProducer(Constants.BROKER);

        kafkaStringProducer.sendMessage(Constants.STRING_MESSAGES_TOPIC, Constants.STRING_MESSAGE_CONTENT);
        kafkaStringProducer.closeProducer();

        kafkaAvroProducer.sendMessage(Constants.AVRO_MESSAGES_TOPIC, Constants.IDENTIFICATION);
        kafkaAvroProducer.closeProducer();
    }
}
