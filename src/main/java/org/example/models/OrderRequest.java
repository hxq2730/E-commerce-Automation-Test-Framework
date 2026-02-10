package org.example.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class OrderRequest {
    private int owner_id;
    private int user_id;
    private int shipping_address_id;
    private String payment_type;
    private String payment_status;
    private String delivery_status;
    private String checkout_type;
    private String coupon_code;
    private double coupon_discount;
    private double grand_total;
}
