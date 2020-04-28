package utils;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

public class TestProducerPropertiesBuilder {
    @Test
    public void testBuildProducerProperties() {
        Properties properties = ProducerPropertiesBuilder.buildProducerProperties(TestsConstants.SERIALIZER_PATH);
        assertCommonProducerProperties(properties, TestsConstants.SERIALIZER_PATH);
    }

    @Test
    public void testBuildAvroProducerProperties() {
        Properties properties = ProducerPropertiesBuilder.buildAvroProducerProperties(TestsConstants.SERIALIZER_PATH);
        assertCommonProducerProperties(properties, TestsConstants.SERIALIZER_PATH);
        Assertions.assertEquals(Constants.SCHEMA_REGISTRY_URL,
                properties.get(Constants.SCHEMA_REGISTRY_URL_COMPONENT_NAME));
    }

    private void assertCommonProducerProperties(Properties properties, String expectedValueSerializerPath) {
        Assertions.assertEquals(Constants.BROKER, properties.get(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG));
        Assertions.assertEquals(Constants.STRINGS_SERIALIZER_PATH,
                properties.get(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG));
        Assertions.assertEquals(expectedValueSerializerPath,
                properties.get(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG));
    }
}
