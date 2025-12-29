package org.example.base;

import io.qameta.allure.testng.AllureTestNg;
import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.listeners.TestListener;
import org.example.utils.LogUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Listeners({AllureTestNg.class, TestListener.class})
public class BaseTest {
    protected WebDriverWait wait;

    @BeforeMethod
    @Parameters({"browser"})
    public void createDriver(@Optional("chrome") String browserName) {

        // 1. If XML parameter is missing, read from Config file
        if (browserName == null || browserName.equalsIgnoreCase("chrome")) {
            String configBrowser = FrameworkConstants.BROWSER;
            if (configBrowser != null && !configBrowser.isEmpty()) {
                browserName = configBrowser;
            }
        }

        LogUtils.info("Initializing browser: " + browserName);
        WebDriver driver = setupBrowser(browserName);

        // 2. Set driver to ThreadLocal
        DriverManager.setDriver(driver);

        // 3. Maximize and Setup Timeouts via DriverManager (not direct driver)
        DriverManager.getDriver().manage().window().maximize();
        DriverManager.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
        DriverManager.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(FrameworkConstants.WAIT_PAGE_LOADED));

        LogUtils.info("Current Window Size: " + DriverManager.getDriver().manage().window().getSize());
    }


    @AfterMethod
    public void closeDriver(){
        LogUtils.info("Closing browser...");
        DriverManager.quit();
    }

    // Helper method to init driver based on name
    private WebDriver setupBrowser(String browserName) {
        WebDriver driver;
        switch (browserName.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-infobars");
                options.addArguments("--remote-allow-origins=*");

                if (System.getenv("CI") != null) {
                    options.addArguments("--headless=new"); // Chạy ẩn
                    options.addArguments("--window-size=1920,1080"); // Set kích thước ảo
                    options.addArguments("--no-sandbox");
                    options.addArguments("--disable-dev-shm-usage");
                }

                // options.addArguments("--headless=new"); // Uncomment for CI/CD
                //options.addArguments("--window-size=1920,1080");
                //options.addArguments("--incognito");

                //Turn off "Chrome is being controlled by automated test software"
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                Map<String, Object> prefs = new HashMap<String, Object>();

                //Turn of Save password bundle
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);

                //Turn off Password Leak Detection
                prefs.put("profile.password_manager_leak_detection", false);

                // Turn off Autofill Address
                prefs.put("autofill.profile_enabled", false);

                // Turn of autofill Credit Card)
                prefs.put("autofill.credit_card_enabled", false);

                options.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(options);

                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        return driver;
    }
}
