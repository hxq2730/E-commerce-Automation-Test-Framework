package org.example.listeners;

import org.example.helpers.CaptureHelpers;
import org.example.utils.LogUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        LogUtils.info("=== STARTING TEST: " + result.getName() + " ===");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LogUtils.info("✅ TEST PASSED: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LogUtils.error("❌ TEST FAILED: " + result.getName());
        LogUtils.error("Error Message: " + result.getThrowable().getMessage());

        // 1. Capture Screenshot to File
        CaptureHelpers.captureScreenshot(result.getName());

        // 2. Attach Screenshot to Allure
        CaptureHelpers.attachScreenshotToAllure(result.getName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LogUtils.warn("⚠️ TEST SKIPPED: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        LogUtils.info("=== STARTING SUITE: " + context.getName() + " ===");
    }

    @Override
    public void onFinish(ITestContext context) {
        LogUtils.info("=== FINISHING SUITE: " + context.getName() + " ===");
    }
}
