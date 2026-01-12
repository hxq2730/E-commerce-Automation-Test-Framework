package org.example.tests;

import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.helpers.ExcelHelpers;
import org.example.pages.AddProductPage;
import org.example.pages.AdminDashboardPage;
import org.example.pages.LoginPage;
import org.example.utils.LogUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AddProductTest extends BaseTest {
    private LoginPage loginPage;
    private AdminDashboardPage adminDashboardPage;
    private AddProductPage addProductPage;
    private ExcelHelpers excelHelpers;

    @DataProvider(name = "getCreateProductData")
    public Object[][] getCreateProductData() {
        excelHelpers = new ExcelHelpers();
        String filePath = FrameworkConstants.EXCEL_DATA_FILE_PATH + "ProductData.xlsx";
        return excelHelpers.getDataHashTable(filePath, "AddProduct", 1, 0);
    }

    @Test(dataProvider = "getCreateProductData", priority = 1, groups = "smoke", description =
            "Test add new product")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateProduct(Hashtable<String, String> data) {

        loginPage = new LoginPage();
        loginPage.openLoginPage();

        adminDashboardPage = loginPage.loginAdminSuccess(FrameworkConstants.DEFAULT_ADMIN_EMAIL,
                FrameworkConstants.DEFAULT_ADMIN_PASSWORD);

        boolean isLoaded = adminDashboardPage.verifyAdminDashboardLoaded();
        Assert.assertTrue(isLoaded, "FAIL: Admin Dashboard Page is not visible. Login might have " +
                "failed.");
        LogUtils.info("✅ Login successfully");

        adminDashboardPage.openAddNewProductPage();
        addProductPage = new AddProductPage();
        addProductPage.enterCreateProductData(data.get("ProductName"), data.get("Category"),
                data.get("Brand"), data.get("Unit"), data.get("MinimumPurchaseQty"),
                data.get("Tags"), data.get("UnitPrice"), data.get("Discount"),
                data.get("Quantity"), data.get("Description"));

        String successMessage = addProductPage.getSuccessMessage();
        String expectedMessage = "Product has been inserted successfully";
        Assert.assertNotNull(successMessage, "FAIL: Success message is null. Create product fail");
        Assert.assertEquals(successMessage, expectedMessage, "FAIL: Success message " +
                "content is incorrect. Found: " + successMessage);
    }
}
