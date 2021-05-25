package utils;

import backend.training.ExtendedIdentification;
import backend.training.Identification;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;

public class TestsConstants {
    public static final Identification IDENTIFICATION = Identification.newBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setBirthCountry("UK")
            .setAge(47)
            .setPetName("Rocky")
            .build();
    public static final ExtendedIdentification EXTENDED_IDENTIFICATION = ExtendedIdentification.newBuilder()
            .setFirstName(IDENTIFICATION.getFirstName())
            .setLastName(IDENTIFICATION.getLastName())
            .setBirthCountry(IDENTIFICATION.getBirthCountry())
            .setAge(IDENTIFICATION.getAge())
            .setPetName(IDENTIFICATION.getPetName())
            .setIsEmployed(true)
            .build();
    public static final Boolean IS_TESTING_MODE = Boolean.TRUE;
    public static final String DESERIALIZER_PATH = "deserializer-path";
    public static final String CONSUMER_GROUP_ID = "groupId";
    public static final String KAFKA_LOG_DIRECTORY_PROPERTY = "log.dirs=logs";
    public static final String TEST_TOPIC = "test-topic";
    public static final String ADDITIONAL_TEST_TOPIC = "additional-test-topic";
    public static final String TEST_MESSAGE_CONTENT = "test-message";
    public static final String EMBEDDED_CONSUMER_GROUP_ID_PROPERTY = "group.id=test3";
    public static final String EMBEDDED_CONSUMER_AUTO_OFFSET_RESET_PROPERTY = "auto.offset.reset=earliest";
    public static final Long EMBEDDED_CONSUMER_POLLING_TIMEOUT_MS = 4500L;
    public static final int PARTITION_NUMBER = 1;
    public static final TopicPartition TOPIC_PARTITION = new TopicPartition(TEST_TOPIC, PARTITION_NUMBER);
    public static final ConsumerRecord<String, String> CONSUMER_RECORD = new ConsumerRecord<String, String>(
            TestsConstants.TEST_TOPIC, PARTITION_NUMBER, 0, "test-key",
            TestsConstants.TEST_MESSAGE_CONTENT);
}
