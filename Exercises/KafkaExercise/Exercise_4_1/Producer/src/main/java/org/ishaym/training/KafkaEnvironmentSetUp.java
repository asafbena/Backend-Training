package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.exception.TopicNotFoundException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private KafkaEnvironmentSetUp() {

    }

    private static Properties createAdminProperties() throws IOException {
        LOGGER.debug("started creating the admin properties object");

        KafkaProperties kafkaProperties = Constants.genInstance().getKafkaProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("client.id", kafkaProperties.getClientId());

        return props;
    }

    private static boolean isTopicExists()
            throws ExecutionException, InterruptedException, IOException {
        LOGGER.debug("checking if topic already exists");

        try (AdminClient adminClient = AdminClient.create(createAdminProperties())) {
            return adminClient.listTopics().names().get().contains(
                    Constants.genInstance().getTopicProperties().getName());
        }
    }

    public static void setUp() throws IOException, ExecutionException, InterruptedException,
            TopicNotFoundException {
        LOGGER.debug("started creating the producer environment");

        if (!isTopicExists()) {
            throw new TopicNotFoundException();
        }
    }
}
