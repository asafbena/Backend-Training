package utils;

import petstore.model.Pet;

import java.util.ArrayList;

public class TestsConstants {
    public static final String PETS_INTEGRATION_TESTS_DB_PATH = "./src/test/resources/pet_integration_tests.json";
    public static final String STORE_INTEGRATION_TESTS_DB_PATH = "./src/test/resources/store_integration_tests.json";
    public static final Long PET_INVALID_ID = -5L;
    public static final Long NOT_FOUND_PET_ID = 12L;
    public static final Long EXISTING_PET_ID = 1L;
    public static final String STORE_DB_NAME = "orders";
    public static final String PETS_DB_NAME = "pets";
    public static final int NUMBER_OF_INVENTORY_APPROVED_ORDERS = 6;
    public static final int NUMBER_OF_INVENTORY_DELIVERED_ORDERS = 1;
    public static final int NUMBER_OF_INVENTORY_PLACED_ORDERS = 1;
    public static final Long EXISTING_ORDER_ID = 3L;
    public static final Long NOT_FOUND_ORDER_ID = 8L;
    public static final Long INVALID_OVER_MAXIMUM_VALUE_ORDER_ID = 12L;
    public static final Long INVALID_BELOW_MINIMAL_VALUE_ORDER_ID = 0L;
    public static final ArrayList<String> EXISTING_PET_STATUSES = new ArrayList<String>() {{
        add(Pet.StatusEnum.SOLD.toString());
        add(Pet.StatusEnum.PENDING.toString());
    }};
    public static final ArrayList<String> INVALID_PET_STATUSES = new ArrayList<String>() {{ add("bought"); }};
    public static final String PET_DATA_BY_ID = "{\"id\": " + EXISTING_PET_ID.toString() + ", \"status\": \"pending\""
            + ", \"name\": \"tom\", \"category\": {\"name\": \"cat\", \"id\": 2}, \"tags\": [{\"name\": \"sometag3\", "
            + "\"id\" : 3}, {\"name\": \"sometag4\", \"id\" : 4}]}";
    public static final String PET_DATA_BY_STATUS = "{\"id\": 2, \"status\": \"sold\", \"name\": \"twitter\", "
        + "\"category\": {\"name\": \"bird\", \"id\": 3}, \"tags\": [{\"name\": \"sometag5\", \"id\" : 5}, "
        + "{\"name\": \"sometag6\", \"id\" : 6}]}";
    public static final ArrayList<Pet> EXISTING_PETS_BY_STATUSES = new ArrayList<Pet>() {{
        add(DataExtractionUtils.extractPetData(PET_DATA_BY_ID));
        add(DataExtractionUtils.extractPetData(PET_DATA_BY_STATUS));
    }};
}
