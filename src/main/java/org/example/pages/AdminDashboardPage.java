package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class AdminDashboardPage {
    private static final By MENU_PRODUCTS = By.xpath("//span[normalize-space()='Products']");
    private static final By CARD_TOTAL_CUSTOMER = By.xpath("//span[normalize-space()='Total " +
            "Customer']");

    public AdminDashboardPage() {
        // Empty constructor
    }

    public void verifyAdminDashboardLoaded() {
        WebUI.waitForPageLoaded();
        boolean isLoaded = WebUI.verifyElementVisible(CARD_TOTAL_CUSTOMER);
        if (isLoaded) {
            LogUtils.info("Admin Dashboard loaded successfully.");
        } else {
            LogUtils.error("Admin Dashboard NOT loaded.");
        }
    }

    public void openProductPage() {
        WebUI.clickElement(MENU_PRODUCTS);
        LogUtils.info("Navigated to Products Management page.");
    }
}
