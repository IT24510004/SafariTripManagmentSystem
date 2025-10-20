package com.safaritrip.management.payment;


import com.safaritrip.management.model.PaymentInfo;
import org.springframework.stereotype.Component;

@Component("creditCard")
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public boolean pay(PaymentInfo info) {
        System.out.println("💳 Processing credit card payment for: " + info.getAmount());
        return true;
    }

    @Override
    public String getName() {
        return "Credit Card";
    }
}
