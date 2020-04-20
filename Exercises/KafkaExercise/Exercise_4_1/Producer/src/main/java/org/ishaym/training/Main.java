package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String CLIENT_ID = "producer-client-id";
    private static final String STRING_MESSAGES_TOPIC = "string-messages";
    private static final int NUMBER_OF_PARTITIONS = 1;
    private static final int FACTOR_OF_REPLICAS = 1;
    private static final int TOPICS_TIMEOUT = 10;
    private static final int MESSAGES_TIMEOUT = 3;

    private static int count = 1;

    private static ExerciseKafkaProducer createProducer() throws InterruptedException,
            ExecutionException, TimeoutException {
        setUpProducerEnvironment();
        return new ExerciseKafkaProducer();
    }

    private static void setUpProducerEnvironment() throws InterruptedException, ExecutionException,
            TimeoutException {
        logger.debug("started creating the producer environment");

        AdminClient adminClient = createAdminClient();
        if (!ifTopicExists(adminClient)) {
            createTopic(adminClient);
        }
    }

    private static boolean ifTopicExists(AdminClient adminClient) throws ExecutionException,
            InterruptedException {
        logger.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(STRING_MESSAGES_TOPIC);
    }

    private static AdminClient createAdminClient() {
        logger.debug("started creating the admin client");

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("client.id", CLIENT_ID);
        return AdminClient.create(properties);
    }

    private static void createTopic(AdminClient adminClient) throws ExecutionException,
            InterruptedException, TimeoutException {
        logger.debug("started creating the new topic");

        NewTopic topic = new NewTopic(STRING_MESSAGES_TOPIC, NUMBER_OF_PARTITIONS,
                (short) FACTOR_OF_REPLICAS);
        final CreateTopicsResult createTopicsResult =
                adminClient.createTopics(Collections.singleton(topic));
        createTopicsResult.values().get(STRING_MESSAGES_TOPIC).get(TOPICS_TIMEOUT, TimeUnit.SECONDS);
    }

    private static void sendDataToKafka(ExerciseKafkaProducer producer) {
        String message = "message number " + count;
        producer.sendMessage(STRING_MESSAGES_TOPIC, count, message);
        count++;
    }

    public static void main(String[] args) {
        try {
            ExerciseKafkaProducer producer = createProducer();
            while (!Thread.currentThread().isInterrupted()) {
                TimeUnit.SECONDS.sleep(MESSAGES_TIMEOUT);
                sendDataToKafka(producer);
            }
            producer.close();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | TimeoutException e) {
            logger.error(e.getMessage(), e);
            System.exit(-1);
        }
    }
}
