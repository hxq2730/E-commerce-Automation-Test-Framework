package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;


public class CustomerDashboardPage extends CommonPage {
    // Locators specific to Customer Dashboard
    private static final By MENU_PURCHASE_HISTORY = By.xpath("//a[contains(@href," +
            "'purchase_history']");
    private static final By SECTION_DEFAULT_SHIPPING_ADDRESS = By.xpath("//h6[normalize-space()" +
            "=\"Default Shipping Address\"]");

    public CustomerDashboardPage() {
        // Empty constructor
    }

    public boolean isDashboardLoaded() {
        //WebUI.waitForPageLoaded();
        return WebUI.verifyElementVisible(SECTION_DEFAULT_SHIPPING_ADDRESS);
    }

    public void openPurchasedHistory() {
        WebUI.clickElement(MENU_PURCHASE_HISTORY);
        LogUtils.info("Navigated to Purchase History page.");
    }

}
