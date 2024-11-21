package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FindPet {
    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testFindPetByValidId() {
        int validPetId = 8;

        Response response = RestAssured
                .given()
                .pathParam("petId", validPetId)
                .get("/pet/:petId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("\"id\":" + validPetId), "Response body does not contain the expected pet ID");
        Assert.assertTrue(responseBody.contains("name"), "Response body does not contain the pet name");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 6: Valid Pet ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testFindPetByNonExistentId() {
        int nonExistentPetId = 99999;

        Response response = RestAssured
                .given()
                .pathParam("petId", nonExistentPetId)
                .get("/pet/:petId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Pet not found") || responseBody.contains("error"),
                "Response body does not indicate that the pet was not found");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 7: Non-Existent Pet ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testFindPetByInvalidIdFormat() {
        String invalidPetId = "abc123";

        Response response = RestAssured
                .given()
                .pathParam("petId", invalidPetId)
                .get("/pet/:petId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertNotEquals(response.getStatusCode(), 200, "Expected an error status code but received 200");
        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid") || responseBody.contains("not a valid ID"),
                "Response body does not indicate an error for invalid ID format");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 8: Invalid ID Format\nResponse Body: " + responseBody);
    }


}
