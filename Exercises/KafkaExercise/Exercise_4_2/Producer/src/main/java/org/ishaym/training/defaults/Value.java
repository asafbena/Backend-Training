package org.ishaym.training.defaults;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {

    @JsonProperty("petName")
    private String petName;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("countryOfBirth")
    private String countryOfBirth;

    @JsonProperty("age")
    private int age;

    public String getPetName() {
        return petName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return
                "Value{" +
                        "petName = '" + petName + '\'' +
                        ",firstName = '" + firstName + '\'' +
                        ",lastName = '" + lastName + '\'' +
                        ",countryOfBirth = '" + countryOfBirth + '\'' +
                        ",age = '" + age + '\'' +
                        "}";
    }
}