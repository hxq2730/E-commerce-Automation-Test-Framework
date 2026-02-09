package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.utils.LogUtils;

import java.util.Map;

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

    public static Response getAllProduct() {
        LogUtils.info("API: Getting All products ");

        var response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .when()
                .get("/products");
        LogUtils.info("Get All Products Status code: " + response.getStatusCode());
        return response;
    }

    public static Response getProductList(Map<String, Object> queryParams) {
        LogUtils.info("API: Getting products with params: " + (queryParams != null ? queryParams.toString() : ""));

        var request = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON);

        if (queryParams!= null && !queryParams.isEmpty()) {
            request.queryParams(queryParams);
        }

        return request.get("/products/search");
    }

    public static Response getProductDetail(int productId) {
        LogUtils.info("API: Getting product detail for ID: " + productId);
        var response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .when()
                .get("/products/" + productId);
        LogUtils.info("Get product detail status code: " + response.getStatusCode());
        return response;
    }
}
