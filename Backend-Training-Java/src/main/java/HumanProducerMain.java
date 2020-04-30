import org.example.Human;

public class HumanProducerMain {
    public static void main(String[] args) {
        Human human = Human.newBuilder()
        .setFirstname("Bob")
        .setLastname("Vian")
        .setAge(28L)
        .build();
        HumanProducer humanProducer = new HumanProducer(Constants.BOOTSTRAP_SERVER);
        humanProducer.sendRecord(Constants.STRING_TEST_TOPIC, human);
        humanProducer.close();
    }
}
