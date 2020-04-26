package org.ishaym.training.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TopicProperties {

    @JsonProperty("name")
    private String name;

    @JsonProperty("topicCheckingProperties")
    private TopicCheckingProperties topicCheckingProperties;

    public String getName() {
        return name;
    }

    public TopicCheckingProperties getTopicCheckingProperties() {
        return topicCheckingProperties;
    }

    @Override
    public String toString() {
        return
                "TopicProperties{" +
                        "name = '" + name + '\'' +
                        ",topicCheckingProperties = '" + topicCheckingProperties + '\'' +
                        "}";
    }
}