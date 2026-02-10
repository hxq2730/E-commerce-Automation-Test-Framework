package org.example.tests.hybrid;

import io.restassured.response.Response;
import org.example.api.AuthApi;
import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.pages.LoginPage;
import org.openqa.selenium.Cookie;
import org.testng.annotations.Test;

public class LoginViaApiTest extends BaseTest {
    @Test(description = "Login via API and open Web")
    public void loginByAPIAndOpenWeb(){
        Response response = AuthApi.login(FrameworkConstants.DEFAULT_EMAIL,
                FrameworkConstants.DEFAULT_PASSWORD);
        String token = response.jsonPath().getString("access_token");

        LoginPage loginPage = new LoginPage();
        loginPage.openLoginPage();


        // Inject token into Cookie
        Cookie authCookie = new Cookie("Authorization", "Bearer" + token);
        DriverManager.getDriver().manage().addCookie(authCookie);

        // Go to Dashboard Page
        DriverManager.getDriver().get("https://cms.anhtester.com/dashboard");
        WebUI.sleep(5);

    }
}
