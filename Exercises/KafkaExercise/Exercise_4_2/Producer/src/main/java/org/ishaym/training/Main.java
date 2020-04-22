package org.ishaym.training;

import org.ishaym.training.common.Common;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            KafkaEnvironmentSetUp.setUp();
            ExerciseKafkaProducer producer = new ExerciseKafkaProducer();
            producer.sendMessage(Common.AVRO_MESSAGES_TOPIC, 0,
                    Person.newBuilder().build());
            producer.close();
        } catch (InterruptedException e) {
            LOGGER.fatal(e.getMessage(), e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | TimeoutException | IOException e) {
            LOGGER.fatal(e.getMessage(), e);
            System.exit(-1);
        } catch (RuntimeException e) {
            LOGGER.fatal("some other exception");
            LOGGER.fatal(e);
            e.printStackTrace();
        }
    }
}
