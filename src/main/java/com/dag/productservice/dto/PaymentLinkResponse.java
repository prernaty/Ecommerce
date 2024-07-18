package com.dag.productservice.dto;

import com.dag.productservice.models.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentLinkResponse {
    private String shortUrl;
    private String referenceId;
    private PaymentStatus paymentStatus;
}
