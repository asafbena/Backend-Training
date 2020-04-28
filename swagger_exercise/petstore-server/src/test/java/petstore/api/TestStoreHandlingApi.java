package petstore.api;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petstore.model.Order;
import utils.DataExtractionUtils;
import utils.TestsConstants;

import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestStoreHandlingApi {
    private ArrayList<Order> orders = new ArrayList<Order>();
    private StoreHandlingApi storeHandlingApi;

    public TestStoreHandlingApi() {
        buildStoreDB();
        storeHandlingApi = new StoreHandlingApi(orders);
    }

    @Test
    public void testGetOrderById() {
        Order order = storeHandlingApi.getOrderById(TestsConstants.EXISTING_ORDER_ID).getBody();
        Assertions.assertEquals(Order.StatusEnum.DELIVERED, order.getStatus());
    }

    @Test
    public void testOrderWithInvalidAboveMaximumId() {
        ResponseEntity<Order> responseEntity = storeHandlingApi.getOrderById(
                TestsConstants.INVALID_OVER_MAXIMUM_VALUE_ORDER_ID);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testOrderWithInvalidBelowMaximumId() {
        ResponseEntity<Order> responseEntity = storeHandlingApi.getOrderById(
                TestsConstants.INVALID_BELOW_MINIMAL_VALUE_ORDER_ID);
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    public void testOrderWithIdOfNotFoundOrder() {
        ResponseEntity<Order> responseEntity = storeHandlingApi.getOrderById(TestsConstants.NOT_FOUND_ORDER_ID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetInventory() {
        Map<String, Integer> inventoryMap = storeHandlingApi.getInventory().getBody();
        assertInventoryQuantityByStatus(TestsConstants.NUMBER_OF_INVENTORY_APPROVED_ORDERS, inventoryMap,
                Order.StatusEnum.APPROVED);
        assertInventoryQuantityByStatus(TestsConstants.NUMBER_OF_INVENTORY_DELIVERED_ORDERS, inventoryMap,
                Order.StatusEnum.DELIVERED);
        assertInventoryQuantityByStatus(TestsConstants.NUMBER_OF_INVENTORY_PLACED_ORDERS, inventoryMap,
                Order.StatusEnum.PLACED);
    }

    private void assertInventoryQuantityByStatus(int expectedQuantity, Map<String, Integer> inventoryMap,
            Order.StatusEnum orderStatus) {
        Assertions.assertEquals(expectedQuantity, (int) inventoryMap.get(orderStatus.toString()));
    }

    private void buildStoreDB() {
        try {
            DataExtractionUtils.extractOrdersData(orders);
        } catch (IOException e) {
            System.out.println("IOE exception: " + e);
        } catch (ParseException e) {
            System.out.println("parse exception: " + e);
        }
    }
}
