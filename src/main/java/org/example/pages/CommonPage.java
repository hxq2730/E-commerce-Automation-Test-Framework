package org.example.pages;

import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonPage {

    private static final By BUTTON_CLOSE_POPUP = By.cssSelector("button[data-key='website-popup']");
    private static final By BUTTON_ACCEPT_COOKIE = By.cssSelector("button.aiz-cookie-accept");

    private static final By INPUT_SEARCH = By.id("search");
    private static final By SEARCH_BUTTON = By.cssSelector("button[type='submit']");

    private static final By ICON_CART_DROPDOWN = By.cssSelector(".d-flex.align-items-center.text-reset" +
            ".h-100");

    private static final By BUTTON_VIEW_CART = By.xpath("//a[normalize-space()=\"View cart\"]");

    public void closePopUpDialog(){
        try{
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOfElementLocated(BUTTON_CLOSE_POPUP));

            WebUI.clickElement(BUTTON_CLOSE_POPUP);
            LogUtils.info("Closed the Website Popup.");

        } catch (Exception e){
            LogUtils.info("Popup did not appear. Continuing...");
        }
    }

    public void clickAcceptCookieDialog(){
        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(),
                    Duration.ofSeconds(3));
            wait.until(ExpectedConditions.visibilityOfElementLocated(BUTTON_ACCEPT_COOKIE));
            WebUI.clickElement(BUTTON_ACCEPT_COOKIE);
            LogUtils.info("Clicked accept cookie \"OK. I Understood\"");
        } catch (Exception e){
            LogUtils.info("Popup did not appear. Continuing...");
        }
    }

    /**
     * Common method to close advertisement popups or cookie banners
     * This should be called immediately after page load.
     */
    public void closeCommonPopups() {
        WebDriverWait waitShort = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(3));

        // 1. Handle Newsletter Popup
        try {
            waitShort.until(ExpectedConditions.visibilityOfElementLocated(BUTTON_CLOSE_POPUP));
            WebUI.clickElement(BUTTON_CLOSE_POPUP);
            LogUtils.info("✅ Closed the Website Popup.");
        } catch (Exception e) {
            // Popup not found is normal behavior for some sessions
            LogUtils.info("ℹ️ Website Popup did not appear.");
        }

        // 2. Handle Cookie Banner (Optional)
        try {
            if (WebUI.verifyElementVisible(BUTTON_ACCEPT_COOKIE)) {
                WebUI.clickElement(BUTTON_ACCEPT_COOKIE);
                LogUtils.info("✅ Accepted Cookies.");
            }
        } catch (Exception e) {
            // Ignore
        }
    }

    /**
     * Enter keyword and press Enter (or click search Icon)
     * @param productName The name of the product to search
     * @return SearchPage - Because searching leads to the Search Result Page
     */
    public SearchPage searchForProduct(String productName){
        LogUtils.info("Search for product" + productName);

        // Wait for search input to be interactable
        WebUI.waitForElementVisible(INPUT_SEARCH);
        WebUI.setText(INPUT_SEARCH, productName);

        //Option 1: Press Enter
        //DriverManager.getDriver().findElement(INPUT_SEARCH).sendKeys(Keys.ENTER);

        //Option 2: Click Search Button
        WebUI.clickElement(SEARCH_BUTTON);

        //Return the next page object
        return new SearchPage();
    }

    /**
     * Click on the Cart icon in the header to go to Cart Page.
     * @return CartPage
     */
    public CartPage goToCartPage(){
        LogUtils.info("Navigating to Cart Page...");
        WebUI.clickElement(ICON_CART_DROPDOWN);
        LogUtils.info("Clicking on View Cart link.");
        WebUI.clickElement(BUTTON_VIEW_CART);
        return new CartPage();
    }
}
