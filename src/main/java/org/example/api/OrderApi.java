package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.models.OrderRequest;
import org.example.utils.LogUtils;

public class OrderApi {
    public static Response createOrder(String token, OrderRequest orderData) {
        LogUtils.info("API: Creating order");
        var request = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(orderData)
                .when()
                .post("/order/store");
        LogUtils.info("Create order status code: " + request.getStatusCode());
        return request;
    }

    public static Response getOrderHistory(String token) {
        LogUtils.info("API: Getting order history");
        var response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/purchase-history");
        LogUtils.info("Get order history status code: " + response.getStatusCode());
        return response;
    }
}
