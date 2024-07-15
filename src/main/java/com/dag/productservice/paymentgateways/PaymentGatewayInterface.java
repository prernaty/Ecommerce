package com.dag.productservice.paymentgateways;

import com.dag.productservice.models.PaymentStatus;

public interface PaymentGatewayInterface {

    String createPaymentLink(
            Long amount,
            String userName,
            String userEmail,
            String userPhone,
            String orderId
    );

    PaymentStatus getPaymentStatus(
            String paymentId
    );
}
