package com.veeam.tests;


import com.veeam.POJO.Order;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Map;

import static com.veeam.utils.OrderUtils.deleteOrderById;
import static com.veeam.utils.OrderUtils.getNewOrder;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestPostOrder extends TestBase {


    @Test
    @DisplayName("POST /order POJO")
    public void placeOrderAsPojo() {

        Order order = getNewOrder();

        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(order)
                .when().post("/store/order")
                .then().spec(responseSpecification)
                .extract().response();

        Order postedOrder = response.as(Order.class);

        assertEquals(order.getId(), postedOrder.getId());
        assertEquals(order.getPetId(), postedOrder.getPetId());
        assertEquals(order.getStatus(), postedOrder.getStatus());
        assertEquals(order.getQuantity(), postedOrder.getQuantity());
        assertEquals(order.isComplete(), postedOrder.isComplete());

        // Deleting after posting
        deleteOrderById(order.getId());

    }

    @Test
    @DisplayName("POST /order/ Map")
    public void placeOrderAsMap() {
        Order order = getNewOrder();

        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(order)
                .when().post("/store/order")
                .then().spec(responseSpecification)
                .extract().response();

        Map<String, Object> orderMap = response.as(Map.class);

        assertEquals(order.getId(), orderMap.get("id"));
        assertEquals(order.getPetId(), orderMap.get("petId"));
        assertEquals(order.getStatus(), orderMap.get("status"));
        assertEquals(order.getQuantity(), orderMap.get("quantity"));
        assertEquals(order.isComplete(), orderMap.get("complete"));

        deleteOrderById(order.getId());
    }

    @Test
    @DisplayName("POST /order/ JsonPath")
    public void placeOrderJsonPath() {
        Order order = getNewOrder();

        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(order)
                .when().post("/store/order")
                .then().spec(responseSpecification)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        assertEquals(order.getId(), jsonPath.getInt("id"));
        assertEquals(order.getPetId(), jsonPath.getInt("petId"));
        assertEquals(order.getStatus(), jsonPath.getString("status"));
        assertEquals(order.getQuantity(), jsonPath.getInt("quantity"));
        assertEquals(order.isComplete(), jsonPath.getBoolean("complete"));

        deleteOrderById(order.getId());

    }
}
