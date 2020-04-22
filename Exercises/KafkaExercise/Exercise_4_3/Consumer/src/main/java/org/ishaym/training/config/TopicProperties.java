package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicProperties {
    private final String name;
    private final int numPartitions;
    private final int replicationFactor;
    private final int creationTimeoutSeconds;

    @JsonCreator
    public TopicProperties(@JsonProperty("name") final String name,
                           @JsonProperty("numPartitions") final int numPartitions,
                           @JsonProperty("replicationFactor") final int replicationFactor,
                           @JsonProperty("creationTimeoutSeconds") final int
                                   creationTimeoutSeconds) {
        this.name = name;
        this.numPartitions = numPartitions;
        this.replicationFactor = replicationFactor;
        this.creationTimeoutSeconds = creationTimeoutSeconds;
    }

    public String getName() {
        return name;
    }

    public int getNumPartitions() {
        return numPartitions;
    }

    public int getReplicationFactor() {
        return replicationFactor;
    }

    public int getCreationTimeoutSeconds() {
        return creationTimeoutSeconds;
    }

    @Override
    public String toString() {
        return "TopicProperties{" +
                "name='" + name + '\'' +
                ", numPartitions=" + numPartitions +
                ", replicationFactor=" + replicationFactor +
                ", creationTimeoutSeconds=" + creationTimeoutSeconds +
                '}';
    }
}
