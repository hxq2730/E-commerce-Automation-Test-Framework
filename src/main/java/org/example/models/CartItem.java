package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItem {
    private int id;
    private int owner_id;
    private int user_id;
    private int product_id;
    private String product_name;
    private String product_thumbnail_image;
    private Object variation;
    private double price;
    private String currency_symbol;
    private double tax;
    private double shipping_cost;
    private int quantity;
    private int lower_limit;
    private int upper_limit;
}
