package org.example.helpers;

import org.example.constants.FrameworkConstants;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesHelpers {
    private static Properties properties;

    //Init and load file config
    public static Properties loadAllFiles(){
        properties = new Properties();
        try{
            FileInputStream file = new FileInputStream(FrameworkConstants.PROPERTIES_FILE_PATH);
            properties.load(file);
            file.close();

            return properties;
        }
        catch (Exception e){
            e.printStackTrace();
            return new Properties();
        }
    }

    //Get value by key
    public static String getValue(String key){
        if(properties == null){
            properties = loadAllFiles();
        }

        try {
            return properties.getProperty(key);
        } catch (Exception e){
            System.out.println("Not found value of key: " + key);
            return null;
        }
    }
}
