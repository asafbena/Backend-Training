import backend.training.Identification;
import producer.api.KafkaAvroProducer;
import producer.api.KafkaStringProducer;
import utils.Constants;

public class Main {
    private static final String STRING_MESSAGE_CONTENT = "simple message";

    private static final Identification IDENTIFICATION_AVRO_MESSAGE = Identification.newBuilder()
            .setFirstName("William")
            .setLastName("Smith")
            .setAge(42)
            .setBirthCountry("Spain")
            .setPetName("Rex")
            .build();

    public static void main(String[] args) {
        KafkaStringProducer kafkaStringProducer = new KafkaStringProducer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);
        KafkaAvroProducer kafkaAvroProducer = new KafkaAvroProducer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        kafkaStringProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, STRING_MESSAGE_CONTENT);
        kafkaStringProducer.closeProducer();

        kafkaAvroProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, IDENTIFICATION_AVRO_MESSAGE);
        kafkaAvroProducer.closeProducer();
    }
}
