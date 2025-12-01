package org.example.helpers;

import java.io.FileInputStream;
import java.util.Properties;

public class PropertiesHelpers {
    private static Properties properties;
    private static String linkFile;
    private static FileInputStream file;


    //Init and load file config
    public static Properties loadAllFiles(){
        properties = new Properties();
        try{
            linkFile = System.getProperty("user.dir") + "/src/test/resources/config.properties";
            file = new FileInputStream(linkFile);
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
