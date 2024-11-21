package com.api.test.automation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SecurityTest {

    @BeforeClass
    public void setup() {
        // Set up the base URI for the API
        RestAssured.baseURI = "https://petstore.swagger.io/v2";
    }

    @Test
    public void testAddNewPetWithoutAuthToken() {
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
                .baseUri("https://petstore.swagger.io")
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Unauthorized"), "Response body does not indicate authorization error");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 21: Add New Pet without Authentication Token\nResponse Body: " + responseBody);
    }

    @Test
    public void testAddNewPetWithInvalidAuthToken() {
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
                .baseUri("https://petstore.swagger.io")
                .header("Authorization", "Bearer invalid_token")
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 401, "Expected status code 401 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Unauthorized"), "Response body does not indicate authorization error");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 22: Add New Pet with Invalid Authentication Token\nResponse Body: " + responseBody);
    }

    @Test
    public void testAddNewPetWithScriptInName() {
        String requestBody = "{\n" +
                "  \"name\": \"<script>alert('XSS')</script>\",\n" +
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
                .baseUri("https://petstore.swagger.io")
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Cow"), "Response body does not contain expected pet name");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 23: Add New Pet with Script in Name\nResponse Body: " + responseBody);
    }

    @Test
    public void testAddNewPetWithScriptInPetId() {
        String requestBody = "{\n" +
                "  \"name\": \"Cow\",\n" +
                "  \"photoUrls\": [],\n" +
                "  \"id\": \"<script>alert('XSS')</script>\",\n" +
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
                .baseUri("https://petstore.swagger.io")
                .body(requestBody)
                .post("/pet");

        String responseBody = response.getBody().asString();

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200 but received: " + response.getStatusCode());
        Assert.assertTrue(responseBody.contains("Cow"), "Response body does not contain expected pet name");
        Assert.assertTrue(response.getTime() < 2000, "Response time is greater than 2 seconds");

        System.out.println("Test Case 24: Add New Pet with Script in Pet ID\nResponse Body: " + responseBody);
    }

}

