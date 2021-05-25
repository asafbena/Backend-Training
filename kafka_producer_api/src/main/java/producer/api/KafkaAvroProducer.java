package producer.api;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.producer.Producer;

public class KafkaAvroProducer extends KafkaProducer<SpecificRecord> {
    public KafkaAvroProducer(Producer<String, SpecificRecord> producer) {
        super(producer);
    }
}
