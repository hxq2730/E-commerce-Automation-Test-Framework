package org.example.tests.api;

import io.restassured.response.Response;
import org.example.api.CartApi;
import org.example.api.ProductApi;
import org.example.base.AuthBaseTest;
import org.example.models.CartItem;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class CartApiTest extends AuthBaseTest {

    @BeforeMethod
    public void cleanUp() {
        CartApi.cleanCart(token);
    }

    @Test(description = "API: Get Cart List: Verify Product Name, Price")
    public void testGetCartList(){
        // Set up: create a new product
        String productId = ProductApi.getFirstProductId(null);
        CartApi.addToCart(token, productId, 1);

        // Get list
        LogUtils.info("Calling Get Cart List API...");
        List<CartItem> items = CartApi.getFlattenCartItems(this.token);

        CartItem item = items.stream()
                .filter(i -> i.getProduct_id() == Integer.parseInt(productId))
                .findFirst()
                .orElse(null);
        Assert.assertNotNull(item, "Item not found in cart!");
        Assert.assertNotNull(item.getProduct_name(), "Product Name is null!");
        Assert.assertFalse(item.getProduct_name().isEmpty(), "Product Name is empty!");
        Assert.assertTrue(item.getPrice() > 0, "Product Price should be > 0");

        LogUtils.info("Verified Product Name: " + item.getProduct_name());
        LogUtils.info("Verified Price: " + item.getPrice());
    }

    @Test(description = "API: Test add to cart (Flow: Search Product -> Add to Cart -> Verify -> Clean)")
    public void testAddToCart() {
        // Search product
        String productID = ProductApi.getFirstProductId(null);

        // Add product to cart
        int quantityToAdd = 2;
        Response addToCartRes = CartApi.addToCart(token, productID, quantityToAdd);
        Assert.assertEquals(addToCartRes.getStatusCode(), 200, "Status code mismatch");
        Assert.assertTrue(addToCartRes.jsonPath().getString("message").contains("added"));

        // Get cart list and verify
        List<CartItem> items = CartApi.getFlattenCartItems(token);
        CartItem foundItem = items.stream()
                .filter(item -> item.getProduct_id() == Integer.parseInt(productID))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(foundItem, "Failed: Product ID " + productID + " not found in any shop cart!");
        Assert.assertEquals(foundItem.getQuantity(), quantityToAdd, "Quantity mismatch!");

        // Teardown
        CartApi.deleteCartItem(token, foundItem.getId());
    }

    @Test(description = "API: Test Delete Cart Item - Verify response when deleting non-existing ID")
    public void testDeleteCartItem_NotFound() {
        // setup
        String productId = ProductApi.getFirstProductId(null);
        CartApi.addToCart(this.token, productId, 1);

        // 2. get total item before deletion
        List<CartItem> itemsBefore = CartApi.getFlattenCartItems(this.token);
        int countBefore = itemsBefore.size();
        LogUtils.info("Item count before delete: " + countBefore);

        int nonExistingCartId = 999999;
        LogUtils.info("Testing Delete with invalid Cart ID: " + nonExistingCartId);
        Response response = CartApi.deleteCartItem(this.token, nonExistingCartId);

        // Verify
        Assert.assertEquals(response.getStatusCode(), 200);

        // get total item after deletion
        List<CartItem> itemsAfter = CartApi.getFlattenCartItems(this.token);
        int countAfter = itemsAfter.size();
        LogUtils.info("Item count after delete: " + countAfter);

        Assert.assertEquals(countAfter, countBefore, "Bug Found: Cart item was removed even with wrong ID!");

        // Verify response
        boolean result = response.jsonPath().getBoolean("result");
        String message = response.jsonPath().getString("message");

        Assert.assertFalse(result, "Result should be False for invalid ID");
        Assert.assertTrue(message.toLowerCase().contains("wrong") || message.toLowerCase().contains("failed"),
                "Message should indicate failure. Actual: " + message);

        LogUtils.info("Negative Test Passed: System handled invalid ID correctly.");
    }

    @Test(description = "API: Test change quantiy cart - Flow: Add Item -> Change Quantity -> Verify New Quantity")
    public void testChangeCartQuantity() {
        LogUtils.info("Step 1: Add initial item with Quantity = 1");

        String productId = ProductApi.getFirstProductId(null);
        int initialQty = 1;

        CartApi.addToCart(this.token, productId, initialQty);

        LogUtils.info("Step 2: Get Cart ID from list");
        List<CartItem> items = CartApi.getFlattenCartItems(this.token);

        // Find item created
        CartItem itemToUpdate = items.stream()
                .filter(i -> i.getProduct_id() == Integer.parseInt(productId))
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(itemToUpdate, "Setup failed: Product not found in cart!");

        // Change quantity
        int newQuantity = 5;
        LogUtils.info("Step 3: Changing quantity to " + newQuantity);

        Response updateRes = CartApi.changeQuantity(this.token, itemToUpdate.getId(), productId, newQuantity);

        // Verify Response của API Update
        Assert.assertEquals(updateRes.getStatusCode(), 200);
        Assert.assertTrue(updateRes.jsonPath().getString("message").contains("updated"),
                "Message mismatch! Actual: " + updateRes.jsonPath().getString("message"));

        // Verify data
        LogUtils.info("Step 4: Verify actual quantity in list");

        List<CartItem> itemsAfterUpdate = CartApi.getFlattenCartItems(this.token);

        CartItem updatedItem = itemsAfterUpdate.stream()
                .filter(i -> i.getId() == itemToUpdate.getId())
                .findFirst()
                .orElse(null);

        Assert.assertNotNull(updatedItem, "Item disappeared after update!");

        // Quantity should be 5
        Assert.assertEquals(updatedItem.getQuantity(), newQuantity, "FAILED: Quantity was not updated correctly!");

        LogUtils.info("Test Passed: Quantity changed from " + initialQty + " to " + newQuantity);

        // --- TEARDOWN ---
        CartApi.deleteCartItem(this.token, itemToUpdate.getId());
    }
}
