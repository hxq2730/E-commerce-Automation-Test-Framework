package org.example.constants;

import org.example.helpers.PropertiesHelpers;

import java.io.File;

public class FrameworkConstants {

    // PATH
    // Project Path
    public static final String PROJECT_PATH = System.getProperty("user.dir");

    // Resources Path
    public static final String RESOURCES_PATH =
            PROJECT_PATH + File.separator + "src" + File.separator + "test" + File.separator + "resources";

    // Data Files Path
    public static final String EXCEL_DATA_FILE_PATH =
            RESOURCES_PATH + File.separator + "testdata" + File.separator + "excel" + File.separator;
    public static final String PROPERTIES_FILE_PATH = RESOURCES_PATH + File.separator + "config" + File.separator +
            "config.properties";
    public static final String TRANSACTION_IMAGE_FILE_PATH =
            RESOURCES_PATH + File.separator + "testdata" + File.separator + "uploads" + File.separator +
            "receipt.jpg";

    // Default User for Testing
    public static final String DEFAULT_EMAIL = "test@gmail.com";
    public static final String DEFAULT_PASSWORD = "12345678";

    // Default Admin user for Testing
    public static final String DEFAULT_ADMIN_EMAIL = PropertiesHelpers.getValue("admin_email");
    public static final String DEFAULT_ADMIN_PASSWORD = PropertiesHelpers.getValue(
            "admin_password");

    // Browser Info (Read from Config)
    public static final String BROWSER = PropertiesHelpers.getValue("browser");
    public static final String URL_DEFAULT = PropertiesHelpers.getValue("url_default");
    public static final String URL_CMS_USER = PropertiesHelpers.getValue("url_user");
    public static final String URL_CMS_ADMIN = PropertiesHelpers.getValue("url_admin");

    // Timeouts
    public static final int WAIT_EXPLICIT = 10;
    public static final int WAIT_IMPLICIT = 10;
    public static final int WAIT_PAGE_LOADED = 30;
    public static final int WAIT_SLEEP_STEP = 3;
}
