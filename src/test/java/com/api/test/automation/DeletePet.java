package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class DeletePet {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testDeletePetByValidId() {
        int validPetId = 8;

        Response response = RestAssured
                .given()
                .pathParam("petId", validPetId)
                .delete("/pet/:petId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Pet deleted") || responseBody.contains("success"),
                "Response body does not indicate that the pet was successfully deleted");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 19: Delete Pet by Valid ID\nResponse Body: " + responseBody);
    }

    @Test
    public void testDeletePetByNonExistentId() {
        int nonExistentPetId = 9999;

        Response response = RestAssured
                .given()
                .pathParam("petId", nonExistentPetId)
                .delete("/pet/:petId");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 404, "Expected status code 404 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Pet not found") || responseBody.contains("error"),
                "Response body does not indicate that the pet was not found");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 20: Delete Pet by Non-Existent ID\nResponse Body: " + responseBody);
    }


}
