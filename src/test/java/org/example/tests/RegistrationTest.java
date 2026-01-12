package org.example.tests;

import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.helpers.ExcelHelpers;
import org.example.pages.HomePage;
import org.example.pages.RegisterPage;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class RegistrationTest extends BaseTest {
    private RegisterPage registerPage;
    private HomePage homePage;
    private ExcelHelpers excelHelpers;

    @DataProvider(name = "getRegisterData")
    public Object[][] getRegisterData() {
        excelHelpers = new ExcelHelpers();
        String filePath = FrameworkConstants.EXCEL_DATA_FILE_PATH + "UserManagementData.xlsx";
        return excelHelpers.getDataHashTable(filePath, "Register", 1, 0);
    }

    @Test(dataProvider = "getRegisterData", description = "Test Login Flow")
    public void testRegisterFlow(Hashtable<String, String> data) {
        homePage = new HomePage();
        registerPage = new RegisterPage();

        String testCaseName = data.get("TestCaseName");
        String fullName = data.get("FullName");
        String email = data.get("Email");
        String password = data.get("Password");
        String confirmPassword = data.get("ConfirmPassword");
        String expectedType = data.get("ExpectedType");
        String expectedMessage = data.get("ExpectedMessage");
        String runMode = data.get("RunMode");

        boolean isAgree = true;
        if (data.containsKey("AgreeTerms")) {
            String agreeVal = data.get("AgreeTerms");
            if (agreeVal.equalsIgnoreCase("FALSE") || agreeVal.equalsIgnoreCase("N") || agreeVal.equalsIgnoreCase("NO")) {
                isAgree = false;
            }
        }

        if (testCaseName == null || testCaseName.trim().isEmpty() || expectedType == null || expectedType.trim().isEmpty()) {
            LogUtils.warn("Detected an empty or invalid row in Excel data. Skipping this test " +
                    "iteration.");
            return;
        }
        if (runMode.equals("N")) throw new SkipException("Skipping test: " + testCaseName);

        LogUtils.info("Running Test Case: " + testCaseName);

        homePage.openHomePage();
        registerPage = homePage.openRegisterPage();
        registerPage.createAnAccount(fullName, email, password, confirmPassword, isAgree);

        switch (expectedType) {
            case "pass": {
                LogUtils.info("Expecting Register Success");
                String msg = registerPage.getSuccessMessage();
                LogUtils.info("Msg: " + msg);
                Assert.assertEquals(msg, expectedMessage, "Fail: Registration error!");
                break;
            }
            case "fail_alert": {
                LogUtils.info("Expecting Error Alert (Toast)");
                boolean isAlertDisplayed = registerPage.isErrorAlertDisplayed(expectedMessage);
                Assert.assertTrue(isAlertDisplayed, "FAIL: Error Alert not displayed or content " +
                        "mismatch. Expected: " + expectedMessage);
                break;
            }
            case "fail_html5": {
                LogUtils.info("Verify Step: Expecting HTML5 Validation Message");
                boolean isHtml5Displayed =
                        registerPage.isHTML5ValidationMessageDisplayed(expectedMessage);
                Assert.assertTrue(isHtml5Displayed,
                        "FAIL: HTML5 validation message does not contain expected keyword. " +
                                "Expected contains: " + expectedMessage);
                break;
            }
            case "fail_text": {
                LogUtils.info("Verify Step: Expecting Text Error under Input");
                boolean isTextDisplayed = registerPage.isInputErrorTextDisplayed(expectedMessage);
                Assert.assertTrue(isTextDisplayed,
                        "FAIL: Error text under input is NOT displayed. Expected: " + expectedMessage);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid EXPECTED_TYPE in Excel: " + expectedType);
        }
    }
}
