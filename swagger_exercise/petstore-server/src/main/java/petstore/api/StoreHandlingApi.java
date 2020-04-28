package petstore.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import petstore.model.Order;
import utils.Constants;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreHandlingApi implements StoreApi {
    private List<Order> orders;

    public StoreHandlingApi(List<Order> orders) {
        this.orders = orders;
    }

    @ApiOperation(value = "Returns pet inventories by status", nickname = "getInventory", notes = "Returns a map of status codes to quantities", response = Integer.class, responseContainer = "Map", tags = {"store",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Map.class, responseContainer = "Map")})
    @RequestMapping(value = "/store/inventory",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Map<String, Integer>> getInventory() {
        Map<String, Integer> inventoryMap = new HashMap<>();
        for (Order order: orders) {
            updateInventoryByOrder(inventoryMap, order);
        }
        return new ResponseEntity<Map<String, Integer>>(inventoryMap, HttpStatus.OK);
    }

    @ApiOperation(value = "Find purchase order by ID", nickname = "getOrderById", notes = "For valid response try integer IDs with value >= 1 and <= 10. Other values will generated exceptions", response = Order.class, tags = {"store",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Order.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied"),
            @ApiResponse(code = 404, message = "Order not found")})
    @RequestMapping(value = "/store/order/{orderId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<Order> getOrderById(@Min(Constants.VALID_ORDER_ID_MINIMAL_VALUE) @Max(Constants.VALID_ORDER_ID_MAXIMUM_VALUE) @ApiParam(value = "ID of pet that needs to be fetched", required = true) @PathVariable("orderId") Long orderId) {
        if (isInvalidOrderId(orderId)) {
            return new ResponseEntity<Order>(HttpStatus.BAD_REQUEST);
        }

        for (Order order: orders) {
            if (order.getId().equals(orderId)) {
                return new ResponseEntity<Order>(order, HttpStatus.OK);
            }
        }
        return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
    }

    private Boolean isInvalidOrderId(Long orderId) {
        return orderId > Constants.VALID_ORDER_ID_MAXIMUM_VALUE || orderId < Constants.VALID_ORDER_ID_MINIMAL_VALUE;
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
