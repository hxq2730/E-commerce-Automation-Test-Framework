package org.example.tests.api;


import io.restassured.response.Response;
import org.example.api.AuthApi;
import org.example.constants.FrameworkConstants;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthApiTest {
    @Test(priority = 1, description = "Test Register User via API")
    public void testRegisterSuccess() {
        // Call API
        Response response = AuthApi.register();

        // Validate Status code
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201");

        // Validate Body
        boolean result = response.jsonPath().getBoolean("result");
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(result, "Result field should be true");
        Assert.assertTrue(message.contains("Registration Successful"), "Message mismatch!");

        LogUtils.info("Register Test Passed!");
    }

    @Test(priority = 2, description = "Test Login via API")
    public void testLoginSuccess() {
        // Call API
        Response response = AuthApi.login(FrameworkConstants.DEFAULT_EMAIL,
                FrameworkConstants.DEFAULT_PASSWORD);

        // Validate status code
        Assert.assertEquals(response.getStatusCode(), 200);

        String message = response.jsonPath().getString("message");
        String accessToken = response.jsonPath().getString("access_token");

        Assert.assertTrue(message.contains("Successfully logged in"), "Message mismatch");
        LogUtils.info("access_token: " + accessToken);
        Assert.assertNotNull(accessToken, "Access Token should not be null");
    }
}
