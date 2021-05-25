package utils;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

class TestConsumerPropertiesBuilder {
    @Test
    void testBuildConsumerProperties() {
        Properties properties = ConsumerPropertiesBuilder.buildConsumerProperties(TestsConstants.DESERIALIZER_PATH,
                TestsConstants.CONSUMER_GROUP_ID);
        assertCommonConsumerProperties(properties, TestsConstants.DESERIALIZER_PATH, TestsConstants.CONSUMER_GROUP_ID);
    }

    @Test
    void testBuildAvroConsumerProperties() {
        Properties properties = ConsumerPropertiesBuilder.buildAvroConsumerProperties(TestsConstants.DESERIALIZER_PATH,
                TestsConstants.CONSUMER_GROUP_ID);
        assertCommonConsumerProperties(properties, TestsConstants.DESERIALIZER_PATH, TestsConstants.CONSUMER_GROUP_ID);
        Assertions.assertEquals(Constants.SCHEMA_REGISTRY_URL,
                properties.get(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME));
        Assertions.assertEquals(Constants.SPECIFIC_AFRO_DESERIALIZATION,
                properties.get(Constants.SPECIFIC_AVRO_READER_CONFIG));
    }

    private void assertCommonConsumerProperties(Properties properties, String expectedValueDeserializerPath,
            String expectedConsumerGroupId) {
        Assertions.assertEquals(Constants.BROKER, properties.get(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG));
        Assertions.assertEquals(Constants.STRINGS_DESERIALIZER_PATH,
                properties.get(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG));
        Assertions.assertEquals(expectedValueDeserializerPath,
                properties.get(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG));
        Assertions.assertEquals(Constants.MAX_NUMBER_OF_POLLED_MESSAGES,
                properties.get(ConsumerConfig.MAX_POLL_RECORDS_CONFIG));
        Assertions.assertEquals(expectedConsumerGroupId, properties.get(ConsumerConfig.GROUP_ID_CONFIG));
    }
}
