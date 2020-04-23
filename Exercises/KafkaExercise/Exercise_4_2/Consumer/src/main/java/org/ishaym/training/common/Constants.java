package org.ishaym.training.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.config.Configurations;
import org.ishaym.training.config.ConsumerProperties;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.config.TopicProperties;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Constants {
    private static final Logger LOGGER = LogManager.getLogger(Constants.class);

    private static Constants constants = null;

    private static final String PROPERTIES_FILE = "configurations.yaml";

    private KafkaProperties kafkaProperties;
    private TopicProperties topicProperties;
    private ConsumerProperties consumerProperties;

    private Configurations getConfigurationsFromFile() throws IOException {
        LOGGER.debug("started reading properties from the yaml file");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource(PROPERTIES_FILE)).getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, Configurations.class);
    }

    private Constants() throws IOException {
        LOGGER.debug("creating the constants object instance");

        Configurations config = getConfigurationsFromFile();

        this.kafkaProperties = config.getKafkaProperties();
        this.topicProperties = config.getTopicProperties();
        this.consumerProperties = config.getConsumerProperties();
    }

    public static Constants genInstance() throws IOException {
        LOGGER.debug("getting the constants object instance");

        if (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    public KafkaProperties getKafkaProperties() {
        return kafkaProperties;
    }

    public TopicProperties getTopicProperties() {
        return topicProperties;
    }

    public ConsumerProperties getConsumerProperties() {
        return consumerProperties;
    }
}
