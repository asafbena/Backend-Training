package org.ishaym.training;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.common.Constants;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.config.TopicCheckingProperties;
import org.ishaym.training.exception.TopicNotFoundException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


public class KafkaEnvironmentSetUp {
    private static final Logger LOGGER = LogManager.getLogger(KafkaEnvironmentSetUp.class);

    private static KafkaEnvironmentSetUp kafkaEnvironmentSetUp = null;

    private AdminClient adminClient;

    private KafkaEnvironmentSetUp() throws IOException {
        this.adminClient = AdminClient.create(createAdminProperties());
    }

    public static KafkaEnvironmentSetUp getInstance() throws IOException {
        if (kafkaEnvironmentSetUp == null) {
            kafkaEnvironmentSetUp = new KafkaEnvironmentSetUp();
        }
        return kafkaEnvironmentSetUp;
    }

    private Properties createAdminProperties() throws IOException {
        LOGGER.debug("started creating the admin properties object");

        KafkaProperties kafkaProperties = Constants.genInstance().getConfigurations().
                getKafkaProperties();

        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        props.put("client.id", kafkaProperties.getClientId());

        return props;
    }

    private boolean isTopicExists()
            throws ExecutionException, InterruptedException, IOException {
        LOGGER.debug("checking if topic already exists");

        return adminClient.listTopics().names().get().contains(
                Constants.genInstance().getConfigurations().getTopicProperties().getName());
    }

    private void waitForTopic() throws IOException, ExecutionException,
            InterruptedException, TopicNotFoundException {
        LOGGER.debug("starting to wait for topic creation");

        TopicCheckingProperties topicCheckingProperties = Constants.genInstance().
                getConfigurations().getTopicProperties().getTopicCheckingProperties();

        for (int i = 0; i < topicCheckingProperties.getNumOfAttempts(); i++) {
            if (isTopicExists()) {
                return;
            }
            TimeUnit.MILLISECONDS.sleep(
                    topicCheckingProperties.getTimeBetweenAttemptsInMillis());
        }
        throw new TopicNotFoundException();
    }

    public void setUp() throws IOException, ExecutionException, InterruptedException,
            TopicNotFoundException {
        LOGGER.debug("started creating the producer environment");

        waitForTopic();
    }

}
