package org.ishaym.training.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.constants.KafkaProperties;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Common {
    private static final Logger LOGGER = LogManager.getLogger(Common.class);
    private static final String PROPERTIES_FILE = "kafka_properties.yaml";

    public static final String AVRO_MESSAGES_TOPIC = "avro-messages-section-5-2";

    private Common() {

    }

    public static KafkaProperties getKafkaPropertiesFromFile() throws IOException {
        LOGGER.debug("started reading properties from the yaml file");

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource(PROPERTIES_FILE)).getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, KafkaProperties.class);
    }

}
