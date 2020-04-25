package utils;

import backend.training.ExtendedIdentification;
import backend.training.Identification;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestAvroConverter {
    private static final Identification IDENTIFICATION = Identification.newBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setBirthCountry("UK")
            .setAge(47)
            .setPetName("Rocky")
            .build();
    private static final ExtendedIdentification EXTENDED_IDENTIFICATION = ExtendedIdentification.newBuilder()
            .setFirstName(IDENTIFICATION.getFirstName())
            .setLastName(IDENTIFICATION.getLastName())
            .setBirthCountry(IDENTIFICATION.getBirthCountry())
            .setAge(IDENTIFICATION.getAge())
            .setPetName(IDENTIFICATION.getPetName())
            .setIsEmployed(true)
            .build();

    @Test
    public void testConvertToExtendedIdentification() {
        ExtendedIdentification result = AvroConverter.convertToExtendedIdentification(IDENTIFICATION);
        Assert.assertEquals(EXTENDED_IDENTIFICATION, result);
    }
}
