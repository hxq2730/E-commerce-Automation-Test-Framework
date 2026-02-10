package org.example.models;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class CartResponse {
    private String name;
    private int owner_id;
    private List<CartItem> cart_items;
}
