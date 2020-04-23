package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConsumerProperties {
    private final String groupId;
    private final int pollingTimeoutInMilliSeconds;

    @JsonCreator
    public ConsumerProperties(@JsonProperty("groupId") final String groupId,
                              @JsonProperty("pollingTimeoutInMilliSeconds") final int
                                      pollingTimeoutInMilliSeconds) {
        this.groupId = groupId;
        this.pollingTimeoutInMilliSeconds = pollingTimeoutInMilliSeconds;
    }

    public String getGroupId() {
        return groupId;
    }

    public int getPollingTimeoutInMilliSeconds() {
        return pollingTimeoutInMilliSeconds;
    }

    @Override
    public String toString() {
        return "ConsumerProperties{" +
                "groupId='" + groupId + '\'' +
                ", pollingTimeoutInMilliSeconds=" + pollingTimeoutInMilliSeconds +
                '}';
    }
}
