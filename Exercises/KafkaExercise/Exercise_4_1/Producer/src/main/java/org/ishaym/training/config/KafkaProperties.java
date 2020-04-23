package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private final String bootstrapServer;
    private final String clientId;

    @JsonCreator
    public KafkaProperties(@JsonProperty("bootstrap.server") final String bootstrapServer,
                           @JsonProperty("client.id") final String clientId) {
        this.bootstrapServer = bootstrapServer;
        this.clientId = clientId;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return "KafkaProperties{" +
                "bootstrap.server='" + bootstrapServer + '\'' +
                ", client.id='" + clientId + '\'' +
                '}';
    }
}
