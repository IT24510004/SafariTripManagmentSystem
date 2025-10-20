package com.safaritrip.management.payment;


import com.safaritrip.management.model.PaymentInfo;
import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalPayment implements PaymentStrategy {

    @Override
    public boolean pay(PaymentInfo info) {
        System.out.println("💰 Processing PayPal payment for: " + info.getAmount());
        return true;
    }

    @Override
    public String getName() {
        return "PayPal";
    }
}
