package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindOrder {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testFindPurchaseOrderByValidId() {
        int validOrderId = 22;

        Response response = RestAssured
                .given()
                .pathParam("orderId", validOrderId)
                .get("/store/order/:orderId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("\"id\":" + validOrderId), "Response body does not contain the expected order ID");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 13: Valid Order ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testFindPurchaseOrderByNonExistentId() {
        int nonExistentOrderId = 99999;

        Response response = RestAssured
                .given()
                .pathParam("orderId", nonExistentOrderId)
                .get("/store/order/:orderId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Order not found") || responseBody.contains("error"),
                "Response body does not indicate that the order was not found");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 14: Non-Existent Order ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testFindPurchaseOrderByInvalidIdFormat() {
        String invalidOrderId = "abc123";

        Response response = RestAssured
                .given()
                .pathParam("orderId", invalidOrderId)
                .get("/store/order/:orderId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected an error status code but received 200");
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid") || responseBody.contains("not a valid ID"),
                "Response body does not indicate an error for invalid ID format");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 15: Invalid Order ID Format\nResponse Body: " + responseBody);
    }

}
