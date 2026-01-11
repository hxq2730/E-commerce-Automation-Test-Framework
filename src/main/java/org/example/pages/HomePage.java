package org.example.pages;

import org.example.constants.FrameworkConstants;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class HomePage extends CommonPage {

    private static final By LOGO_IMG = By.xpath("//a[contains(@class,'d-block')]//img");
    private static final By LINK_REGISTER = By.xpath("//a[normalize-space()='Registration']");

    // --- ACTIONS ---

    public HomePage(){}

    public boolean isHomePageLoaded() {
        return WebUI.verifyElementVisible(LOGO_IMG);
    }

    /**
     * Open the Home Page, handle popups, and verify the page is ready.
     * @return HomePage instance for method chaining
     */
    public void openHomePage() {
        LogUtils.info("Opening Home Page...");
        WebUI.openURL(FrameworkConstants.URL_DEFAULT);
        WebUI.waitForPageLoaded();

        closeCommonPopups();

        // Verify that we are truly on the Home Page
        boolean isPageLoaded = WebUI.verifyElementVisible(LOGO_IMG);
        if (!isPageLoaded) {
            LogUtils.error("❌ Home Page did not load correctly (Slider not visible).");
        }
        LogUtils.info("✅ Home Page loaded and ready.");
    }

    public RegisterPage openRegisterPage(){
        WebUI.clickElement(LINK_REGISTER);
        WebUI.waitForPageLoaded();
        return new RegisterPage();
    }
}
