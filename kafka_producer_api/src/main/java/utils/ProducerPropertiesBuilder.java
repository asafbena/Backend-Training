package utils;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Properties;

public class ProducerPropertiesBuilder {
    private ProducerPropertiesBuilder() {
    }

    public static Properties buildProducerProperties(String valueSerializerPath) {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, Constants.STRINGS_SERIALIZER_PATH);
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerPath);
        return producerProperties;
    }

    public static Properties buildAvroProducerProperties(String valueSerializerPath) {
        Properties producerProperties = buildProducerProperties(valueSerializerPath);
        producerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, Constants.SCHEMA_REGISTRY_URL);
        return producerProperties;
    }
}
