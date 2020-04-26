package org.ishaym.training;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.ishaym.training.common.Constants;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public class Main {
    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            KafkaEnvironmentSetUp.getInstance().setUp();
            MessagesConsumer consumer = new MessagesConsumer();
            consumer.subscribe(
                    Constants.genInstance().getConfigurations().getTopicProperties().getName());
            consumer.consume().start();
        } catch (InterruptedException e) {
            LOGGER.fatal(e);
            Thread.currentThread().interrupt();
            System.exit(-1);
        } catch (ExecutionException | TimeoutException | IOException e) {
            LOGGER.fatal(e);
            System.exit(-1);
        } catch (RuntimeException e) {
            LOGGER.fatal("other runtime error");
            LOGGER.fatal(e);
            System.exit(-1);
        }
    }

}
