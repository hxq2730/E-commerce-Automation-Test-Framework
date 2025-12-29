package org.example.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.helpers.ExcelHelpers;
import org.example.helpers.WebUI;
import org.example.pages.*;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class CheckoutTest extends BaseTest {
    private HomePage homePage;
    private SearchPage searchPage;
    private ProductDetailsPage productDetailsPage;
    private CartPage cartPage;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;
    private ExcelHelpers excelHelpers;
    // --- DATA PROVIDER SETUP ---
    @DataProvider(name = "getCheckoutData")
    public Object [][] getCheckoutData() {
        excelHelpers = new ExcelHelpers();
        String filePath = FrameworkConstants.EXCEL_DATA_FILE_PATH + "CheckoutData.xlsx";
        return excelHelpers.getDataHashTable(filePath, "ShippingInfo", 1, 0 );
    }

    @Test(dataProvider = "getCheckoutData", description = "DDT: Checkout with multiple data sets")
    public void testCheckoutDataDriven(Hashtable<String, String> data) {
        // 1. Init Guest User
        homePage = new HomePage();
        homePage.openHomePage();

        // 2. Search & Add to Car
        searchPage = homePage.searchForProduct(data.get("ProductName"));
        productDetailsPage = searchPage.clickFirstProduct();
        productDetailsPage.addToCart();

        // Assert Add Success
        Assert.assertNotNull(productDetailsPage.getAddToCartSuccessMessage(),
                "FAIL: Product not added to cart.");

        // 3. Go to Cart Page
        productDetailsPage.clickCloseModel();
        cartPage = productDetailsPage.goToCartPage();

        // 4. Proceed to Checkout
        cartPage.clickContinueToShipping();

        // 5. Handle Login Modal
        if (cartPage.isLoginModalDisplay()) {
            cartPage.loginInModal(FrameworkConstants.DEFAULT_EMAIL, FrameworkConstants.DEFAULT_PASSWORD);
            LogUtils.info("Performing 2nd Click on Checkout button after Login...");
            cartPage.clickContinueToShipping();
        } else {
            LogUtils.info("Login Modal did not appear (User might be already logged in).");
        }

        // 6. Init Checkout Page
        checkoutPage = new CheckoutPage();
        checkoutPage.checkoutSuccessfully(data.get("Address"), data.get("Country"), data.get(
                "State"), data.get("City"), data.get("PostalCode"), data.get("Phone"), data.get(
                        "AdditionInfo"));

        // 7. Verify Success
        String confirmMsg = checkoutPage.getOrderConfirmationMessage();
        Assert.assertNotNull(confirmMsg, "FAIL: Order confirmation message is null (Order likely " +
                "failed).");

        Assert.assertTrue(confirmMsg.toLowerCase().contains("thank you"), "FAIL: Success message " +
                "content is incorrect. Found: " + confirmMsg);
    }

    @Test(description = "E2E: Guest User -> Cart -> Force Login -> Checkout -> Order Success")
    @Severity(SeverityLevel.CRITICAL)
    public void testGuestCheckoutWithLoginRedirect() {
        // 1. Init Guest User
        homePage = new HomePage();
        homePage.openHomePage();

        // 2. Search & Add to Car
        searchPage = homePage.searchForProduct("dell");
        productDetailsPage = searchPage.clickFirstProduct();
        productDetailsPage.addToCart();

        // Assert Add Success
        Assert.assertNotNull(productDetailsPage.getAddToCartSuccessMessage(),
                "FAIL: Product not added to cart.");

        // 3. Go to Cart Page
        productDetailsPage.clickCloseModel();
        cartPage = productDetailsPage.goToCartPage();

        // 4. Proceed to Checkout
        cartPage.clickContinueToShipping();

        // 5. Handle Login Modal
        if (cartPage.isLoginModalDisplay()) {
            cartPage.loginInModal(FrameworkConstants.DEFAULT_EMAIL, FrameworkConstants.DEFAULT_PASSWORD);
            LogUtils.info("Performing 2nd Click on Checkout button after Login...");
            cartPage.clickContinueToShipping();
        } else {
            LogUtils.info("Login Modal did not appear (User might be already logged in).");
        }

        // 6. Init Checkout Page
        checkoutPage = new CheckoutPage();
        checkoutPage.checkoutSuccessfully("123 Street", "Vietnam", "Dong Thap", "Cao Lanh", "1000"
                , "0987654321", "fragile goods");

        // 7. Verify Success
        String confirmMsg = checkoutPage.getOrderConfirmationMessage();
        Assert.assertNotNull(confirmMsg, "FAIL: Order confirmation message is null (Order likely " +
                "failed).");

        Assert.assertTrue(confirmMsg.toLowerCase().contains("thank you"), "FAIL: Success message " +
                "content is incorrect. Found: " + confirmMsg);
    }
}
