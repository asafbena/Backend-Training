package petstore.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import petstore.model.Order;
import utils.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreHandlingApi implements StoreApi {
    private static final Logger LOGGER = LoggerFactory.getLogger(StoreHandlingApi.class);

    private List<Order> orders;

    public StoreHandlingApi(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    @ApiOperation(value = "Returns pet inventories by status", nickname = "getInventory", notes = "Returns a map of status codes to quantities", response = Integer.class, responseContainer = "Map", tags = {"store",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Map.class, responseContainer = "Map")})
    @GetMapping(value = "/store/inventory",
            produces = {"application/json"})
    public ResponseEntity<Map<String, Integer>> getInventory() {
        LOGGER.info("Gathering inventory data according to existing orders.");
        Map<String, Integer> inventoryMap = new HashMap<>();
        updateInventoryByOrders(inventoryMap);
        LOGGER.debug("The gathered inventory data is: {}", inventoryMap);
        return new ResponseEntity<>(inventoryMap, HttpStatus.OK);
    }

    @Override
    @ApiOperation(value = "Find purchase order by ID", nickname = "getOrderById", notes = "For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions", response = Order.class, tags = {"store",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Order.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Order not found")})
    @GetMapping(value = "/store/order/{orderId}",
            produces = {"application/json"})
    public ResponseEntity<Order> getOrderById(@Min(Constants.VALID_ORDER_ID_MINIMAL_VALUE) @Max(Constants.VALID_ORDER_ID_MAXIMUM_VALUE) @ApiParam(value = "ID of pet that needs to be fetched", required = true) @PathVariable("orderId") Long orderId) {
        LOGGER.info("Getting an order according to given order id {}.", orderId);
        if (Boolean.TRUE.equals(isInvalidOrderId(orderId))) {
            LOGGER.error("Received an order request with an invalid order id {}.", orderId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return getOrderByValidOrderId(orderId);
    }

    private Boolean isInvalidOrderId(Long orderId) {
        LOGGER.info("Checking if the given order id {} is between {} and {}.", orderId,
                Constants.VALID_ORDER_ID_MINIMAL_VALUE, Constants.VALID_ORDER_ID_MAXIMUM_VALUE);
        return orderId > Constants.VALID_ORDER_ID_MAXIMUM_VALUE || orderId < Constants.VALID_ORDER_ID_MINIMAL_VALUE;
    }

    private ResponseEntity<Order> getOrderByValidOrderId(Long orderId) {
        for (Order order: orders) {
            if (order.getId().equals(orderId)) {
                LOGGER.info("Successfully found an order with the given order id {}.", orderId);
                LOGGER.debug("The retrieved order is {}.", order);
                return new ResponseEntity<>(order, HttpStatus.OK);
            }
        }
        LOGGER.error("Could not find an order with the given id {}.", orderId);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private void updateInventoryByOrders(Map<String, Integer> inventoryMap) {
        for (Order order: orders) {
            updateInventoryByOrder(inventoryMap, order);
        }
    }

    private void updateInventoryByOrder(Map<String, Integer> inventoryMap, Order order) {
        String orderStatus = order.getStatus().toString();
        int orderQuantity = order.getQuantity();
        if (inventoryMap.containsKey(orderStatus)) {
            int oldQuantity = inventoryMap.get(orderStatus);
            inventoryMap.put(orderStatus, oldQuantity + orderQuantity);
        } else {
            inventoryMap.put(orderStatus, orderQuantity);
        }
    }
}
