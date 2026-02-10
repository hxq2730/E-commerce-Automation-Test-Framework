package org.example.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.example.constants.FrameworkConstants;
import org.example.models.CartQuantityRequest;
import org.example.models.CartRequest;
import org.example.models.CartResponse;
import org.example.models.CartItem;
import org.example.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class CartApi {
    public static Response addToCart(String token, String productID, int quantity) {
        LogUtils.info("API: Adding product ID " + productID + " to cart");

        CartRequest cardBody = new CartRequest(productID, quantity);
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " +  token)
                .body(cardBody)
                .when()
                .post("/carts/add");
        LogUtils.info("Add to cart status code: " + response.getStatusCode());
        return response;
    }

    public static Response getCartList(String token) {
        LogUtils.info("API: Getting cart list");
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " +  token)
                .when()
                .post("/carts");
        LogUtils.info("Get cart list status code: " + response.getStatusCode());
        return response;
    }

    public static Response deleteCartItem(String token, int cartID) {
        LogUtils.info("API: Deleting cart item ID: " + cartID);
        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " +  token)
                .when()
                .delete("/carts/" + cartID);
        LogUtils.info("Delete cart item status code: " + response.statusCode());
        return response;
    }

    public static void cleanCart(String token){
        LogUtils.info("PRE-CONDITION: Cleaning up the cart...");
        List<CartItem> items = getFlattenCartItems(token);
        if (items.isEmpty()) {
            LogUtils.info("Cart is already empty.");
            return;
        }
        for (CartItem item : items) {
            deleteCartItem(token, item.getId());
        }
        LogUtils.info("Cart cleaned successfully.");

    }
    public static List<CartItem> getFlattenCartItems(String token) {
        LogUtils.info("API: Extracting cart items from response");
        Response response = getCartList(token);
        
        CartResponse[] shops = response.as(CartResponse[].class);
        List<CartItem> allItems = new ArrayList<>();

        if (shops != null) {
            for (CartResponse shop : shops) {
                if (shop.getCart_items() != null) {
                    // Collect all items from each shop into a common list
                    allItems.addAll(shop.getCart_items());
                }
            }
        }

        LogUtils.info("Found total " + allItems.size() + " items across " + (shops != null ? shops.length : 0) + " shops.");
        return allItems;
    }

    public static Response changeQuantity(String token, int cartId, String productId, int newQuantity) {
        LogUtils.info("API: Changing quantity for Cart ID " + cartId + " to " + newQuantity);

        CartQuantityRequest body = new CartQuantityRequest(cartId, productId, newQuantity);

        Response response = RestAssured.given()
                .baseUri(FrameworkConstants.URL_API)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post("/carts/change-quantity");
        LogUtils.info("Change quantity status code: " + response.statusCode());
        return response;
    }
}
