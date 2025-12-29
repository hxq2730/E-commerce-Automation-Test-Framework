package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class ForgotPasswordPage {

    //Locators
    public static final By HEADER_FORGOT_PASSWORD = By.xpath("//h1[normalize-space()='Forgot " +
            "password?']");
    public static final By INPUT_EMAIL = By.id("email");
    public static final By BUTTON_SEND_PASSWORD_RESET_LINK = By.xpath("button[normalize-space()" +
            "='Send Password Reset Link']");
    public static final By LINK_BACK_TO_LOGIN = By.xpath("a[contains(text(),'Back to Login')]");

    //Actions
    public boolean isForgetPasswordPageLoaded() {
        return WebUI.verifyElementVisible(HEADER_FORGOT_PASSWORD);
    }

    public ForgotPasswordPage sendPasswordResetLink(String email) {
        LogUtils.info("Reset Password for email: " + email);
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.clickElement(BUTTON_SEND_PASSWORD_RESET_LINK);
        WebUI.waitForPageLoaded();
        return this;
    }
}
