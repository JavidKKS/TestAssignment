package com.veeam.utils;

import com.veeam.POJO.Order;
import com.veeam.tests.TestBase;
import io.restassured.http.ContentType;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class OrderUtils extends TestBase {

    private static String baseUrl = ConfigurationReader.getProperty("base.url");

    public static void deleteOrderById(int orderId) {
        given().pathParam("id", orderId)
                .when().delete(baseUrl + "/store/order/{id}");

    }

    public static Order getNewOrder() {
        Order order = new Order();
        Random random = new Random();
        String[] statuses = {"pending", "cancelled", "on hold", "shipped", "placed", "approved", "delivered"};

        int randomIndexForStatuses = random.nextInt(statuses.length);
        order.setId(random.nextInt(100));
        order.setPetId(random.nextInt(100));
        order.setStatus(statuses[randomIndexForStatuses]);
        order.setShipDate(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        order.setComplete(random.nextBoolean());
        order.setQuantity(random.nextInt(100));

        return order;
    }

    public static Order postOrder() {
        Order order = getNewOrder();
        given().spec(requestSpecification)
                .contentType(ContentType.JSON)
                .body(order)
                .when().post("/store/order")
                .then().spec(responseSpecification)
                .and().body("id", is(order.getId()),
                        "petId", equalTo(order.getPetId()),
                        "quantity", is(order.getQuantity()),
                        "status", equalTo(order.getStatus()),
                        "shipDate", not(blankString()),
                        "complete", is(order.isComplete()));

        return order;
    }


}
