package com.veeam.tests;

import com.veeam.POJO.Order;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static com.veeam.utils.OrderUtils.getNewOrder;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestPostGetDeleteOrder extends TestBase {

    private static final Order newOrder = getNewOrder();

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("POST /new order")
    public void postNewOrder() {
        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(newOrder)
                .when().post("/store/order")
                .then().spec(responseSpecification)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        assertEquals(newOrder.getId(), jsonPath.getInt("id"));
        assertEquals(newOrder.getPetId(), jsonPath.getInt("petId"));
        assertEquals(newOrder.getQuantity(), jsonPath.getInt("quantity"));
        assertEquals(newOrder.getStatus(), jsonPath.getString("status"));
        assertEquals(newOrder.isComplete(), jsonPath.getBoolean("complete"));
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("GET /order by ID")
    public void getOrderById() {
        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .pathParam("id", newOrder.getId())
                .when().get("/store/order/{id}")
                .then().spec(responseSpecification)
                .extract().response();

        Order postedOrder = response.as(Order.class);

        assertEquals(newOrder.getId(), postedOrder.getId());
        assertEquals(newOrder.getPetId(), postedOrder.getPetId());
        assertEquals(newOrder.getStatus(), postedOrder.getStatus());
        assertEquals(newOrder.getQuantity(), postedOrder.getQuantity());
        assertEquals(newOrder.isComplete(), postedOrder.isComplete());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("DELETE /order by ID")
    public void deleteOrderById() {
        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", newOrder.getId())
                .when().delete("/store/order/{id}")
                .jsonPath();

        assertEquals(200, jsonPath.getInt("code"));
        assertEquals("unknown", jsonPath.getString("type"));
        assertEquals(String.valueOf(newOrder.getId()), jsonPath.getString("message"));

    }
}
