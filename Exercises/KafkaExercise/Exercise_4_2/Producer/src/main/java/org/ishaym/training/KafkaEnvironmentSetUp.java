package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.common.Common;
import org.ishaym.training.constants.KafkaProperties;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private static final String CLIENT_ID = "producer-client-id";
    private static final int NUMBER_OF_PARTITIONS = 1;
    private static final int FACTOR_OF_REPLICAS = 1;
    private static final int TIMEOUT = 10;

    private KafkaEnvironmentSetUp() {

    }

    private static Properties createAdminProperties() throws IOException {
        LOGGER.debug("started creating the admin properties object");

        KafkaProperties propertiesFromFile = Common.getKafkaPropertiesFromFile();

        Properties props = new Properties();
        props.put("bootstrap.servers", propertiesFromFile.getBootstrapServer());
        props.put("client.id", CLIENT_ID);

        return props;
    }

    private static AdminClient createAdminClient() throws IOException {
        LOGGER.debug("started creating the admin client");

        return AdminClient.create(createAdminProperties());
    }

    private static boolean isTopicExists(AdminClient adminClient, String topic)
            throws ExecutionException, InterruptedException {
        LOGGER.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(topic);
    }

    private static void createTopic(AdminClient adminClient, String topicName)
            throws ExecutionException, InterruptedException, TimeoutException {
        LOGGER.debug("started creating the new topic");

        NewTopic newTopic = new NewTopic(topicName, NUMBER_OF_PARTITIONS,
                (short) FACTOR_OF_REPLICAS);
        final CreateTopicsResult createTopicsResult =
                adminClient.createTopics(Collections.singleton(newTopic));
        createTopicsResult.values().get(topicName).get(TIMEOUT, TimeUnit.SECONDS);
    }

    public static void setUp() throws IOException, ExecutionException, InterruptedException,
            TimeoutException {
        LOGGER.debug("started creating the producer environment");

        String topic = Common.AVRO_MESSAGES_TOPIC;

        AdminClient adminClient = createAdminClient();
        if (!isTopicExists(adminClient, topic)) {
            createTopic(adminClient, topic);
        }
    }

}
