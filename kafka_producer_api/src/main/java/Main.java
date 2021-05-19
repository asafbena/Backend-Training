import backend.training.Identification;
import utils.Constants;

public class Main {
    public static void main(String[] args) {
        KafkaStringProducer kafkaStringProducer = new KafkaStringProducer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);
        KafkaAvroProducer kafkaAvroProducer = new KafkaAvroProducer(Constants.BROKER,
                Constants.SCHEMA_REGISTRY_URL);

        String messageContent = "simple message";
        kafkaStringProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, messageContent);
        kafkaStringProducer.closeProducer();

        Identification identification = Identification.newBuilder()
                .setFirstName("William")
                .setLastName("Foo-foo")
                .setAge(42)
                .setBirthCountry("UK")
                .setPetName("Bunny")
                .build();
        kafkaAvroProducer.sendMessage(Constants.SENT_MESSAGES_TOPIC, identification);
        kafkaAvroProducer.closeProducer();
    }
}
