package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class SearchPage extends CommonPage {
    //---LOCATOR---
    private static final By LIST_PRODUCT_NAMES = By.xpath("//div[contains(@class, 'aiz-card-box')" +
            "]//h3/a");

    private static final By MESSAGE_NO_RESULT = By.xpath("//div[contains(@class, " +
            "'search-nothing']");

    //---ACTIONS---

    public String getFirstResultName() {
        WebUI.waitForPageLoaded();

        //Get list of all product name elements
        List<WebElement> productList = WebUI.getWebElements(LIST_PRODUCT_NAMES);

        if (productList.isEmpty()) {
            LogUtils.warn("⚠️ Search result list is empty.");
            return null;
        }
        String productName = productList.getFirst().getText();
        LogUtils.info("Found product: " + productName);

        return productName;
    }

    /**
     * Click on the first product to go to Product Details Page
     *
     * @return ProductDetailsPage - Navigates to the product details.
     */
    public ProductDetailsPage clickFirstProduct() {
        WebUI.waitForPageLoaded();
        List<WebElement> productList = WebUI.getWebElements(LIST_PRODUCT_NAMES);

        if (productList.isEmpty()) {
            LogUtils.error("❌ Cannot click product. Search result list is empty.");
            return null;
        }

        WebElement firstItem = productList.getFirst();
        LogUtils.info("Clicking on product: " + firstItem.getText());
        firstItem.click();

        // Initialize the next page object
        return new ProductDetailsPage();
    }
}
