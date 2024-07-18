package com.dag.productservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePaymentLinkRequestDto {
    private String orderId;
    private String username;
    private String phone;
    private String email;
    private String amount;
}
