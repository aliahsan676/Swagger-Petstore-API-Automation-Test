package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeleteOrder {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testDeletePurchaseOrderByValidId() {
        int validOrderId = 22;

        Response response = RestAssured
                .given()
                .pathParam("orderId", validOrderId)
                .delete("/store/order/:orderId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("order deleted") || responseBody.contains("success"),
                "Response body does not indicate that the order was successfully deleted");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 17: Delete Purchase Order by Valid ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testDeletePurchaseOrderByNonExistentId() {
        int nonExistentOrderId = 99999; // Replace with a non-existent order ID

        Response response = RestAssured
                .given()
                .pathParam("orderId", nonExistentOrderId)
                .delete("/store/order/:orderId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Order not found") || responseBody.contains("error"),
                "Response body does not indicate that the order was not found");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 18: Delete Purchase Order by Non-Existent ID\nResponse Body: " + responseBody);
    }

}
