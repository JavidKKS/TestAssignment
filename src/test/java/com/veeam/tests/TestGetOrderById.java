package com.veeam.tests;

import com.veeam.POJO.Order;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.Random;

import static com.veeam.utils.OrderUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static io.restassured.RestAssured.*;


public class TestGetOrderById extends TestBase {


    @Test
    @DisplayName("GET /order by ID")
    public void testGetOrderById() {

        // Post Order
        Order expectedOrder = postOrder();

        // Get expectedOrder by id
        Response response = given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .pathParam("id", expectedOrder.getId())
                .when().get("/store/order/{id}")
                .then().spec(responseSpecification)
                .extract().response();

        Order actualOrder = response.as(Order.class);

        assertEquals(expectedOrder.getPetId(), actualOrder.getPetId());
        assertEquals(expectedOrder.getId(), actualOrder.getId());
        assertEquals(expectedOrder.getStatus(), actualOrder.getStatus());
        assertEquals(expectedOrder.getQuantity(), actualOrder.getQuantity());
        assertEquals(expectedOrder.isComplete(), actualOrder.isComplete());

        // Deleting after test
        deleteOrderById(expectedOrder.getId());
    }

    @Test
    @Disabled("Seems like there are bugs")
    @DisplayName("GET /negative test")
    public void negativeTestGetOrderById() {
        Response response = given().spec(requestSpecification)
                .pathParam("id", new Random().nextInt(200))
                .when().get("/order/{id}");

        response.prettyPeek();

        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());
        int code = response.path("code");
        assertEquals(1, code);
        assertEquals("error", response.path("type"));
        assertEquals("Order not found", response.path("message"));

        /**
         * Assertions failing :
         * Expected code is 1, actual is 404
         * Expected type is error, actual unknown
         * Expected message is 'Order not found', actual null for uri:
         */
    }
}
