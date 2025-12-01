package org.example.helpers;

import io.qameta.allure.Allure;
import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.utils.LogUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CaptureHelpers {
    // 1. Capture Screenshot to File (Save in /screenshots folder)
    public static void captureScreenshot(String screenshotName){

        try{
            if(DriverManager.getDriver() == null){
                LogUtils.error("Driver is null, can't take screenshot");
                return;
            }
            // Create timestamp to avoid overwriting files
            String dateName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = screenshotName + "_" + dateName + ".png";

            //Define location
            File screenshotDir = new File(FrameworkConstants.PROJECT_PATH + File.separator + "screenshots");
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }

            File source = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            File destination = new File(screenshotDir, fileName);

            FileHandler.copy(source, destination);
            LogUtils.info("Screenshot taken: " + destination.getAbsolutePath());
        } catch (Exception e){
            LogUtils.error("Exception while taking screenshot: " + e.getMessage());
        }
    }

    // 2. Attach Screenshot to Allure Report (Byte Array)
    public static void attachScreenshotToAllure(String screenshotName) {
        try {
            if (DriverManager.getDriver() != null) {
                byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment(screenshotName, new ByteArrayInputStream(screenshotBytes));
                LogUtils.info("Screenshot attached to Allure Report: " + screenshotName);
            }
        } catch (Exception e) {
            LogUtils.error("Cannot attach screenshot to Allure: " + e.getMessage());
        }
    }
}
