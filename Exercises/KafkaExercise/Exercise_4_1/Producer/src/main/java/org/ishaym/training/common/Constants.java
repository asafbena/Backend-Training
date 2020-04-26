package org.ishaym.training.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.defaults.DefaultMessage;
import org.ishaym.training.config.Configurations;

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

    public static Constants genInstance() throws IOException {
        LOGGER.debug("getting the constants object instance");

        if (constants == null) {
            constants = new Constants();
        }
        return constants;
    }

    public Configurations getConfigurations() {
        return configurations;
    }

    public DefaultMessage getDefaultMessage() {
        return defaultMessage;
    }
}
