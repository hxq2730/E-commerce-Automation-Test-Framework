package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.utils.LogUtils;

public class ProductApi {
    public static String getFirstProductId(String keyword) {
        var request = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON);

        if (keyword != null && !keyword.isEmpty()) {
            request.queryParam("name", keyword);
        }

        Response response = request.when().get("/products");
        if (response.getStatusCode() != 200) {
            throw new RuntimeException("No products found with keyword: " + keyword);
        }

        String productID = response.jsonPath().getString("data[0].id");
        LogUtils.info("Found product ID: " + productID);
        return productID;
    }
}
