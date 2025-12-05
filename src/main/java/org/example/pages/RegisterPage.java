package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class RegisterPage {

    //Locators
    public static final By HEADER_REGISTRATION = By.xpath("//h1[normalize-space()='Create an " +
            "account.']");

    //Actions
    public void verifyPageLoaded(){
        WebUI.waitForPageLoaded();
        if(WebUI.verifyElementVisible(HEADER_REGISTRATION)){
            LogUtils.info("Register Page loaded.");
        } else {
            LogUtils.error("Register Page NOT loaded.");
        }
    }
}
