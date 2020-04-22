package org.ishaym.training.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private final String bootstrapServer;

    @JsonCreator
    public KafkaProperties(@JsonProperty("bootstrap.server") final String bootstrapServer) {
        this.bootstrapServer = bootstrapServer;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    @Override
    public String toString() {
        return "KafkaProperties{" +
                "bootstrap.server='" + bootstrapServer + '\'' +
                '}';
    }
}
