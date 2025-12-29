package org.example.pages;

import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends CommonPage {

    //Locators
    private static final By INPUT_EMAIL = By.id("email");
    private static final By INPUT_PASSWORD = By.id("password");
    private static final By BUTTON_LOGIN = By.xpath("//button[normalize-space()='Login']");
    private static final By ALERT_MESSAGE = By.xpath("//span[@data-notify='message']");
    private static final By INPUT_ERROR_MESSAGE = By.xpath("//strong[contains(text(),\"The email "
            + "field" + " is" + " required when phone is not pres\")]");
    private static final By CHECKBOX_REMEMBER = By.xpath("//span[normalize-space()='Remember Me']");
    private static final By LINK_FORGOT_PASSWORD = By.xpath("//a[normalize-space()=\"Forgot " +
            "password?\"]");
    private static final By LINK_REGISTER = By.xpath("//a[contains(@href, 'users/registration')]");

    //Actions
    public void openLoginPage() {
        WebUI.openURL(FrameworkConstants.URL_CMS_USER);
        WebUI.waitForPageLoaded();

        this.closePopUpDialog();
        this.clickAcceptCookieDialog();

        if (!WebUI.verifyElementVisible(BUTTON_LOGIN)) {
            LogUtils.error("Login Page is not displayed");
        }
    }

    public void performLogin(String email, String password) {
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.setText(INPUT_PASSWORD, password);
        WebUI.clickElement(BUTTON_LOGIN);
    }

    public boolean isErrorAlertDisplayed(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        try {
            // Wait until the specific text exists in the element, This condition checks both
            // presence and text content
            wait.until(ExpectedConditions.textToBePresentInElementLocated(ALERT_MESSAGE,
                    expectedMessage));

            LogUtils.info("Error message found: " + expectedMessage);
            return true;
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for error message: " + expectedMessage);
            return false;
        }
    }

    public boolean isInputErrorTextDisplayed(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(INPUT_ERROR_MESSAGE));
            return true;
        } catch (Exception e) {
            LogUtils.error("Failed to find input error text: " + expectedMessage);
            return false;
        }
    }

    public boolean isHTML5ValidationMessageDisplayed(String expectedKeyword) {
        try {
            WebElement element = DriverManager.getDriver().findElement(INPUT_EMAIL);
            String validationMessage = element.getAttribute("validationMessage");
            LogUtils.info("HTML5 Message: " + validationMessage);
            return validationMessage.contains(expectedKeyword);
        } catch (Exception e) {
            return false;
        }
    }

    public void loginWithEnterKey(String email, String password) {
        LogUtils.info("Performing login using ENTER key with Email: " + email);
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.setText(INPUT_PASSWORD, password);

        // Press ENTER on the Password field instead of clicking the Login button
        DriverManager.getDriver().findElement(INPUT_PASSWORD).sendKeys(Keys.ENTER);
    }

    public CustomerDashboardPage loginSuccess(String email, String password) {
        LogUtils.info("Performing customer login with Email: " + email + " | Password: " + password);
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.setText(INPUT_PASSWORD, password);
        WebUI.clickElement(BUTTON_LOGIN);
        WebUI.waitForPageLoaded();

        return new CustomerDashboardPage();
    }

    public void loginFail(String email, String password) {
        LogUtils.info("Performing customer login (expecting failure) with Email: " + email + " | "
                + "Password: " + password);
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.setText(INPUT_PASSWORD, password);
        WebUI.clickElement(BUTTON_LOGIN);
    }

    public LoginPage setRememberMe(boolean isRemember) {
        if (!isRemember) {
            WebUI.clickElement(CHECKBOX_REMEMBER);
            LogUtils.info("Checked 'Remember Me' checkbox.");
        }
        return this;
    }

    public ForgotPasswordPage clickForgotPassword() {
        WebUI.clickElement(LINK_FORGOT_PASSWORD);
        LogUtils.info("Clicked 'Forgot Password' link.");
        return new ForgotPasswordPage();
    }

    //
    public RegisterPage clickRegister() {
        WebUI.clickElement(LINK_REGISTER);
        LogUtils.info("Clicked 'Register Now' link.");
        return new RegisterPage();
    }
}
