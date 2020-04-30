package org.ishaym.training.defaults;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultMessage {

    @JsonProperty("value")
    private String value;

    @JsonProperty("key")
    private int key;

    public String getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }
}