package com.safaritrip.management.controller;


import com.safaritrip.management.model.PaymentInfo;
import com.safaritrip.management.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/process")
    public String processPayment(@RequestParam String method,
                                 @RequestBody PaymentInfo info) {
        boolean ok = service.processPayment(method, info);
        return ok ? "Payment processed via " + method : "Payment failed";
    }
}
