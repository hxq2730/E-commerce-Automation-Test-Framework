package org.example.pages;

import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {

    // Locators
    private static final By HEADER_REGISTRATION = By.xpath("//h1[normalize-space()='Create an " +
            "account.']");
    private static final By INPUT_FULL_NAME = By.xpath("//input[@name='name']");
    private static final By INPUT_EMAIL = By.xpath("//input[@name='email']");
    private static final By INPUT_PASSWORD = By.xpath("//input[@name='password']");
    private static final By INPUT_CONFIRM_PASSWORD = By.xpath("//input[@name" +
            "='password_confirmation']");
    private static final By CHECKBOX_AGREE = By.xpath("//input[@type='checkbox']/parent::label");
    private static final By BUTTON_CREATE_ACCOUNT = By.xpath("//button[normalize-space()='Create " +
            "Account']");
    private static final By MESSAGE_SUCCESS = By.xpath("//span[@data-notify='message' and " +
            "contains(text(),'successful')]");

    private static final By FAIL_ALERT_MESSAGE = By.xpath("//div[contains(@class,'alert-info')" +
            "]//span[@data-notify='message']");
    private static final By FAIL_TEXT = By.xpath("//input[@name='password']/following-sibling" +
            "::span//strong");

    // ACTIONS
    public void createAnAccount(String fullName, String email, String password,
                                String confirmPassword, boolean agreeTerms) {
        WebUI.setText(INPUT_FULL_NAME, fullName);
        WebUI.setText(INPUT_EMAIL, email);
        WebUI.setText(INPUT_PASSWORD, password);
        WebUI.setText(INPUT_CONFIRM_PASSWORD, confirmPassword);
        if (agreeTerms) {
            WebUI.clickElement(CHECKBOX_AGREE);
        }
        //WebUI.clickElement(CHECKBOX_AGREE);
        WebUI.clickElement(BUTTON_CREATE_ACCOUNT);
        WebUI.waitForPageLoaded();
    }

    public String getSuccessMessage() {
        try {
            WebUI.waitForElementVisible(MESSAGE_SUCCESS);
            String msg = WebUI.getElementText(MESSAGE_SUCCESS);
            LogUtils.info("Registration successful.");
            return msg;
        } catch (Exception e) {
            LogUtils.error("Registration Failed.");
            return null;
        }
    }

    public boolean isErrorAlertDisplayed(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));
        try {
            wait.until(ExpectedConditions.textToBePresentInElementLocated(FAIL_ALERT_MESSAGE,
                    expectedMessage));

            LogUtils.info("Error message found: " + expectedMessage);
            return true;
        } catch (Exception e) {
            LogUtils.error("Timeout waiting for error message: " + expectedMessage);
            return false;
        }
    }

    public boolean isHTML5ValidationMessageDisplayed(String expectedKeyword) {
        try {
            Thread.sleep(500);
            WebElement activeElement = DriverManager.getDriver().switchTo().activeElement();

            String validationMessage = activeElement.getAttribute("validationMessage");

            LogUtils.info("Active Element Tag: " + activeElement.getTagName());
            LogUtils.info("HTML5 Message found: " + validationMessage);

            if (validationMessage == null || validationMessage.isEmpty()) {
                return false;
            }
            return validationMessage.toLowerCase().contains(expectedKeyword.toLowerCase());

        } catch (Exception e) {
            LogUtils.error("Error getting HTML5 message from active element: " + e.getMessage());
            return false;
        }
    }

    public boolean isInputErrorTextDisplayed(String expectedMessage) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(5));

        try {
            String dynamicXpath = "//strong[contains(normalize-space(), '" + expectedMessage +
                    "')]";
            By errorLocator = By.xpath(dynamicXpath);

            WebElement errorElement =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(errorLocator));

            LogUtils.info("Found error message: " + errorElement.getText());
            return true;

        } catch (Exception e) {
            LogUtils.error("Failed to find error message with text: " + expectedMessage);
            return false;
        }
    }

    public boolean verifyPageLoaded() {
        WebUI.waitForPageLoaded();
        if (WebUI.verifyElementVisible(HEADER_REGISTRATION)) {
            LogUtils.info("Register Page loaded.");
            return true;
        } else {
            LogUtils.error("Register Page NOT loaded.");
            return false;
        }
    }
}
