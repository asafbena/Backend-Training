import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SampleProducer implements Runnable {

    private KafkaProducer<Integer, String> producer;


    public SampleProducer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID) {
        initProducer();
    }

    private void initProducer() {
        Properties properties;
        properties = new Properties();
        properties.put("bootstrap.servers", Constants.KAFKA_SERVER_URL + ":" + Constants.KAFKA_SERVER_PORT);
        properties.put("client.id", Constants.CLIENT_ID);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(properties);
    }

    private void sendMessage(String topicName, int key, String message) {
        producer.send(new ProducerRecord<>(topicName, key, message));
    }

    private void startProducingLoop() {
        while (true) {
            int key = (int) System.currentTimeMillis();
            sendMessage(Constants.TOPIC_NAME, key, Constants.MESSEGE);

            try {
                Thread.sleep(Constants.DELAY_BETWEEN_MESSEGES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        startProducingLoop();
    }
}
