package org.example.tests;

import org.example.base.BaseTest;
import org.example.constants.FrameworkConstants;
import org.example.helpers.WebUI;
import org.example.utils.LogUtils;
import org.testng.annotations.Test;

public class SmokeTest extends BaseTest {

    @Test
    public void testOpenPage() {
        LogUtils.info("Starting Smoke Test...");

        // Open URL from Config
        WebUI.openURL(FrameworkConstants.URL_CMS);

        // Simple assertion to verify Log & Report
        WebUI.sleep(2);
    }
}