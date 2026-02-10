package org.example.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.models.UserAccount;
import org.example.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthApi {

    public static Response login(String email, String password){
        LogUtils.info("API Login with email: " + email);

        // Create body json
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        // Send Request
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login");

        LogUtils.info("Login Status code: " + response.getStatusCode());
        return response;
    }

    @Step("API: Register new user with random data")
    public static Response register() {
        UserAccount user = new UserAccount();
        LogUtils.info("API Testing Register new User: " + user.getEmail());

        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/signup");

        LogUtils.info("Status code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }

    public static void register(UserAccount user){
        LogUtils.info("API: Registering new User: " + user.getEmail());
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/signup");

        LogUtils.info("Status code: " + response.getStatusCode());
        LogUtils.info("API: Register Success!");
    }
}
