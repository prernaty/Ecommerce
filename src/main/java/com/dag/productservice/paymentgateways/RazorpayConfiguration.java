package com.dag.productservice.paymentgateways;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfiguration {

    @Value("${razorpay.key_id}")
    private String razorPayKey;

    @Value("${razorpay.key_secret}")
    private String razorPaySecret;

    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient(razorPayKey, razorPaySecret);
    }
}
