package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.ProductDetailsPage;
import org.example.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AddToCartTest extends BaseTest {
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductDetailsPage productDetailsPage;

    @Test(description = "Verify User can add a product to the cart")
    public void testAddProductToCart() {
        homePage = new HomePage();
        homePage.openHomePage();

        //Search for a product
        String keyword = "coca";
        searchPage = homePage.searchForProduct(keyword);

        //Click on the first product result
        productDetailsPage = searchPage.clickFirstProduct();

        //Add to cart
        productDetailsPage.setQuantity(2);
        productDetailsPage.addToCart();

        //Verify success
        String actualMessage = productDetailsPage.getAddToCartSuccessMessage();

        // Assert 1: Check if message is not null (meaning it appeared)
        Assert.assertNotNull(actualMessage, "FAIL: Success message did not appear.");

        // Assert 2: Check content of the message
        String expectedMessage = "added";
        Assert.assertTrue(actualMessage.toLowerCase().contains(expectedMessage.toLowerCase()),
                "FAIL: Success message content is incorrect. Actual: " + actualMessage);

    }
}
