package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class PlaceOrder {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testPlaceOrderWithValidInput() {
        String requestBody = "{\n" +
                "  \"id\": \"22\",\n" +
                "  \"petId\": \"8\",\n" +
                "  \"quantity\": \"1\",\n" +
                "  \"shipDate\": \"2024-11-21\",\n" +
                "  \"status\": \"sold\",\n" +
                "  \"complete\": \"true\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/store/order");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("\"id\":22"), "Response body does not contain the expected order ID");
        Assert.assertTrue(responseBody.contains("\"status\":\"sold\""), "Response body does not contain the expected status");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 9: Valid Order\nResponse Body: " + responseBody);
    }

    @Test
    public void testPlaceOrderWithBlankPetId() {
        String requestBody = "{\n" +
                "  \"id\": \"22\",\n" +
                "  \"petId\": \"\",\n" +
                "  \"quantity\": \"1\",\n" +
                "  \"shipDate\": \"2024-11-21\",\n" +
                "  \"status\": \"sold\",\n" +
                "  \"complete\": \"true\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/store/order");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected an error status code but received 200");
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid"), "Response body does not indicate an error for blank petId");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 10: Blank Pet ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testPlaceOrderWithInvalidPetId() {
        String requestBody = "{\n" +
                "  \"id\": \"22\",\n" +
                "  \"petId\": \"yyy\",\n" +
                "  \"quantity\": \"1\",\n" +
                "  \"shipDate\": \"2024-11-21\",\n" +
                "  \"status\": \"sold\",\n" +
                "  \"complete\": \"true\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/store/order");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected an error status code but received 200");
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid"), "Response body does not indicate an error for invalid petId");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 11: Invalid Pet ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testPlaceOrderWithNegativeQuantity() {
        String requestBody = "{\n" +
                "  \"id\": \"22\",\n" +
                "  \"petId\": \"yyy\",\n" +
                "  \"quantity\": \"-1\",\n" +
                "  \"shipDate\": \"2024-11-21\",\n" +
                "  \"status\": \"sold\",\n" +
                "  \"complete\": \"true\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/store/order");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected an error status code but received 200");
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid") || responseBody.contains("negative"), "Response body does not indicate an error for negative quantity");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 12: Negative Quantity\nResponse Body: " + responseBody);
    }

}
