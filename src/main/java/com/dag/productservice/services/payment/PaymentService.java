package com.dag.productservice.services.payment;

import com.dag.productservice.dao.PaymentRepository;
import com.dag.productservice.dto.CreatePaymentLinkRequestDto;
import com.dag.productservice.dto.PaymentLinkResponse;
import com.dag.productservice.models.Payment;
import com.dag.productservice.models.PaymentStatus;
import com.dag.productservice.paymentgateways.PaymentGatewayInterface;
import com.dag.productservice.paymentgateways.RazorpayPaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private final RazorpayPaymentGateway razorpayPaymentGateway;
    private PaymentRepository paymentRepository;

    public PaymentService(RazorpayPaymentGateway paymentGatewayFactory, PaymentRepository paymentRepository, RazorpayPaymentGateway razorpayPaymentGateway) {
        this.paymentRepository = paymentRepository;
        this.razorpayPaymentGateway = razorpayPaymentGateway;
    }

    public String createPaymentLink(CreatePaymentLinkRequestDto createPaymentLinkRequestDto) {

        Long amount = Long.valueOf(createPaymentLinkRequestDto.getAmount());
        String userName = createPaymentLinkRequestDto.getUsername();
        String userMobile = createPaymentLinkRequestDto.getPhone();
        String userEmail = createPaymentLinkRequestDto.getEmail();
        String orderId=createPaymentLinkRequestDto.getOrderId();

        PaymentGatewayInterface paymentGateway = razorpayPaymentGateway;

        PaymentLinkResponse paymentLinkResponse = paymentGateway.createPaymentLink(
                amount, userName, userEmail, userMobile, orderId
        );

        com.dag.productservice.models.Payment payment = new Payment();
        payment.setPaymentLink(paymentLinkResponse.getShortUrl());
        payment.setPaymentGatewayReferenceId(paymentLinkResponse.getReferenceId());
        payment.setOrderId(orderId);
        payment.setPaymentStatus(PaymentStatus.PENDING);
        payment.setAmount(amount);

        paymentRepository.save(payment);

        return paymentLinkResponse.getShortUrl();

    }

    public PaymentStatus getPaymentStatus(String paymentGatewayPaymentId) {

        com.dag.productservice.models.Payment payment = paymentRepository.findByOrderId(paymentGatewayPaymentId);
        PaymentGatewayInterface paymentGateway = null;
        paymentGateway = razorpayPaymentGateway;

        PaymentStatus paymentStatus = paymentGateway.getPaymentStatus(payment.getPaymentGatewayReferenceId());;

        payment.setPaymentStatus(paymentStatus);

        paymentRepository.save(payment);

        return paymentStatus;
    }
}