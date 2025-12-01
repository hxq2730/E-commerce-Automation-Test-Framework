package org.example.base;

import org.example.driver.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected WebDriverWait wait;

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {
        System.out.println("Start: Initializing browser: " + browserName);
        WebDriver tmpDriver;

        if (browserName.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            Map<String, Object> prefs = new HashMap<String, Object>();

            //Turn of Save password bundle
            prefs.put("credentials_enable_service", false);
            prefs.put("profile.password_manager_enabled", false);

            //Turn off Password Leak Detection
            prefs.put("profile.password_manager_leak_detection", false);

            options.setExperimentalOption("prefs", prefs);

            //Turn off "Chrome is being controlled by automated test software"
            options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

            //options.addArguments("--incognito");
            //options.addArguments("--headless=new");
            //options.addArguments("--window-size=1920,1080");

            tmpDriver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            tmpDriver = new FirefoxDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            tmpDriver = new EdgeDriver();
        } else {
            throw new RuntimeException("Browser not supported: " + browserName);
        }

        DriverManager.setDriver(tmpDriver);

        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(10));
    }

    @AfterMethod
    public void closeDriver(){
        System.out.println("End: Closing browser");
        DriverManager.quit();
    }
}
