package com.veeam.tests;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestGetInventory extends TestBase {


    @Test
    @DisplayName("GET / inventories")
    public void getAllInventories() {
        Response response = given().spec(requestSpecification)
                .when().get("/store/inventory")
                .then().spec(responseSpecification)
                .and().extract().response();

        assertEquals(response.statusCode(), HttpStatus.SC_OK);
        assertEquals(response.contentType(), ContentType.JSON.toString());

        JsonPath jsonPath = response.jsonPath();

        response.prettyPrint();

        String sold = jsonPath.getString("sold");
        String available = jsonPath.getString("available");
        String pending = jsonPath.getString("pending");
        String string = jsonPath.getString("string");
        String unavailable = jsonPath.getString("unavailable");


    }
}
