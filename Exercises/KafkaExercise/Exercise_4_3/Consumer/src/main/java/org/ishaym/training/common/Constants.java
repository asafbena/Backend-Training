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

    private Configurations configurations;

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

        this.configurations = getConfigurationsFromFile();
    }

    private static Constants getInstance() throws IOException {
        LOGGER.debug("getting the constants object instance");

        if (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    private static Configurations getConfigurations() throws IOException {
        return getInstance().configurations;
    }

    public static KafkaProperties getKafkaProperties() throws IOException {
        return getConfigurations().getKafkaProperties();
    }

    public static ConsumerProperties getConsumerProperties() throws IOException {
        return getConfigurations().getConsumerProperties();
    }

    public static TopicProperties getTopicProperties() throws IOException {
        return getConfigurations().getTopicProperties();
    }
}
