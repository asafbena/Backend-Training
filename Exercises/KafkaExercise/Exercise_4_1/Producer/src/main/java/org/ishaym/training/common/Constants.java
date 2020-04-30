package org.ishaym.training.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.config.*;
import org.ishaym.training.defaults.DefaultMessage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Constants {
    private static final Logger LOGGER = LogManager.getLogger(Constants.class);

    private static Constants constants = null;

    private static final String PROPERTIES_FILE = "configurations.yaml";
    private static final String DEFAULT_MESSAGE_FILE = "default_message.yaml";

    private Configurations configurations;
    private DefaultMessage defaultMessage;


    private <T> T getDataFromYAMLFile(String fileName, Class<T> valueType) throws IOException {
        LOGGER.debug("started reading properties from the yaml file");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource(fileName)).getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, valueType);
    }

    private Constants() throws IOException {
        LOGGER.debug("creating the constants object instance");

        this.configurations = getDataFromYAMLFile(PROPERTIES_FILE, Configurations.class);
        this.defaultMessage = getDataFromYAMLFile(DEFAULT_MESSAGE_FILE, DefaultMessage.class);
    }

    private static Constants getInstance() throws IOException {
        LOGGER.debug("getting the constants object instance");

        if (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    private static Configurations getConfigurations() throws IOException {
        return Constants.getInstance().configurations;
    }

    public static KafkaProperties getKafkaProperties() throws IOException {
        return getConfigurations().getKafkaProperties();
    }

    public static ProducerProperties getProducerProperties() throws IOException {
        return getConfigurations().getProducerProperties();
    }

    public static TopicProperties getTopicProperties() throws IOException {
        return getConfigurations().getTopicProperties();
    }

    public static TopicCheckingProperties getTopicCheckingProperties() throws IOException {
        return getTopicProperties().getTopicCheckingProperties();
    }

    public static DefaultMessage getDefaultMessage() throws IOException {
        return Constants.getInstance().defaultMessage;
    }
}
