package org.mashov.tomera.backend;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class kafkaProducer {
    private final KafkaProducer producer;

    private final String topic;

    private final Boolean isAsync;

    public static final String KAFKA_SERVER_URL = "localhost";

    public static final int KAFKA_SERVER_PORT = 9092;

    public static final String CLIENT_ID = "SampleProducer";

    public kafkaProducer(String topic, Boolean isAsync) {

        Properties properties = new Properties();

        properties.put("bootstrap.servers", KAFKA_SERVER_URL + ":" + KAFKA_SERVER_PORT);

        properties.put("client.id", CLIENT_ID);

        properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");

        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer(properties);

        this.topic = topic;

        this.isAsync = isAsync;
    }

    public void run() {

        int messageNo = 1;

        while (true) {

            String messageStr = "producer sent message number" + messageNo;

            long startTime = System.currentTimeMillis();

            if (isAsync) { // Send asynchronously

                producer.send(new ProducerRecord(topic,

                        messageNo,

                        messageStr));

            } else { // Send synchronously

                try {

                    producer.send(new ProducerRecord(topic,

                            messageNo,

                            messageStr)).get();

                    System.out.println("Sent message: (" + messageNo + ", " + messageStr + ")");

                } catch (InterruptedException | ExecutionException e) {

                    e.printStackTrace();

                    // handle the exception

                }

            }

            ++messageNo;

        }

    }
}

