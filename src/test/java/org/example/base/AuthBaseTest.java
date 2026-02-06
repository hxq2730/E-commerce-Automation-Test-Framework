package org.example.base;

import io.restassured.response.Response;
import org.example.api.AuthApi;
import org.example.constants.FrameworkConstants;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;

public class AuthBaseTest {
    protected String token;

    @BeforeClass(alwaysRun = true)
    public void autoLogin() {
        LogUtils.info("Pre-condition: Login to get Token for all tests in this class");
        Response res = AuthApi.login(FrameworkConstants.DEFAULT_EMAIL, FrameworkConstants.DEFAULT_PASSWORD);
        this.token = res.jsonPath().getString("access_token");
        Assert.assertNotNull(token, "Login failed, Token is null");
    }
}
