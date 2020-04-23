package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicProperties {
    private final String name;

    @JsonCreator
    public TopicProperties(@JsonProperty("name") final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "TopicProperties{" +
                "name='" + name + '\'' +
                '}';
    }
}
