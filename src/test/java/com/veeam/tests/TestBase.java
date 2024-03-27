package com.veeam.tests;
import com.veeam.utils.ConfigurationReader;
import com.veeam.utils.Environment;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.*;
public abstract class TestBase {

    protected static RequestSpecification requestSpecification;
    protected static ResponseSpecification responseSpecification;


    @BeforeAll
    public static void setUp(){
        baseURI = Environment.BASE_URL;
        requestSpecification = given().accept(ContentType.JSON);
        responseSpecification = expect().statusCode(HttpStatus.SC_OK)
                .and().contentType(ContentType.JSON.toString());
    }





}
