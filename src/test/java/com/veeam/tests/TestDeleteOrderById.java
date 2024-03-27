package com.veeam.tests;

import com.veeam.POJO.Order;
import com.veeam.utils.OrderUtils;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestDeleteOrderById extends TestBase{


    @Test
    @DisplayName("DELETE /order by ID")
    public void deleteOrderById() {

        Order newOrder = OrderUtils.postOrder();

        JsonPath jsonPath = given().accept(ContentType.JSON)
                .pathParam("id", newOrder.getId())
                .when().delete("/store/order/{id}")
                .jsonPath();

        assertEquals(200, jsonPath.getInt("code"));
        assertEquals("unknown", jsonPath.getString("type"));
        assertEquals(String.valueOf(newOrder.getId()), jsonPath.getString("message"));

    }
}
