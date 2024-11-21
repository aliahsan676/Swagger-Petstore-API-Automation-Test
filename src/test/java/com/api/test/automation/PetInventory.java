package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PetInventory {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testReturnsPetInventoriesByStatus() {
        Response response = RestAssured
                .given()
                .get("/store/inventory");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("available") || responseBody.contains("sold") || responseBody.contains("pending"),
                "Response body does not contain expected inventory statuses");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 16: Returns Pet Inventories by Status\nResponse Body: " + responseBody);
    }

}
