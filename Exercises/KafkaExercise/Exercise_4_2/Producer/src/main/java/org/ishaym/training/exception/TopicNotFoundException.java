package org.ishaym.training.exception;

public class TopicNotFoundException extends Exception {

    public TopicNotFoundException() {
        super("Topic had not been created yet");
    }
}
