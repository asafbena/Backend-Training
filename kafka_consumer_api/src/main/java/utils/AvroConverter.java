package utils;

import backend.training.ExtendedIdentification;
import backend.training.Identification;

public class AvroConverter {
    private AvroConverter() {
    }

    public static ExtendedIdentification convertToExtendedIdentification(Identification identification) {
        return ExtendedIdentification.newBuilder()
                .setFirstName(identification.getFirstName())
                .setLastName(identification.getLastName())
                .setBirthCountry(identification.getBirthCountry())
                .setAge(identification.getAge())
                .setPetName(identification.getPetName())
                .setIsEmployed(true)
                .build();
    }
}
