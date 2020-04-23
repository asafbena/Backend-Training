package org.mashov.tomera.backend;

import org.apache.kafka.clients.consumer.*;

import org.apache.kafka.clients.consumer.Consumer ;

import org.apache.kafka.common.serialization.IntegerDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;

import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;

import java.util.Properties;

public class kafkaConsumer {
    private final static String TOPIC = "testTopic";

    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";

    private static Consumer<Integer, String> createConsumer() {

        final Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,

                BOOTSTRAP_SERVERS);

        props.put(ConsumerConfig.GROUP_ID_CONFIG,

                "KafkaExampleConsumer");

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,

                IntegerDeserializer.class.getName());

        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,

                StringDeserializer.class.getName());

        // Create the consumer using props.

        final Consumer<Integer, String> consumer =

                new KafkaConsumer<>(props);

        // Subscribe to the topic.

        consumer.subscribe(Collections.singletonList(TOPIC));

        return consumer;

    }

    static void runConsumer() throws InterruptedException {

        final Consumer<Integer, String> consumer = createConsumer();

        while (true) {

            final ConsumerRecords<Integer, String> consumerRecords =
                    consumer.poll(1000);

            consumerRecords.forEach(record -> {
                System.out.printf("Consumer Record:(%d, %s, %d, %d)\n",
                        record.key(), record.value(),
                        record.partition(), record.offset());
            });

            consumer.commitAsync();

        }
    }
}
