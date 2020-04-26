package utils;

import backend.training.ExtendedIdentification;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestAvroMessagesExpander {
    @Test
    public void testConvertToExtendedIdentification() {
        ExtendedIdentification result = AvroMessagesExpander.expandToExtendedIdentification(TestsConstants.IDENTIFICATION);
        Assert.assertEquals(TestsConstants.EXTENDED_IDENTIFICATION, result);
    }
}
