package com.safaritrip.management.payment;


import com.safaritrip.management.model.PaymentInfo;
import org.springframework.stereotype.Component;

@Component("bankTransfer")
public class BankTransferPayment implements PaymentStrategy {

    @Override
    public boolean pay(PaymentInfo info) {
        System.out.println("🏦 Processing Bank Transfer for: " + info.getAmount());
        return true;
    }

    @Override
    public String getName() {
        return "Bank Transfer";
    }
}
