package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import petstore.model.Category;
import petstore.model.Order;
import petstore.model.Pet;
import petstore.model.Tag;

import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.List;

public class DataExtractionUtils {
    private final static JSONParser JSON_PARSER = new JSONParser();

    private DataExtractionUtils() {
    }

    public static void extractPetsData(List<Pet> pets) throws IOException, ParseException {
        JSONObject jsonObject = (JSONObject) JSON_PARSER.parse(
                new FileReader(TestsConstants.PETS_INTEGRATION_TESTS_DB_PATH));
        List<JSONObject> petsData = (List<JSONObject>)jsonObject.get(TestsConstants.PETS_DB_NAME);
        for (JSONObject petData: petsData) {
            pets.add(extractPetData(petData));
        }
    }

    public static Pet extractPetData(String petData) {
        try {
            JSONObject jsonObject = (JSONObject) JSON_PARSER.parse(petData);
            return extractPetData(jsonObject);
        } catch (ParseException e) {
            System.out.println("parse exception: " + e);
        }
        return null;
    }

    public static Pet extractPetData(JSONObject petData) {
        Pet pet = new Pet();
        pet.setId((Long) petData.get("id"));
        pet.setStatus(Pet.StatusEnum.valueOf(petData.get("status").toString().toUpperCase()));
        pet.setName(petData.get("name").toString());
        pet.setTags((List<Tag>) petData.get("tags"));
        pet.setCategory(extractPetCategoryData((JSONObject) petData.get("category")));
        return pet;
    }

    public static void extractOrdersData(List<Order> orders) throws IOException, ParseException {
        Object obj = JSON_PARSER.parse(new FileReader(TestsConstants.STORE_INTEGRATION_TESTS_DB_PATH));
        JSONObject jsonObject = (JSONObject) obj;
        List<JSONObject> ordersData = (List<JSONObject>)jsonObject.get(TestsConstants.STORE_DB_NAME);
        for (JSONObject orderData: ordersData) {
            orders.add(extractOrderData(orderData));
        }
    }

    private static Category extractPetCategoryData(JSONObject petCategoryData) {
        Category petCategory = new Category();
        petCategory.setId((Long) petCategoryData.get("id"));
        petCategory.setName(petCategoryData.get("name").toString());
        return petCategory;
    }

    private static Order extractOrderData(JSONObject orderData) {
        Order order = new Order();
        order.setId((Long) orderData.get("id"));
        order.setOrderId((Long) orderData.get("orderId"));
        order.setQuantity(((Number) orderData.get("quantity")).intValue());
        order.setStatus(Order.StatusEnum.valueOf(orderData.get("status").toString().toUpperCase()));
        order.setShipDate(OffsetDateTime.parse(orderData.get("shipDate").toString()));
        order.setComplete((Boolean) orderData.get("complete"));
        return order;
    }
}
