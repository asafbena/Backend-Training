package utils;

import backend.training.ExtendedIdentification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestAvroMessagesExpander {
    @Test
    void testConvertToExtendedIdentification() {
        ExtendedIdentification result = AvroMessagesExpander.expandToExtendedIdentification(TestsConstants.IDENTIFICATION);
        Assertions.assertEquals(TestsConstants.EXTENDED_IDENTIFICATION, result);
    }
}
