package org.ishaym.training.common;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ishaym.training.config.KafkaProperties;
import org.ishaym.training.config.ProducerProperties;

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

    private static Properties createProducerPropertiesObject() throws IOException {
        LOGGER.debug("started creating the producer properties object");

        KafkaProperties kafkaProperties = Constants.getKafkaProperties();
        ProducerProperties producerProperties = Constants.getProducerProperties();

        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaProperties.getBootstrapServer());
        properties.put("key.serializer", producerProperties.getKeySerializer());
        properties.put("value.serializer", producerProperties.getValueSerializer());

        return properties;
    }

    public static AdminClient buildAdminClientFromConstants() throws IOException {
        LOGGER.debug("building the admin client from the properties");

        return AdminClient.create(createAdminClientPropertiesObject());
    }

    public static Producer<Integer, String> buildProducerFromConstants() throws IOException {
        LOGGER.debug("building the Kafka Producer from the properties");

        return new KafkaProducer<>(createProducerPropertiesObject());
    }
}
