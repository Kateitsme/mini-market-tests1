package ru.geekbrains.mini.market.tests;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class RestAssuredTests {
    RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(8189)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    //CRUD tests
    @Test
    public void getProductTest() {
        given()
                .spec(requestSpecification)
                .when()
                .get("market/api/v1/products/1")
                .then()
                .statusCode(200)
                .and()
                .body("id", equalTo(1));
    }

    @Test
    public void getAllProductsTest() {
        given()
                .spec(requestSpecification)
                .when()
                .get("market/api/v1/products")
                .then()
                .statusCode(200)
                .and()
                .body("[0].id", equalTo(1));
    }

    @Test
    public void deleteProductTest() {
        given()
                .spec(requestSpecification)
                .when()
                .delete("market/api/v1/products")
                .then()
                .statusCode(200);
    }

    @Test
    public void updateTest() {
        Map<String, String> productMap = new HashMap<>();
        productMap.put("id", "1");
        productMap.put("title", "Calculator");
        productMap.put("price", "1000");
        productMap.put("categoryTitle", "Electronic");

        given()
                .spec(requestSpecification)
                .body(productMap)
                .when()
                .put("http://localhost:8189/market/api/v1/products")
                .then()
                .log().ifValidationFails(LogDetail.BODY)
                .statusCode(200)
                .body("title", equalTo("Calculator"));
    }

    @Test
    public void addProductTest() {
        Product p = new Product(null, "Calculator", 1000, "Electronic");

        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .post("market/api/v1/products")
                .then()
                .log().ifValidationFails(LogDetail.BODY)
                .body("title", equalTo("Calculator"));
    }

    // 2. Проверить что при отправке некорректных запросов
    // backend должен выдать 400 (возможно придется где-то
    // backend подкрутить)
    // 3. Проверить корректность сообщения об ошибке
    // в случае POST/PUT запросов

    @Test
    public void addProductBadTest(){
        Product p = new Product(2L, "Calculator", 1000, "Electronic");

        given()
                .spec(requestSpecification)
                .body(p)
                .when()
                .post("market/api/v1/products")
                .then()
                .statusCode(400)
                .body("message",equalTo("Id must be null for new entity"));
    }

    @Test
    public void updateNoIdBadTest() {
        Map<String, String> productMap = new HashMap<>();
        productMap.put("title", "Calculator");
        productMap.put("price", "1000");
        productMap.put("categoryTitle", "Electronic");

        given()
                .spec(requestSpecification)
                .body(productMap)
                .when()
                .put("market/api/v1/products")
                .then()
                .statusCode(400)
                .body("message",equalTo("Id must be not null for new entity"));
    }

    @Test
    public void updateIdDontExistBadTest() {
        Map<String, String> productMap = new HashMap<>();
        productMap.put("id", "20");
        productMap.put("title", "Calculator");
        productMap.put("price", "1000");
        productMap.put("categoryTitle", "Electronic");

        given()
                .spec(requestSpecification)
                .body(productMap)
                .when()
                .put("market/api/v1/products")
                .then()
                .statusCode(400)
                .body("message",equalTo("Product with id: 20 doesn't exist"));
    }
}
