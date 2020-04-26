package utils;

import backend.training.ExtendedIdentification;
import backend.training.Identification;

public class TestsConstants {
    public static final Identification IDENTIFICATION = Identification.newBuilder()
            .setFirstName("John")
            .setLastName("Smith")
            .setBirthCountry("UK")
            .setAge(47)
            .setPetName("Rocky")
            .build();
    public static final ExtendedIdentification EXTENDED_IDENTIFICATION = ExtendedIdentification.newBuilder()
            .setFirstName(IDENTIFICATION.getFirstName())
            .setLastName(IDENTIFICATION.getLastName())
            .setBirthCountry(IDENTIFICATION.getBirthCountry())
            .setAge(IDENTIFICATION.getAge())
            .setPetName(IDENTIFICATION.getPetName())
            .setIsEmployed(true)
            .build();
}
