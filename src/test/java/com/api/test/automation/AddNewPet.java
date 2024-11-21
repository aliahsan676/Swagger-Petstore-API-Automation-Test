package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddNewPet {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testAddFullPetDetails() {
        // Test Case 1: Add a new pet with full details

        String requestBody = "{\n" +
                "  \"name\": \"Cow\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"id\": \"8\",\n" +
                "  \"category\": {\n" +
                "    \"id\": \"8\",\n" +
                "    \"name\": \"New House Pet\"\n" +
                "  },\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": \"8\",\n" +
                "      \"name\": \"New House Pet\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("\"name\":\"Cow\""), "Response body does not contain expected name");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 1: Full Pet Details");
        System.out.println("Response Body: " + responseBody);
        System.out.println("Response Time: " + response.getTime() + " ms");
    }

    @Test
    public void testAddMinimalPetDetails() {
        // Test Case 2: Add a new pet with minimal details

        String requestBody = "{\n" +
                "  \"name\": \"Cow\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("\"name\":\"Cow\""), "Response body does not contain expected name");

        Assert.assertTrue(responseBody.contains("id") || responseBody.contains("error"),
                "Response does not indicate success or a missing field error");

        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 2: Minimal Pet Details");
        System.out.println("Response Body: " + responseBody);
        System.out.println("Response Time: " + response.getTime() + " ms");
    }

    @Test
    public void testAddPetWithInvalidData() {
        // Test Case 3: Add a new pet with invalid data types

        String requestBody = "{\n" +
                "  \"name\": \"123\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"id\": \"Cow\",\n" +
                "  \"category\": {\n" +
                "    \"id\": \"Cow\",\n" +
                "    \"name\": \"New House Pet\"\n" +
                "  },\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": \"Cow\",\n" +
                "      \"name\": \"New House Pet\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions

        int statusCode = response.getStatusCode();
        Assert.assertNotEquals(statusCode, 200, "Expected an error status code but received: " + statusCode);


        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("invalid") || responseBody.contains("bad request"),
                "Response body does not indicate an error for invalid data");


        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 3: Invalid Data Types");
        System.out.println("Response Body: " + responseBody);
        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Time: " + responseTime + " ms");
    }

    @Test
    public void testAddPetWithExistingId() {
        // Test Case 4: Add a new pet with an existing ID

        String requestBody = "{\n" +
                "  \"name\": \"Cow\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"id\": \"8\",\n" +
                "  \"category\": {\n" +
                "    \"id\": \"8\",\n" +
                "    \"name\": \"New House Pet\"\n" +
                "  },\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": \"8\",\n" +
                "      \"name\": \"New House Pet\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions

        int statusCode = response.getStatusCode();
        Assert.assertNotEquals(statusCode, 200, "Expected an error status code but received: " + statusCode);

        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("id already exists"),
                "Response body does not indicate an error for an existing ID");

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 4: Existing ID\nResponse Body: " + responseBody);
        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Time: " + responseTime + " ms");
    }

    @Test
    public void testAddPetWithLongName() {
        // Test Case 5: Add a new pet with a long name

        String longName = "Dooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooog";
        String requestBody = "{\n" +
                "  \"name\": \"" + longName + "\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"id\": \"9\",\n" +
                "  \"category\": {\n" +
                "    \"id\": \"9\",\n" +
                "    \"name\": \"New House Pet\"\n" +
                "  },\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": \"9\",\n" +
                "      \"name\": \"New House Pet\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \"available\"\n" +
                "}";

        Response response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions


        int statusCode = response.getStatusCode();
        Assert.assertNotEquals(statusCode, 200, "Expected an error status code but received: " + statusCode);

        Assert.assertTrue(responseBody.contains("error") || responseBody.contains("name too long") || responseBody.contains("bad request"),
                "Response body does not indicate an error for a long name");

        long responseTime = response.getTime();
        Assert.assertTrue(responseTime < 2000, "Response time is greater than 2 seconds");

        // Debugging outputs
        System.out.println("Test Case 5: Long Name\nResponse Body: " + responseBody);
        System.out.println("Response Status Code: " + statusCode);
        System.out.println("Response Time: " + responseTime + " ms");
    }

}



