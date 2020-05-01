package org.ishaym.training.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.ishaym.training.tests.TestItem;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class TestConstants {

    private static TestConstants constants = null;

    private static final String TESTS_FILE = "tests.yaml";

    private TestItem test;

    private TestItem getDataFromYAMLFile() throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        File file = new File(Objects.requireNonNull(
                classLoader.getResource(TESTS_FILE)).getFile());
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        return om.readValue(file, TestItem.class);
    }

    private TestConstants() throws IOException {
        this.test = getDataFromYAMLFile();
    }

    private static TestConstants getInstance() throws IOException {
        if (constants == null) {
            constants = new TestConstants();
        }
        return constants;
    }

    public static TestItem getTestsItem() throws IOException {
        return getInstance().test;
    }
}
