package org.ishaym.training.common;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.config.ConsumerProperties;
import org.ishaym.training.config.KafkaProperties;

import java.io.IOException;
import java.util.Properties;

public class ConstantBuilds {
    private static final Logger LOGGER = LogManager.getLogger(ConstantBuilds.class);

    private ConstantBuilds() {

    }

    private static Properties createAdminClientPropertiesObject() throws IOException {
        LOGGER.debug("started creating the admin properties object");

        KafkaProperties kafkaProperties = Constants.getKafkaProperties();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        properties.put("client.id", kafkaProperties.getClientId());

        return properties;
    }

    private static Properties createConsumerPropertiesObject() throws IOException {
        LOGGER.debug("started creating the consumer properties object");

        KafkaProperties kafkaProperties = Constants.getKafkaProperties();
        ConsumerProperties consumerProperties = Constants.getConsumerProperties();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        properties.put("group.id", consumerProperties.getGroupId());
        properties.put("key.deserializer", consumerProperties.getKeyDeserializer());
        properties.put("value.deserializer", consumerProperties.getValueDeserializer());

        return properties;
    }

    public static AdminClient buildAdminClientFromConstants() throws IOException {
        LOGGER.debug("building the admin client from the properties");

        return AdminClient.create(createAdminClientPropertiesObject());
    }

    public static Consumer<Integer, String> buildConsumerFromConstants() throws IOException {
        LOGGER.debug("building the Kafka Consumer from the properties");

        return new KafkaConsumer<>(createConsumerPropertiesObject());
    }

}
