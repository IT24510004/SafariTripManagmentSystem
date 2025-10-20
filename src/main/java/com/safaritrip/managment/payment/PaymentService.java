package com.safaritrip.management.payment;


import com.safaritrip.management.model.PaymentInfo;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentStrategyFactory factory;

    public PaymentService(PaymentStrategyFactory factory) {
        this.factory = factory;
    }

    public boolean processPayment(String method, PaymentInfo info) {
        PaymentStrategy strategy = factory.getStrategy(method);
        return strategy.pay(info);
    }
}
