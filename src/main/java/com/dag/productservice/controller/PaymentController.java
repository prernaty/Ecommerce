package com.dag.productservice.controller;


import com.dag.productservice.dto.CreatePaymentLinkRequestDto;
import com.dag.productservice.dto.CreatePaymentLinkResponseDto;
import com.dag.productservice.models.PaymentStatus;
import com.dag.productservice.services.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping()
    public CreatePaymentLinkResponseDto createPaymentLink(@RequestBody CreatePaymentLinkRequestDto request) {

            String redirectUrl = this.paymentService.createPaymentLink(request);

            CreatePaymentLinkResponseDto response = new CreatePaymentLinkResponseDto();
            response.setUrl(redirectUrl);

        return response;
    }

    @GetMapping("/{id}")
    public PaymentStatus checkPaymentStatus(@PathVariable("id") String paymentGatewayPaymentId) {
        return this.paymentService.getPaymentStatus(paymentGatewayPaymentId);
    }

}
