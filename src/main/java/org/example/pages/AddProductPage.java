package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

import java.util.List;

public class AddProductPage extends CommonPage{
    // --- LOCATORS ----
    private static final By INPUT_PRODUCT_NAME = By.xpath("//input[@name='name']");
    private static final By DROPDOWN_CATEGORY_TRIGGER = By.xpath("//div[@id='category']//button" +
            "[@data-id='category_id']");
    private static final By INPUT_SEARCH_CATEGORY = By.xpath("//div[@id='category']//input[@type" +
            "='search']");
    private String STR_CATEGORY_RESULT = "//div[@id='category']//span[normalize-space()" +
            "='%s']/parent::a";

    private static final By DROPDOWN_BRAND_TRIGGER = By.xpath("//div[@id='brand']//button" +
            "[@data-id='brand_id']");
    private static final By INPUT_SEARCH_BRAND = By.xpath("//div[@id='brand']//input[@type" +
            "='search']");
    private String STR_BRAND_RESULT = "//div[@id='brand']//span[normalize-space()" +
            "='%s']/parent::a";

    private static final By INPUT_UNIT = By.xpath("//input[@name='unit']");
    private static final By INPUT_MINIMUM_PURCHASE_QTY = By.xpath("//input[@name='min_qty']");
    private static final By INPUT_TAGS = By.xpath("//tags[contains(@class,'tagify')]//span[@contenteditable='']");
    private static final By INPUT_UNIT_PRICE = By.xpath("//input[@name='unit_price']");
    private static final By INPUT_DISCOUNT = By.xpath("//input[@name='discount']");
    private static final By INPUT_QUANTITY = By.xpath("//input[@name='current_stock']");
    private static final By INPUT_DESCRIPTION = By.xpath("//div[@role='textbox']");
    private static final By BUTTON_SAVE_AND_PUBLISH = By.xpath("//button[@value='publish']");
    private static final By ALERT_SUCCESS = By.xpath("//span[normalize-space()='Product has been " +
            "inserted successfully']");

    public AddProductPage() {}

    // ---ACTIONS---
    public void enterCreateProductData(String productName, String category, String brand,
                                      String unit, String minimumPurchaseQty, String tags,
                                      String unitPrice, String discount, String quantity,
                                      String description){
        WebUI.waitForPageLoaded();
        LogUtils.info("Starting create a new product: " + productName);

        WebUI.setText(INPUT_PRODUCT_NAME, productName);

        // category
        WebUI.clickElement(DROPDOWN_CATEGORY_TRIGGER);
        WebUI.setText(INPUT_SEARCH_CATEGORY, category);
        By categoryResult = By.xpath(String.format(STR_CATEGORY_RESULT, category));
        WebUI.clickElement(categoryResult);

        // brand
        WebUI.clickElement(DROPDOWN_BRAND_TRIGGER);
        WebUI.setText(INPUT_SEARCH_BRAND, brand);
        By brandResult = By.xpath(String.format(STR_BRAND_RESULT, brand));
        WebUI.clickElement(brandResult);

        // unit, minimum Purchase Qty
        WebUI.setText(INPUT_UNIT, unit);
        WebUI.setText(INPUT_MINIMUM_PURCHASE_QTY, minimumPurchaseQty);

        // tags
        String[] tagList = tags.split(",");
        for (String tag:tagList){
            WebUI.setText(INPUT_TAGS, tag);
            WebUI.pressEnter(INPUT_TAGS);
        }

        WebUI.scrollToElement(INPUT_UNIT_PRICE);
        //
        WebUI.setText(INPUT_UNIT_PRICE, unitPrice);
        WebUI.setText(INPUT_DISCOUNT, discount);
        WebUI.setText(INPUT_QUANTITY, quantity);

        //
        WebUI.scrollToElement(INPUT_DESCRIPTION);
        WebUI.setText(INPUT_DESCRIPTION, description);

        // click save & publish button
        WebUI.scrollToElement(BUTTON_SAVE_AND_PUBLISH);
        WebUI.clickElement(BUTTON_SAVE_AND_PUBLISH);

        LogUtils.info("Create product workflows end!");

    }

    public String getSuccessMessage(){
        try {
            WebUI.waitForElementVisible(ALERT_SUCCESS);
            String msg = WebUI.getElementText(ALERT_SUCCESS);
            LogUtils.info("A confirming message for successful product creation was found.");
            return msg;
        } catch (Exception e) {
            LogUtils.error("A confirming message for successful product creation was not found.");
            return null;
        }
    }
}
