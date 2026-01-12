package org.example.tests;

import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.helpers.ExcelHelpers;
import org.example.pages.CustomerDashboardPage;
import org.example.pages.ForgotPasswordPage;
import org.example.pages.LoginPage;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class LoginTest extends BaseTest {

    //Declare Page Objects
    private LoginPage loginPage;
    private CustomerDashboardPage customerDashboardPage;
    private ForgotPasswordPage forgotPasswordPage;
    private ExcelHelpers excelHelpers;

    // --- DATA PROVIDER SETUP ---
    // This method reads data from "Login" sheet in Excel
    @DataProvider(name = "getDataLoginCustomer")
    public Object[][] getDataLoginCustomer() {
        excelHelpers = new ExcelHelpers();
        String filePath = FrameworkConstants.EXCEL_DATA_FILE_PATH + "UserManagementData.xlsx";
        return excelHelpers.getDataHashTable(filePath, "Login", 1, 0);
    }


    @Test(priority = 1, dataProvider = "getDataLoginCustomer", groups = "smoke")
    public void testLoginFlows(Hashtable<String, String> data) {
        loginPage = new LoginPage();
        customerDashboardPage = new CustomerDashboardPage();

        String testCaseName = data.get("TEST_CASE_NAME");
        String email = data.get("EMAIL");
        String password = data.get("PASSWORD");
        String expectedType = data.get("EXPECTED_TYPE");
        String expectedMessage = data.get("EXPECTED_MESSAGE");
        String runMode = data.get("RUN_MODE");

        if (testCaseName == null || testCaseName.trim().isEmpty() || expectedType == null || expectedType.trim().isEmpty()) {
            LogUtils.warn("Detected an empty or invalid row in Excel data. Skipping this test " +
                    "iteration.");
            return;
        }

        if (runMode.equals("N")) throw new SkipException("Skipping test: " + testCaseName);

        LogUtils.info("Running Test Case: " + testCaseName);

        loginPage.openLoginPage();
        loginPage.performLogin(email, password);

        switch (expectedType) {
            case "pass":
                LogUtils.info("Verify Step: Expecting Login Success");
                boolean isDashboardLoaded = customerDashboardPage.isDashboardLoaded();
                Assert.assertTrue(isDashboardLoaded, "FAIL: Login success but Dashboard is NOT " +
                        "visible.");
                break;
            case "fail_alert":
                LogUtils.info("Verify Step: Expecting Error Alert (Toast)");
                boolean isAlertDisplayed = loginPage.isErrorAlertDisplayed(expectedMessage);
                Assert.assertTrue(isAlertDisplayed, "FAIL: Error Alert not displayed or content " +
                        "mismatch. Expected: " + expectedMessage);
                break;
            case "fail_html5":
                LogUtils.info("Verify Step: Expecting HTML5 Validation Message");
                boolean isHtml5Displayed =
                        loginPage.isHTML5ValidationMessageDisplayed(expectedMessage);
                Assert.assertTrue(isHtml5Displayed,
                        "FAIL: HTML5 validation message does not contain expected keyword. " +
                                "Expected contains: " + expectedMessage);
                break;
            case "fail_text":
                LogUtils.info("Verify Step: Expecting Text Error under Input");
                boolean isTextDisplayed = loginPage.isInputErrorTextDisplayed(expectedMessage);
                Assert.assertTrue(isTextDisplayed,
                        "FAIL: Error text under input is NOT displayed. Expected: " + expectedMessage);
                break;
            default:
                throw new IllegalArgumentException("Invalid EXPECTED_TYPE in Excel: " + expectedType);
        }
    }

    @Test(priority = 2, description = "Verify login fail with invalid credentials")
    public void testLoginFail() {
        loginPage = new LoginPage();
        loginPage.openLoginPage();
        loginPage.loginFail("wrongemail@gmail.com", "wrong_password");


        String expectedMsg = "Invalid login credentials";

        // Call the boolean method from Page Object
        boolean isMsgDisplayed = loginPage.isErrorAlertDisplayed(expectedMsg);

        // Assert True if message displayed
        Assert.assertTrue(isMsgDisplayed, "FAIL: Error message '" + expectedMsg + "' did not " +
                "appear!");
    }

    @Test(priority = 2, description = "Verify Forgot Password Link")
    public void testForgotPasswordLink() {
        loginPage = new LoginPage();
        forgotPasswordPage = new ForgotPasswordPage();

        // 1. Open Login Page
        loginPage.openLoginPage();

        // 2. Click Forgot Password
        loginPage.clickForgotPassword();

        // 3. Verify URL
        // We expect the URL to contain "password/reset"
        String currentUrl = DriverManager.getDriver().getCurrentUrl();
        LogUtils.info("Current URL after click: " + currentUrl);

        Assert.assertTrue(currentUrl.contains("/password/reset"), "FAIL: The URL does not contain" +
                " 'password/reset'. Current URL: " + currentUrl);
        boolean isLoaded = forgotPasswordPage.isForgetPasswordPageLoaded();
        Assert.assertTrue(isLoaded, "FAIL: Reset Page is not visible." +
                ".");
    }

    @Test(priority = 3, description = "Verify login functionality using ENTER key")
    public void testLoginWithEnterKey() {
        loginPage = new LoginPage();
        customerDashboardPage = new CustomerDashboardPage();

        loginPage.openLoginPage();

        // Use a valid account for this test
        loginPage.loginWithEnterKey("test@gmail.com", "12345678");

        // Verify Dashboard loads
        Assert.assertTrue(customerDashboardPage.isDashboardLoaded(),
                "FAIL: Login with ENTER key failed. Dashboard not loaded.");
    }


//    @Test(priority = 1, description = "Verify login success with valid credentials",
//            dataProvider = "getDataLoginCustomer")
//    public void testLoginSuccess(Hashtable<String, String> data) {
//        //1. Initialize Page Object
//        loginPage = new LoginPage();
//
//        //2. Navigate to Login Page
//        loginPage.openLoginPage();
//
//        //3. Perform Login using data from Excel
//        customerDashboardPage = loginPage.loginSuccess(data.get("EMAIL"), data.get("PASSWORD"));
//
//        // 4. Verify Customer Dashboard is loaded
//        boolean isLoaded = customerDashboardPage.isDashboardLoaded();
//        Assert.assertTrue(isLoaded, "FAIL: Dashboard page is not visible. Login might have failed" +
//                ".");
//
//        LogUtils.info("✅ Login Test Passed for user: " + data.get("email"));
//    }
}
