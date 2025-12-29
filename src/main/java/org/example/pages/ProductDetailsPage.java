package org.example.pages;

import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductDetailsPage extends CommonPage {

    // --- LOCATORS ---
    private static final By BUTTON_ADD_TO_CART = By.xpath("//button[normalize-space()='Add to cart']");
    private static final By INPUT_QUANTITY = By.xpath("//input[@name='quantity']");
    private static final By MESSAGE_SUCCESS = By.xpath("//h3[normalize-space()=\"Item added to your cart!\"]");
    private static final By BUTTON_CLOSE_MODAL = By.xpath("//button[contains(@class, 'close')]");

    // --- ACTIONS ---

    /**
     * Click the "Add to Cart" button.
     */
    public void addToCart(){
        WebUI.waitForPageLoaded();
        LogUtils.info("Clicking 'Add to Cart' button.");

        // Scroll to button to ensure visibility
        WebUI.scrollToElement(BUTTON_ADD_TO_CART);
        WebUI.clickElement(BUTTON_ADD_TO_CART);
    }

    /**
     * Optional: Change quantity before adding
     */
    public void setQuantity(int quantity) {
        LogUtils.info("Setting quantity to: " + quantity);
        WebUI.setText(INPUT_QUANTITY, String.valueOf(quantity));
    }

    /**
     * Get the text content of the success message.
     * @return String - The message text if found, or null if not found/timeout.
     */
    public String getAddToCartSuccessMessage(){
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(MESSAGE_SUCCESS));

            String messageText = WebUI.getElementText(MESSAGE_SUCCESS);
            LogUtils.info("✅ Captured Success Message: " + messageText);

            return messageText;
        } catch (Exception e){
            LogUtils.error("❌ 'Add to Cart' success message NOT found (Timeout).");
            return null;
        }
    }

    public void clickCloseModel(){
        WebUI.clickElement(BUTTON_CLOSE_MODAL);
        LogUtils.info("Closed added to cart message");
    }
}

