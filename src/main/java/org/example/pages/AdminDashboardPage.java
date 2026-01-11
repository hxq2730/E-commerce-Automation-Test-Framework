package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class AdminDashboardPage {
    private static final By MENU_PRODUCTS = By.xpath("//span[normalize-space()='Products']");
    private static final By BUTTON_ADD_NEW_PRODUCT = By.xpath("//span[normalize-space()=\"Add New" +
            " Product\"]");


    public AdminDashboardPage() {
        // Empty constructor
    }

    public boolean verifyAdminDashboardLoaded() {
        WebUI.waitForPageLoaded();
        boolean isLoaded = WebUI.verifyElementVisible(MENU_PRODUCTS);
        if (isLoaded) {
            LogUtils.info("Admin Dashboard loaded successfully.");
            return true;
        } else {
            LogUtils.error("Admin Dashboard NOT loaded.");
            return false;
        }
    }

    public void openAddNewProductPage() {
        WebUI.clickElement(MENU_PRODUCTS);
        WebUI.clickElement(BUTTON_ADD_NEW_PRODUCT);
        WebUI.waitForPageLoaded();
        LogUtils.info("Navigated to Add New Products Page.");
    }
}
