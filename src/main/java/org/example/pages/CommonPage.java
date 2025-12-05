package org.example.pages;

import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CommonPage {

    private static final By BUTTON_CLOSE_POPUP = By.cssSelector("button[data-key='website-popup']");
    private static final By BUTTON_ACCEPT_COOKIE = By.cssSelector("button.aiz-cookie-accept");


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
}
