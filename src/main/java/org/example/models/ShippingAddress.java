package org.example.models;

import com.github.javafaker.Faker;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingAddress {
    private int id;
    private String address;
    private int country_id;
    private int state_id;
    private int city_id;
    private String postal_code;
    private String phone;

    public ShippingAddress() {
        Faker faker = new Faker();
        this.address = faker.address().streetAddress();
        this.country_id = 238;
        this.state_id = 4124;
        this.city_id = 48361;
        this.postal_code = faker.number().digits(5);
        this.phone = "09" + faker.number().digits(8);
    }

    public ShippingAddress(String address, int country_id, int state_id, int city_id, String postalCode, String phone) {
        this.address = address;
        this.country_id = country_id;
        this.state_id = state_id;
        this.city_id = city_id;
        this.postal_code = postalCode;
        this.phone = phone;
    }
}
