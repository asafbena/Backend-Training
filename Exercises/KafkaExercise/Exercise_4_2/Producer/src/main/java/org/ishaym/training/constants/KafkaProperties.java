package org.ishaym.training.constants;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class KafkaProperties {
    private final String bootstrapServer;
    private final String schemaRegistryUrl;

    @JsonCreator
    public KafkaProperties(@JsonProperty("bootstrap.server") final String bootstrapServer,
                           @JsonProperty("schema.registry.url") final String schemaRegistryUrl) {
        this.bootstrapServer = bootstrapServer;
        this.schemaRegistryUrl = schemaRegistryUrl;
    }

    public String getBootstrapServer() {
        return bootstrapServer;
    }

    public String getSchemaRegistryUrl() {
        return schemaRegistryUrl;
    }

    @Override
    public String toString() {
        return "KafkaProperties{" +
                "bootstrap.server='" + bootstrapServer + '\'' +
                ", schema.registry.url='" + schemaRegistryUrl + '\'' +
                '}';
    }

}
