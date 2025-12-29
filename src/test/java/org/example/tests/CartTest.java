package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.CartPage;
import org.example.pages.HomePage;
import org.example.pages.ProductDetailsPage;
import org.example.pages.SearchPage;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;

    @Test(description = "Verify Product details in Cart Page")
    public void testProductAddToCart() {
        // --- PRE-CONDITION: ADD PRODUCT TO CART ---
        homePage = new HomePage();
        homePage.openHomePage();

        String searchKeyword = "coca";
        searchPage = homePage.searchForProduct(searchKeyword);

        // Save product name from Search Result to compare later
        String expectedProductName = searchPage.getFirstResultName();
        LogUtils.info("Expected Product Name: " + expectedProductName);

        productDetailsPage = searchPage.clickFirstProduct();
        productDetailsPage.addToCart();

        String successMessage = productDetailsPage.getAddToCartSuccessMessage();

        // Check if message is not null (meaning it appeared)
        Assert.assertNotNull(successMessage, "FAIL: Added to cart failed, no success message.");

        //Close Add to Cart success message
        productDetailsPage.clickCloseModel();

        // --- ACTION: GO TO CART PAGE ---
        cartPage = productDetailsPage.goToCartPage();

        // --- VERIFY: CHECK DATA IN CART ---
        String actualProductName = cartPage.getFirstProductName();

        // Assert Name
        Assert.assertEquals(actualProductName, expectedProductName,
                "FAIL: Product Name in Cart does not match. Expected: " + expectedProductName +
                        ", Found: " + actualProductName);

        // Assert Price
        String price = cartPage.getFirstProductPrice();
        Assert.assertNotNull(price, "FAIL: Product Price is missing.");
    }
}
