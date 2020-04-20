package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final String CLIENT_ID = "consumer-client-id";
    private static final String STRING_MESSAGES_TOPIC = "string-messages";
    private static final int NUMBER_OF_PARTITIONS = 1;
    private static final int FACTOR_OF_REPLICAS = 1;
    private static final int TIMEOUT = 10;

    private static ExerciseKafkaConsumer createConsumer() throws InterruptedException,
            ExecutionException, TimeoutException {
        setUpProducerEnvironment();
        return new ExerciseKafkaConsumer();
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
        createTopicsResult.values().get(STRING_MESSAGES_TOPIC).get(TIMEOUT, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        try {
            ExerciseKafkaConsumer consumer = createConsumer();
            consumer.subscribe(STRING_MESSAGES_TOPIC);
            consumer.consume();
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
