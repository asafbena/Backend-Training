package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicCheckingProperties {

    @JsonProperty("timeBetweenAttemptsInMillis")
    private int timeBetweenAttemptsInMillis;

    @JsonProperty("numOfAttempts")
    private int numOfAttempts;

    public int getTimeBetweenAttemptsInMillis() {
        return timeBetweenAttemptsInMillis;
    }

    public int getNumOfAttempts() {
        return numOfAttempts;
    }

    @Override
    public String toString() {
        return
                "TopicCheckingProperties{" +
                        "timeBetweenAttemptsInMillis = '" + timeBetweenAttemptsInMillis + '\'' +
                        ",numOfAttempts = '" + numOfAttempts + '\'' +
                        "}";
    }
}