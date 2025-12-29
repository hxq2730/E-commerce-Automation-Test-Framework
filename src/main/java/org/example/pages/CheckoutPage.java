package org.example.pages;

import org.example.constants.FrameworkConstants;
import org.example.driver.DriverManager;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class CheckoutPage extends CommonPage {

    //---LOCATORS---

    // Step 1: Shipping Info
    private static final By BUTTON_ADD_NEW_ADDRESS = By.cssSelector("div[onClick='add_new_address" +
            "()']");

    private static final By INPUT_ADDRESS = By.cssSelector("textarea[placeholder=\"Your " +
            "Address\"]");

    private static final By INPUT_POSTAL_CODE = By.cssSelector("input[placeholder=\"Your Postal " +
            "Code\"]");

    private static final By INPUT_PHONE = By.cssSelector("input[placeholder=\"+880\"]");

    private static final By BUTTON_SAVE = By.xpath("//button[normalize-space()=\"Save\"]");

    // Button to move to Step 2
    private static final By BUTTON_CONTINUE_TO_DELIVERY = By.xpath("//button[normalize-space()" +
            "=\"Continue to Delivery Info\"]");

    // Step 2: Delivery info
    private static final By DELIVERY_TYPE = By.cssSelector("input[value='home_delivery']");

    // Button to move to Step 3
    private static final By BUTTON_CONTINUE_TO_PAYMENT = By.xpath("//button[normalize-space()" +
            "='Continue to Payment']");

    // Step 3: Payment Section
    private static final By INPUT_ADDITIONAL_INFO = By.cssSelector("textarea[placeholder=\"Type " +
            "your text\"]");

    private static final By RADIO_VISA_ONLINE = By.xpath("//span[contains(text(), 'Online')" +
            "]/ancestor::label");

    private static final By INPUT_TRANSACTION_ID = By.id("trx_id");

    private static final By BUTTON_BROWSE_FILE = By.xpath("//div[contains(text(), 'Browse')]");

    private static final By MODAL_UPLOAD = By.xpath("//div[@id='aizUploaderModal']");

    private static final By TAB_UPLOAD_NEW = By.xpath("//a[contains(text(), 'Upload New')]");

    private static final By INPUT_FILE_IN_MODAL = By.xpath("//div[@id='aiz-upload-files']//input[@name='files[]']");

    private static final By BUTTON_ADD_FILES = By.xpath("//button[contains(text(), 'Add Files')]");

    private static final By CHECKBOX_AGREE_TERMS = By.xpath("//input[@id='agree_checkbox']/parent" +
            "::label");

    private static final By BUTTON_COMPLETE_ORDER = By.xpath("//button[normalize-space()" +
            "=\"Complete Order\"]");

    // Order Success Message
    private static final By MESSAGE_ORDER_CONFIRMED = By.xpath("//h1[contains(text(), 'Thank " +
            "You')]");

    private static final By TEXT_ORDER_CODE = By.xpath("//h2[contains(text(), 'Order Code')]");

    // Dynamic Dropdown Locators (Using String format)
    private String STR_BUTTON_DROPDOWN = "//select[@name='%s']/following-sibling::button";

    private String STR_INPUT_SEARCH = "//select[@name='%s']/parent::div//input" +
            "[@type='search']";

    //private String STR_ADDRESS_RADIO = "//label[contains(., '%s')]//input[@name='address_id']";
    private String STR_ADDRESS_RADIO = "//span[normalize-space()='%s']/ancestor::label";


    //---ACTIONS---

    /**
     * Perform the complete checkout process from Shipping to Order Completion.
     */
    public void checkoutSuccessfully(String address, String country, String state,
                                     String city, String postalCode, String phone,
                                     String additionInfo) {

        LogUtils.info("STARTING CHECKOUT WORKFLOW...");

        // Step 1: Shipping info
        WebUI.clickElement(BUTTON_ADD_NEW_ADDRESS);
        WebUI.waitForPageLoaded();
        fillShippingInformation(address, country, state, city, postalCode, phone);
        selectShippingAddress(address);

        // Move to Delivery
        clickContinueToDeliveryInfo();

        // Step 2: Delivery
        selectDeliveryType();
        clickContinueToPayment();

        // Step 3: Payment
        WebUI.setText(INPUT_ADDITIONAL_INFO, additionInfo);
        selectPaymentMethod();

        // Step 4: Click agree to policies
        clickAgreePolicies();

        // Step 5: Click complete order
        clickCompleteOrder();

        LogUtils.info("CHECKOUT WORKFLOW COMPLETED.");
    }

    /**
     * Step 1: Fill in the shipping information form.
     */
    public void fillShippingInformation(String address, String country, String state,
                                        String city, String postalCode, String phone) {

        LogUtils.info("Filling Shipping Information...");

        WebUI.setText(INPUT_ADDRESS, address);
        selectCountry(country);
        selectState(state);
        selectCity(city);
        WebUI.setText(INPUT_POSTAL_CODE, postalCode);
        WebUI.setText(INPUT_PHONE, phone);
        WebUI.clickElement(BUTTON_SAVE);

        WebUI.waitForPageLoaded();
    }

    /**
     * Explicitly select a shipping address from the list based on a keyword.
     *
     * @param addressKeyword Unique text to identify the address.
     */
    public void selectShippingAddress(String addressKeyword) {
        LogUtils.info("Select Shipping address containing " + addressKeyword);

        By addressRatio = By.xpath(String.format(STR_ADDRESS_RADIO, addressKeyword));
        WebUI.scrollToElement(addressRatio);

        // Check if not selected, then click
        if (!WebUI.verifyElementChecked(addressRatio)) {
            WebUI.clickElement(addressRatio);
            LogUtils.info("Address selected.");
        } else {
            LogUtils.info("Address was already auto-selected.");
        }
    }

    /**
     * Action: Click "Continue to Delivery Info"
     * Moves from Step 1 to Step 2
     */
    public void clickContinueToDeliveryInfo() {
        LogUtils.info("Clicking 'Continue to Delivery info' button");
        WebUI.scrollToElement(BUTTON_CONTINUE_TO_DELIVERY);
        WebUI.clickElement(BUTTON_CONTINUE_TO_DELIVERY);
        WebUI.waitForPageLoaded();
    }

    /**
     * Step 2: Explicitly select a Delivery Type from the list.
     */
    public void selectDeliveryType() {
        String deliveryType = DriverManager.getDriver().findElement(DELIVERY_TYPE).getText();
        LogUtils.info("Selecting Delivery Type " + deliveryType);

        if (!WebUI.verifyElementChecked(DELIVERY_TYPE)) {
            WebUI.clickElement(DELIVERY_TYPE);
            LogUtils.info(" Delivery Type selected.");
        } else {
            LogUtils.info(" Delivery Type was already auto-selected");
        }
    }

    /**
     * Action: Click "Continue to Payment"
     * Moves from Step 2 to Step 3
     */
    public void clickContinueToPayment() {
        LogUtils.info("Clicking 'Continue to Payment' button");
        WebUI.clickElement(BUTTON_CONTINUE_TO_PAYMENT);
        WebUI.waitForPageLoaded();
    }

    public void selectPaymentMethod() {
        LogUtils.info("Selecting Payment Method (VISA online).");
        WebUI.clickElement(RADIO_VISA_ONLINE);

        LogUtils.info("Fulfill Transaction ID");
        WebUI.setText(INPUT_TRANSACTION_ID, "1");

        LogUtils.info("👉 Clicking 'Browse' to open Media Manager...");
        WebUI.clickElement(BUTTON_BROWSE_FILE);

        WebUI.waitForElementVisible(MODAL_UPLOAD);

        try {
            WebUI.clickElement(TAB_UPLOAD_NEW);
        } catch (Exception e) {
        }

        LogUtils.info("Attachment transaction photo");
        String filePath = FrameworkConstants.TRANSACTION_IMAGE_FILE_PATH;

        LogUtils.info("Uploading file from path: " + filePath);

        WebUI.uploadFile(INPUT_FILE_IN_MODAL, filePath);
        WebUI.sleep(5);

        LogUtils.info("Clicking 'Add Files' to confirm.");
        WebUI.clickElement(BUTTON_ADD_FILES);

        WebUI.waitForElementInvisible(MODAL_UPLOAD);
    }

    public void clickAgreePolicies() {
        if (!WebUI.verifyElementChecked(CHECKBOX_AGREE_TERMS)) {
            WebUI.clickElement(CHECKBOX_AGREE_TERMS);
            LogUtils.info("Checked agree to the policies");
        } else {
            LogUtils.info("ℹ️ Checkbox was already auto-selected.");
        }
    }

    public void clickCompleteOrder() {
        LogUtils.info("Clicking 'Complete Order' button.");
        WebUI.clickElement(BUTTON_COMPLETE_ORDER);
        WebUI.waitForPageLoaded();
    }

    public String getOrderConfirmationMessage() {
        try {
            WebUI.waitForElementVisible(MESSAGE_ORDER_CONFIRMED);
            String msg = WebUI.getElementText(MESSAGE_ORDER_CONFIRMED);
            LogUtils.info("✅ Captured Order Success Message: " + msg);
            return msg;
        } catch (Exception e) {
            LogUtils.error("❌ Order confirmation message not found.");
            return null;
        }
    }

    /**
     * Generic method to handle Bootstrap Dropdowns (Country, State, City).
     * @param nameAttribute The 'name' attribute of the select tag (e.g., "country_id",
     *                      "state_id", "city_id")
     * @param valueToSelect The text value to search and select (e.g., "Vietnam")
     */
    public void selectOptionFromDropdown(String nameAttribute, String valueToSelect) {
        LogUtils.info("Selecting '" + valueToSelect + "' from dropdown: " + nameAttribute);

        // 1. Construct Dynamic XPaths
        By buttonLocator = By.xpath(String.format(STR_BUTTON_DROPDOWN, nameAttribute));
        By inputLocator = By.xpath(String.format(STR_INPUT_SEARCH, nameAttribute));

        // 2. Click to open dropdown
        WebUI.clickElement(buttonLocator);

        // 3. Type text into the SPECIFIC search box of this dropdown
        WebUI.setText(inputLocator, valueToSelect);

        // 4. Press Enter to select
        WebUI.pressEnter(inputLocator);
    }

    // Wrapper methods for cleaner Test usage
    public void selectCountry(String country) {
        selectOptionFromDropdown("country_id", country);
    }

    public void selectState(String state) {
        selectOptionFromDropdown("state_id", state);
    }

    public void selectCity(String city) {
        selectOptionFromDropdown("city_id", city);
    }

    //    //COUNTRY
//    // The Button that opens the dropdown select country
//    private static final By BUTTON_COUNTRY_DROPDOWN = By.xpath("//select[@name='country_id" +
//            "']/following-sibling::button");
//
//    // The Search Input inside the dropdown (Visible only after clicking the button)
//    private static final By INPUT_COUNTRY_SEARCH = By.xpath("//select[@name='country_id" +
//            "']/parent::div//input[@type='search']");

//    public void selectCountry(String countryName){
//        LogUtils.info("Selecting Country: " + countryName);
//
//        // 1. Click the dropdown button to expand the list
//        WebUI.clickElement(BUTTON_COUNTRY_DROPDOWN);
//
//        // 2. Type the country name into the search box
//        // Note: The search box appears inside the dropdown
//        WebUI.setText(INPUT_COUNTRY_SEARCH, countryName);
//
//        // 3. Press ENTER to select the filtered result
//        // Using Keys.ENTER is faster and more stable than clicking the <span> item
//        WebUI.pressEnter(INPUT_COUNTRY_SEARCH);
//
//        WebUI.sleep(1);
//    }
}
