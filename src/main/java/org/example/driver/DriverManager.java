package org.example.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {
    //Init variable ThreadLocal
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(WebDriver driverInstance){
        driver.set(driverInstance);
    }

    public static WebDriver getDriver(){
        return driver.get();
    }

    public static void quit(){
        if (driver.get() != null){
            driver.get().quit();
            driver.remove();
        }
    }
}
