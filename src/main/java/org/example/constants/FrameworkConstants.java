package org.example.constants;

import org.example.helpers.PropertiesHelpers;

import java.io.File;

public class FrameworkConstants {
    // Project Path
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    // Resources Path
    public static final String RESOURCES_PATH =
            PROJECT_PATH + File.separator + "src" + File.separator + "test" + File.separator + "resources";

    // Data Files Path
    public static final String EXCEL_DATA_FILE_PATH =
            RESOURCES_PATH + File.separator + "dataproviders" + File.separator + "LoginData.xlsx";
    public static final String PROPERTIES_FILE_PATH = RESOURCES_PATH + File.separator + "config" + File.separator +
            "config.properties";

    // Browser Info (Read from Config)
    public static final String BROWSER = PropertiesHelpers.getValue("browser");
    public static final String URL_CMS = PropertiesHelpers.getValue("url");
    // Timeouts
    public static final int WAIT_EXPLICIT = 10;
    public static final int WAIT_PAGE_LOADED = 30;
    public static final int WAIT_SLEEP_STEP = 3;
}
