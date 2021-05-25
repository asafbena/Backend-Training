package utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;

import java.util.Properties;

public class ConsumerPropertiesBuilder {
    private ConsumerPropertiesBuilder() {
    }

    public static Properties buildConsumerProperties(String valueDeserializerPath, String groupId) {
        Properties consumerProperties = new Properties();
        consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Constants.BROKER);
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, Constants.STRINGS_DESERIALIZER_PATH);
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerPath);
        consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, Constants.MAX_NUMBER_OF_POLLED_MESSAGES);
        consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return consumerProperties;
    }

    public static Properties buildAvroConsumerProperties(String valueDeserializerPath, String groupId) {
        Properties consumerProperties = buildConsumerProperties(valueDeserializerPath, groupId);
        consumerProperties.put(Constants.SPECIFIC_AVRO_READER_CONFIG,
                Constants.SPECIFIC_AFRO_DESERIALIZATION);
        consumerProperties.put(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME, Constants.SCHEMA_REGISTRY_URL);
        return consumerProperties;
    }
}
