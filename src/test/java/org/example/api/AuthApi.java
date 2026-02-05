package org.example.api;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.models.UserAccount;
import org.example.utils.LogUtils;

import java.util.HashMap;
import java.util.Map;

public class AuthApi {
    private static final String BASE_URL = "https://cms.anhtester.com/api/v2";

    public static Response login(String email, String password){
        LogUtils.info("API Login with email: " + email);

        // Create body json
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);

        // Send Request
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/auth/login");
        LogUtils.info("Status code: " + response.getStatusCode());
        return response;
    }

    @Step("API: Register new user with random data")
    public static Response register() {
        UserAccount user = new UserAccount();
        LogUtils.info("API Testing Register new User: " + user.getEmail());

        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(user)
                .when()
                .post("/auth/signup");

        LogUtils.info("Status code: " + response.getStatusCode());
        response.prettyPrint();
        return response;
    }
}
