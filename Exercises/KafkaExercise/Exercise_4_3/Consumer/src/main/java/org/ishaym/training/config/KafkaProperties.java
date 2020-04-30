package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {

    @JsonProperty("schema.registry.url")
    private String schemaRegistryUrl;

    @JsonProperty("bootstrap.server")
    private String bootstrapServer;

    @JsonProperty("client.id")
    private String clientId;

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

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
                        "schema.registry.url = '" + schemaRegistryUrl + '\'' +
                        ",bootstrap.server = '" + bootstrapServer + '\'' +
                        ",client.id = '" + clientId + '\'' +
                        "}";
    }
}