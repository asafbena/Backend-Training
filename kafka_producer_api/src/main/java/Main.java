import backend.training.Identification;
import producer.api.KafkaAvroProducer;
import producer.api.KafkaStringProducer;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaStringProducer kafkaStringProducer = new KafkaStringProducer(Constants.BROKER);
        KafkaAvroProducer kafkaAvroProducer = new KafkaAvroProducer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, Constants.STRING_MESSAGE_CONTENT);
        kafkaStringProducer.closeProducer();

        kafkaAvroProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, Constants.IDENTIFICATION_AVRO_MESSAGE);
        kafkaAvroProducer.closeProducer();
    }
}
