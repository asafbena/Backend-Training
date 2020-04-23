import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class SampleProducer implements Runnable {

    private KafkaProducer<Long, MyIdentity> producer;


    public SampleProducer(String KAFKA_SERVER_URL, int KAFKA_SERVER_PORT, String CLIENT_ID) {
        initProducer();
    }

    private void initProducer() {
        Properties properties;
        properties = new Properties();
        properties.put("bootstrap.servers", Constants.KAFKA_SERVER_URL + ":" + Constants.KAFKA_SERVER_PORT);
        properties.put("client.id", Constants.CLIENT_ID);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.LongSerializer");
        properties.put("value.serializer", "io.confluent.kafka.serializers.KafkaAvroSerializer");
        properties.put("schema.registry.url", "http://" + Constants.SCHEMA_REGISTRY_HOST + ":" + Constants.SCHEMA_REGISTRY_PORT);

        producer = new KafkaProducer<>(properties);
    }

    private void startProducingLoop() {
        while (true) {
            long key = System.currentTimeMillis();
            producer.send(new ProducerRecord<>(Constants.TOPIC_NAME, key, Constants.IDENTITY_EXAMPLE));

            try {
                Thread.sleep(Constants.DELAY_BETWEEN_MESSAGES);
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
