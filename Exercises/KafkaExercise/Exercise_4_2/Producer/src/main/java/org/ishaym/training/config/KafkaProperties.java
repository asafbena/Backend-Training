package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private final String bootstrapServer;
    private final String clientId;
    private final String schemaRegistryUrl;

    @JsonCreator
    public KafkaProperties(@JsonProperty("bootstrap.server") final String bootstrapServer,
                           @JsonProperty("client.id") final String clientId,
                           @JsonProperty("schema.registry.url") final String schemaRegistryUrl) {
        this.bootstrapServer = bootstrapServer;
        this.clientId = clientId;
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getClientId() {
        return clientId;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    @Override
    public String toString() {
        return "KafkaProperties{" +
                "bootstrap.server='" + bootstrapServer + '\'' +
                ", client.id='" + clientId + '\'' +
                ", schema.registry.url='" + schemaRegistryUrl + '\'' +
                '}';
    }
}
