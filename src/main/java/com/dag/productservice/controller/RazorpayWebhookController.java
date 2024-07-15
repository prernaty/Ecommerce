package com.dag.productservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks/razorpay")
public class RazorpayWebhookController {

    @PostMapping
    public void handleWebhookEvent() {
        System.out.println("Hi");
    }
}
