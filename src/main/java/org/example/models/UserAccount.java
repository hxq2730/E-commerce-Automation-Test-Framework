package org.example.models;

import com.github.javafaker.Faker;
import lombok.Getter;

public class UserAccount {
    @Getter
    private final String name;
    @Getter
    private final String email;
    @Getter
    private final String password;
    @Getter
    private final String password_confirmation;
    @Getter
    private final String phone;

    public UserAccount(){
        Faker faker = new Faker();
        this.name = "Auto Test " + faker.name().firstName();
        this.email = "auto_tester_" + System.currentTimeMillis() + "@gmail.com";
        this.password = "123456";
        this.password_confirmation = "123456";
        this.phone = "09" + faker.number().digits(8);
    }

    public UserAccount(String name, String email, String password, String password_confirmation, String phone){
        this.name = name;
        this.email = email;
        this.password = password;
        this.password_confirmation = password_confirmation;
        this.phone = phone;
    }

}
