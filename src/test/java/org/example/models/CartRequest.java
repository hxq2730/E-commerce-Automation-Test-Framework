package org.example.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private String id;
    private int quantity;

    public CartRequest(String id, int quantity){
        this.id = id;
        this.quantity = quantity;
    }
}
