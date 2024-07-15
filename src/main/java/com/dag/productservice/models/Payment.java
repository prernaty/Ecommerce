package com.dag.productservice.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment extends V0 {
    @Column(name = "amount")
    private Long amount;

    @Column(name="status")
    private PaymentStatus paymentStatus;

    @Column(name="user_id")
    private Long userId;

    @Column(name="order_id")
    private String orderId;

    @Column(name="payment_link")
    private String paymentLink;

    @Column(name="payment_ref")
    private String paymentGatewayReferenceId;

}
