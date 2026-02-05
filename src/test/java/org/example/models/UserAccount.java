package org.example.models;

import com.github.javafaker.Faker;
import lombok.Getter;

public class UserAccount {
    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private String password_confirmation;
    @Getter
    private String phone;

    public UserAccount(){
        Faker faker = new Faker();
        this.name = "Auto Test " + faker.name().firstName();
        this.email = "auto_tester_" + System.currentTimeMillis() + "@gmail.com";
        this.password = "123456";
        this.password_confirmation = "123456";
        this.phone = "09" + faker.number().digits(8);
    }

}
