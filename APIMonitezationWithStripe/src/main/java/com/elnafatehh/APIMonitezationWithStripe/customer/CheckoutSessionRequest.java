package com.elnafatehh.APIMonitezationWithStripe.customer;

import lombok.Data;

@Data
public class CheckoutSessionRequest {
    private Long id;
    private String email;
    private String priceId;
}
