package org.ishaym.training.tests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TestItem {

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