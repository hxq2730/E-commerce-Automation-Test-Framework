package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {
    private int id;
    private String name;
    private String thumbnail_image;
    private String main_price;
    private String stroked_price;
    private int rating;
    private int sales;

    public double getPriceAsDouble() {
        if (this.main_price == null) return 0.0;

        String cleanPrice = this.main_price.replace("$", "").replace(",", "");

        try {
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing price: " + this.main_price);
            return 0.0;
        }
    }
}
