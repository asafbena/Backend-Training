package org.ishaym.training;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;
import org.ishaym.training.defaults.Value;
import org.ishaym.training.exception.TopicNotFoundException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            KafkaEnvironmentSetUp.getInstance().setUp();
            MessagesProducer producer = new MessagesProducer();
            Value messageValue = Constants.getDefaultMessageValue();
            producer.sendMessage(
                    Constants.getTopicProperties().getName(),
                    Constants.getDefaultMessageKey(),
                    new Person(messageValue.getFirstName(), messageValue.getLastName(),
                            messageValue.getCountryOfBirth(), messageValue.getAge(),
                            messageValue.getPetName()));
            producer.close();
        } catch (InterruptedException e) {
            LOGGER.fatal(e.getMessage(), e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | IOException | TopicNotFoundException e) {
            LOGGER.fatal(e.getMessage(), e);
            System.exit(-1);
        } catch (RuntimeException e) {
            LOGGER.fatal("some other exception");
            LOGGER.fatal(e);
        }
    }
}
