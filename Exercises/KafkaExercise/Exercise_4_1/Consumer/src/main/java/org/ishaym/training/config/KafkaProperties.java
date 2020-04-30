package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {

    @JsonProperty("bootstrap.server")
    private String bootstrapServer;

    @JsonProperty("client.id")
    private String clientId;

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getClientId() {
        return clientId;
    }

    @Override
    public String toString() {
        return
                "KafkaProperties{" +
                        "bootstrap.server = '" + bootstrapServer + '\'' +
                        ",client.id = '" + clientId + '\'' +
                        "}";
    }
}