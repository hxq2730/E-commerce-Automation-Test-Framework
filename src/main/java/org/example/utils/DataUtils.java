package org.example.utils;

public class DataUtils {
    public static String getEmailWithTimestamp(String email) {
        if (email == null || !email.contains("@")) {
            return email;
        }
        String timestamp = String.valueOf(System.currentTimeMillis());
        int atIndex = email.indexOf("@");
        String prefix = email.substring(0, atIndex);
        String domain = email.substring(atIndex);
        return prefix + "_" + timestamp + domain;
    }
}