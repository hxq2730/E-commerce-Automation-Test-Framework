package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartQuantityRequest {
    private int id;          // cart_id
    private String product_id; // product_id
    private int quantity;

    public CartQuantityRequest(int id, String product_id, int quantity) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

}
