package org.ishaym.training.defaults;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultMessage {

    @JsonProperty("value")
    private Value value;

    @JsonProperty("key")
    private int key;

    public Value getValue() {
        return value;
    }

    public int getKey() {
        return key;
    }

    @Override
    public String toString() {
        return
                "DefaultMessage{" +
                        "value = '" + value + '\'' +
                        ",key = '" + key + '\'' +
                        "}";
    }
}