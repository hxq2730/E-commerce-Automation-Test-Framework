package org.example.pages;

import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.openqa.selenium.By;

public class OrderHistoryPage extends CommonPage {
    private static final By FIRST_ORDER_CODE = By.xpath("//tbody/tr[1]/td[1]");

    public String getLatestOrderCode(){
        WebUI.openURL("https://cms.anhtester.com/purchase_history");

        String actualOrderCode = WebUI.getElementText(FIRST_ORDER_CODE);
        LogUtils.info("Latest Order in History: " + actualOrderCode);

        return actualOrderCode;
    }
}
