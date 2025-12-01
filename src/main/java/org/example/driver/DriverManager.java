package org.example.driver;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    //Use ThreadLocal to manage driver for parallel execution
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        // Private constructor to prevent instantiation
    }

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
