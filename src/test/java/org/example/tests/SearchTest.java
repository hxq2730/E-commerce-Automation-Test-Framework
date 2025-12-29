package org.example.tests;

import org.example.base.BaseTest;
import org.example.pages.HomePage;
import org.example.pages.LoginPage;
import org.example.pages.SearchPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest {

    //Declare Page Objects
    private HomePage homePage;
    private SearchPage searchPage;

    @Test(description = "Verify Search functionality")
    public void testSearchProduct(){
        // 1. Init HomePage
        homePage = new HomePage();

        // 2. Open Home Page
        homePage.openHomePage();

        // 3. Perform Search
        String keyword = "coca";
        searchPage = homePage.searchForProduct(keyword);

        // 4. Verify results
        String firstResult = searchPage.getFirstResultName();
        Assert.assertNotNull(firstResult, "FAIL: Search result list is empty.");
        Assert.assertTrue(firstResult.toLowerCase().contains(keyword.toLowerCase()), "FAIL: First" +
                " result '" + firstResult + "' does not contain keyword '" + keyword + "'");
    }
}
