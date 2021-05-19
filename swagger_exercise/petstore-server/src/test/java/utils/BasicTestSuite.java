package utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicTestSuite {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicTestSuite.class);

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        String fullTestName = testInfo.getDisplayName();
        String shortenedTestName = fullTestName.substring(TestsConstants.TEST_NAME_STARTING_INDEX,
                fullTestName.length() - TestsConstants.TEST_NAME_NUMBER_OF_ADDITIONAL_CHARACTERS);
        if (testInfo.getTestMethod().isPresent()) {
            LOGGER.info("Starting the following test: {}.", shortenedTestName);
        }
    }
}
