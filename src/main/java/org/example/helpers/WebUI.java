package org.example.helpers;

import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.utils.LogUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WebUI {
    private static WebDriver getDriver() {
        return DriverManager.getDriver();
    }

    // --- WAITS ---
    public static void waitForElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for element visible. " + error.getMessage());
        }
    }

    public static void waitForElementInvisible(By by){
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(),
                    Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
        } catch (Throwable error){
            LogUtils.error("Timeout waiting for element invisible." + error.getMessage());
        }
    }

    public static void waitForElementClickable(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.elementToBeClickable(by));
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for element clickable. " + error.getMessage());
        }
    }

    /**
     * Wait for element to be present in DOM (doesn't have to be visible).
     * Useful for hidden inputs like File Upload.
     */
    public static void waitForElementPresent(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Exception e) {
            LogUtils.warn("⚠️ Element not found in DOM: " + by);
        }
    }

    // --- ACTIONS ---

    // Init Actions
    private static Actions getActions() {
        return new Actions(getDriver());
    }

    //1. Hover
    public static void hoverOnElement(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().moveToElement(element).perform();
    }

    //2. Right Click
    public static void rightClick(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().contextClick(element).perform();
    }

    //3. Double Click
    public static void doubleClick(By by) {
        WebElement element = getDriver().findElement(by);
        getActions().doubleClick(element).perform();
    }

    //4. Drag and Drop
    public static void dragAndDrop(By source, By target) {
        WebElement sourceElement = getDriver().findElement(source);
        WebElement targetElement = getDriver().findElement(target);
        getActions().dragAndDrop(sourceElement, targetElement).perform();
    }

    //5. Slider
    public static void dragAndDropByOffset(By by, int x, int y) {
        WebElement element = getDriver().findElement(by);
        getActions().dragAndDropBy(element, x, y).perform();
    }

    public static void setSliderValue(By by, String value) {
        WebElement slider = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript(
                "var nativeInputValueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, " +
                        "'value').set;" +
                        "nativeInputValueSetter.call(arguments[0], arguments[1]);" +
                        "var event = new Event('input', { bubbles: true});" +
                        "arguments[0].dispatchEvent(event);",
                slider, value
        );
    }

    //6. SendKeys advanced (Press Enter/Tab)
    public static void pressKey(Keys key) {
        getActions().sendKeys(key).perform();
    }

    //7. Copy and past (hold Ctrl key)
    public static void copyAndPast(By source, By target) {
        WebElement sourceElement = getDriver().findElement(source);
        WebElement targetElement = getDriver().findElement(target);

        // Ctrl + A -> Ctrl + C
        getActions().click(sourceElement)
                .keyDown(Keys.CONTROL)
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .keyDown(Keys.CONTROL)
                .sendKeys("c")
                .keyUp(Keys.CONTROL)
                .perform();

        // Ctrl + V
        getActions().click(targetElement).keyDown(Keys.CONTROL).sendKeys("v").keyUp(Keys.CONTROL).perform();

    }

    // JavascriptExecutor

    //JS Click: Used when ElementClickInterceptedException (Obstructed) error occurs
    public static void jsClick(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].click();", element);
    }

    // Scroll to Element: Used when the element is at the bottom of the page, Selenium cannot scroll to it automatically
    public static void scrollToElement(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    //
    public static void scrollToElementOnTop(By by) {
        WebElement element = getDriver().findElement(by);
        JavascriptExecutor js = (JavascriptExecutor) getDriver();

        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});", element);
    }

    public static void uploadFile(By by, String filePath){
        LogUtils.info("📁 Uploading file to hidden element: " + filePath);
        waitForElementPresent(by);
        try {
            getDriver().findElement(by).sendKeys(filePath);
        } catch (Exception e) {
            LogUtils.error("❌ Error uploading file: " + e.getMessage());
            throw e;
        }
        LogUtils.info("Set text to hidden element: " + by);
    }
    public static void openURL(String url) {
        getDriver().get(url);
        LogUtils.info("Open URL: " + url);
    }

    public static void clickElement(By by) {
        waitForElementClickable(by);
        highLightElement(by); // Optional: Highlight before click
        getDriver().findElement(by).click();
        LogUtils.info("Clicked on element: " + by);
    }

    public static void setText(By by, String value) {
        waitForElementVisible(by);
        highLightElement(by);
        WebElement element = getDriver().findElement(by);
        element.clear();
        element.sendKeys(value);
        LogUtils.info("Set text: '" + value + "' on element: " + by);
    }

    public static String getElementText(By by) {
        waitForElementVisible(by);
        String text = getDriver().findElement(by).getText();
        LogUtils.info("Get text: '" + text + "' from element: " + by);
        return text;
    }

    public static void pressEnter(By by){
        DriverManager.getDriver().findElement(by).sendKeys(Keys.ENTER);
        LogUtils.info("Press Enter on element: " + by);
    }

    // --- UTILITIES ---

    public static void highLightElement(By by) {
        try {
            // JavascriptExecutor to highlight element
            if (getDriver() instanceof JavascriptExecutor) {
                ((JavascriptExecutor) getDriver()).executeScript("arguments[0].style.border='3px solid red'", getDriver().findElement(by));
                sleep(0.5); // Wait a bit to see the highlight
            }
        } catch (Exception e) {
            LogUtils.error("Cannot highlight element: " + e.getMessage());
        }
    }

    public static void sleep(double second) {
        try {
            Thread.sleep((long) (second * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Get HTML5 Validation Message (The bubble popup)
    public static String getHTML5ValidationMessage(By by) {
        waitForElementVisible(by);
        String validationMessage = getDriver().findElement(by).getAttribute("validationMessage");
        LogUtils.info("HTML5 Validation Message: " + validationMessage);
        return validationMessage;
    }

    // --- NEW VERIFICATION METHODS (For Assertions) ---

    public static boolean verifyElementVisible(By by) {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            LogUtils.info("Verify element visible: " + by);
            return true;
        } catch (Exception e) {
            LogUtils.error("Element NOT visible: " + by);
            return false;
        }
    }

    public static boolean verifyElementText(By by, String expectedText) {
        waitForElementVisible(by);
        String actualText = getElementText(by);
        if (actualText.equals(expectedText)) {
            LogUtils.info("Verify text match: " + actualText);
            return true;
        } else {
            LogUtils.error("Verify text mismatch! Actual: " + actualText + " | Expected: " + expectedText);
            return false;
        }
    }

    /**
     * Verify if a checkbox or radio button is checked (selected)
     * @param by The locator of the element
     * @return true if selected, false otherwise.
     */
    public static boolean verifyElementChecked(By by){
        try{
            WebDriverWait wait = new WebDriverWait(getDriver(),
                    Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

            return getDriver().findElement(by).isSelected();
        } catch (Exception e) {
            LogUtils.warn("⚠️ Element not found or check failed: " + by);
            return false;
        }

    }
    public static boolean verifyPageTitle(String expectedTitle) {
        waitForPageLoaded();
        String actualTitle = getDriver().getTitle();
        if (actualTitle.equals(expectedTitle)) {
            LogUtils.info("Verify page title match: " + actualTitle);
            return true;
        } else {
            LogUtils.error("Verify page title mismatch! Actual: " + actualTitle + " | Expected: " + expectedTitle);
            return false;
        }
    }

    // --- PAGE LOAD WAIT---

    public static void waitForPageLoaded() {
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(FrameworkConstants.WAIT_PAGE_LOADED));

            // Wait for Javascript to complete
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));

            LogUtils.info("Page loaded successfully.");
        } catch (Throwable error) {
            LogUtils.error("Timeout waiting for Page Load Request to complete.");
        }
    }

    /**
     * Get a list of WebElements found by the locator.
     * Use this when you expect multiple elements (e.g., search results, table rows).
     * @param by The locator of the elements
     * @return A List of WebElements. Returns an empty list if no elements are found (does not return null).
     */
    public static List<WebElement> getWebElements(By by){
        try{
            WebDriverWait wait = new WebDriverWait(getDriver(),
                    Duration.ofSeconds(FrameworkConstants.WAIT_EXPLICIT));

            // Wait for at least one element to be present in the DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(by));

            return getDriver().findElements(by);
        } catch (Exception e){
            LogUtils.warn("⚠️ No elements found or timeout waiting for locator: " + by);
            // Return an empty list to avoid NullPointerException in the test
            return new ArrayList<>();
        }
    }
}
