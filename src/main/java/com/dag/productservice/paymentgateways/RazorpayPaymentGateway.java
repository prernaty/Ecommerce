package com.dag.productservice.paymentgateways;

import com.dag.productservice.dto.PaymentLinkResponse;
import com.dag.productservice.models.PaymentStatus;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class RazorpayPaymentGateway implements PaymentGatewayInterface{
    private RazorpayClient razorpayClient;

    public RazorpayPaymentGateway(RazorpayClient razorpayClient) throws RazorpayException {
        this.razorpayClient = razorpayClient;
    }

    @Override
    public PaymentLinkResponse createPaymentLink(Long amount, String userName, String userEmail, String userPhone, String orderId) {
        JSONObject paymentLinkRequest = new JSONObject();
        paymentLinkRequest.put("amount",amount);
        paymentLinkRequest.put("currency","INR");
        paymentLinkRequest.put("accept_partial",false);
        paymentLinkRequest.put("expire_by",System.currentTimeMillis()/1000 + 30 * 60); // epoch timestamp
        paymentLinkRequest.put("reference_id",orderId.toString());
        JSONObject customer = new JSONObject();
        customer.put("name",userPhone);
        customer.put("contact",userName);
        customer.put("email",userEmail);
        paymentLinkRequest.put("customer",customer);
        JSONObject notify = new JSONObject();
        notify.put("sms",true);
        notify.put("email",true);
        paymentLinkRequest.put("reminder_enable",true);
        paymentLinkRequest.put("callback_url","https://scaler.com/");
        paymentLinkRequest.put("callback_method","get");

        PaymentLink payment = null;

        try {
            payment = razorpayClient.paymentLink.create(paymentLinkRequest);
        } catch (RazorpayException razorpayException) {
            System.out.println(razorpayException);
            System.out.printf("Something went wrong");
        }

        PaymentLinkResponse paymentLinkResponse=new PaymentLinkResponse();
        paymentLinkResponse.setShortUrl(payment.get("short_url"));
        paymentLinkResponse.setReferenceId(payment.get("id"));
        paymentLinkResponse.setPaymentStatus(payment.get("status"));
        return paymentLinkResponse;
    }

    @Override
    public PaymentStatus getPaymentStatus(String paymentId) {
        PaymentLink payment = null;

        try {
            payment = razorpayClient.paymentLink.fetch(paymentId);
        } catch (RazorpayException razorpayException) {
            System.out.println(razorpayException);
            System.out.println("Something went wrong");
        }

        String paymentStatus = payment.get("status");


        if (paymentStatus.equals("captured")) {
            return PaymentStatus.SUCCESS;
        } else if (paymentStatus.equals("failed")) {
            return PaymentStatus.FAILURE;
        }
        return null;
    }
}
