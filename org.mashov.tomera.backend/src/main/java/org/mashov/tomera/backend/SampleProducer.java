package org.mashov.tomera.backend;

public class SampleProducer {
    public static final String TOPIC = "testTopic";

    public static void main(String[] args) {
        boolean isAsync = false;

        kafkaProducer producer = new kafkaProducer(TOPIC, isAsync);

        producer.run();
    }
}
