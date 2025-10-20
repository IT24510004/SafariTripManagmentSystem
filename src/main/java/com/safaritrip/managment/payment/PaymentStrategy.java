package com.safaritrip.management.payment;

import com.safaritrip.management.model.PaymentInfo;

public interface PaymentStrategy {
    // Executes the payment process
    boolean pay(PaymentInfo info);

    // Returns a descriptive name of the payment type
    String getName();
}
