package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class CartPage extends CommonPage {

    //---LOCATORS---
    // Locators for elements inside the Cart Table
    // Row of the first product
    private static final By FIRST_PRODUCT_ROW = By.xpath("//section[@id=\"cart-summary\"]//li[1]");

    // Product Name inside the row
    private static final By FIRST_PRODUCT_NAME = By.cssSelector("li:nth-child(1) div:nth-child(1)" +
            " div:nth-child(1) span:nth-child(2)");

    // Product Price inside the row
    private static final By FIRST_PRODUCT_PRICE = By.cssSelector("li:nth-child(1) div:nth-child" +
            "(1) div:nth-child(2) span:nth-child(2)");

    // Quantity input inside the row
    private static final By FIRST_PRODUCT_QUANTITY = By.cssSelector("li:nth-child(1) " +
            "div:nth-child(1) div:nth-child(4) div:nth-child(1)");

    // Checkout
    private static final By BUTTON_CONTINUE_TO_SHIPPING = By.xpath("//button[normalize-space()" +
            "=\"Continue to Shipping\"] | //a[contains(text(), 'Continue to Shipping')]");

    // Locator for login modal
    private static final By MODAL_LOGIN = By.xpath("//div[@id='login-modal']//div[@class='modal" +
            "-content']");

    private static final By INPUT_EMAIL_MODAL = By.xpath("//div[@id='login-modal']//input[@id" +
            "='email']");

    private static final By INPUT_PASSWORD_MODAL = By.xpath("//div[@id='login-modal']//input[@id" +
            "='password']");

    private static final By BUTTON_LOGIN_MODAL = By.xpath("//div[@id='login-modal']//button" +
            "[contains(text(), 'Login')]");

    // --- ACTIONS ---

    /**
     * Get the name of the first product in the cart.
     * @return String Product Name
     */
    public String getFirstProductName() {
        WebUI.waitForPageLoaded();
        String name = WebUI.getElementText(FIRST_PRODUCT_NAME);
        LogUtils.info("Cart - First Product Name: " + name);
        return name;
    }

    /**
     * Get the price of the first product in the cart.
     * @return String Product Price (e.g., "$100.00")
     */
    public String getFirstProductPrice() {
        String price = WebUI.getElementText(FIRST_PRODUCT_PRICE);
        LogUtils.info("Cart - First Product Price: " + price);
        return price;
    }

    /**
     * Get the quantity of the first product in the cart.
     * @return String Product Quantity (e.g., "1")
     */
    public int getFirstProductQuantity() {
        String quantity = WebUI.getElementText(FIRST_PRODUCT_QUANTITY);
        LogUtils.info("Cart - First Product Price: " + quantity);
        return Integer.parseInt(quantity);
    }

    /**
     * Click 'Continue To Shipping' to navigate to the Shipping page.
     * @return CheckoutPage
     */
    public void clickContinueToShipping() {
        WebUI.waitForPageLoaded();
        LogUtils.info("Clicking 'Continue to shipping' button.");
        WebUI.waitForElementVisible(BUTTON_CONTINUE_TO_SHIPPING);
        WebUI.waitForElementClickable(BUTTON_CONTINUE_TO_SHIPPING);
        WebUI.scrollToElement(BUTTON_CONTINUE_TO_SHIPPING);
        WebUI.clickElement(BUTTON_CONTINUE_TO_SHIPPING);
    }

    public boolean isLoginModalDisplay() {
        return WebUI.verifyElementVisible(MODAL_LOGIN);
    }

    public void loginInModal(String email, String password) {
        LogUtils.info("Detected Login Modal. Logging in as: " + email);

        WebUI.setText(INPUT_EMAIL_MODAL, email);
        WebUI.setText(INPUT_PASSWORD_MODAL, password);
        WebUI.clickElement(BUTTON_LOGIN_MODAL);

        WebUI.waitForElementInvisible(MODAL_LOGIN);
        LogUtils.info("Login in Modal success. Modal closed.");
    }
}
