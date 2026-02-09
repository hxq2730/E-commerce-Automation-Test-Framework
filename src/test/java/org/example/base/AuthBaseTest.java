package org.example.base;

import io.restassured.response.Response;
import org.example.api.AuthApi;
import org.example.constants.FrameworkConstants;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class AuthBaseTest {
    protected String token;
    protected int userId;

    @BeforeClass(alwaysRun = true)
    public void autoLogin() {
        LogUtils.info("Pre-condition: Login to get Token and User info");
        Response loginRes = AuthApi.login(FrameworkConstants.DEFAULT_EMAIL, FrameworkConstants.DEFAULT_PASSWORD);
        this.token = loginRes.jsonPath().getString("access_token");
        this.userId = loginRes.jsonPath().getInt("user.id");
        Assert.assertNotNull(token, "Login failed, Token is null");
        LogUtils.info("Login successfully as User ID: " + this.userId);
    }
}
