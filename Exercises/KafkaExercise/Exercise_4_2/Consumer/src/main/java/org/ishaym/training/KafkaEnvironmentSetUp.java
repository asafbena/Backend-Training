package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.config.TopicProperties;

import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private static AdminClient adminClient;

    static {
        try {
            adminClient = AdminClient.create(createAdminProperties());
        } catch (IOException e) {
            LOGGER.fatal(e);
            System.exit(-1);
        }
    }

    private KafkaEnvironmentSetUp() {

    }

    private static Properties createAdminProperties() throws IOException {
        LOGGER.debug("started creating the admin properties object");

        KafkaProperties kafkaProperties = Constants.genInstance().getConfigurations().
                getKafkaProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("client.id", kafkaProperties.getClientId());

        return props;
    }

    private static boolean isTopicExists()
            throws ExecutionException, InterruptedException, IOException {
        LOGGER.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(
                Constants.genInstance().getConfigurations().getTopicProperties().getName());
    }

    private static void createTopic()
            throws ExecutionException, InterruptedException, TimeoutException, IOException {
        LOGGER.debug("started creating the new topic");

        TopicProperties topicProperties = Constants.genInstance().getConfigurations().
                getTopicProperties();

        NewTopic newTopic = new NewTopic(topicProperties.getName(),
                topicProperties.getNumPartitions(), (short) topicProperties.getReplicationFactor());
        final CreateTopicsResult createTopicsResult =
                adminClient.createTopics(Collections.singleton(newTopic));
        createTopicsResult.values().get(topicProperties.getName()).get(
                topicProperties.getCreationTimeoutSeconds(), TimeUnit.SECONDS);
    }

    public static void setUp() throws IOException, ExecutionException, InterruptedException,
            TimeoutException {
        LOGGER.debug("started creating the consumer environment");

        if (!isTopicExists()) {
            createTopic();
        }
    }

}
