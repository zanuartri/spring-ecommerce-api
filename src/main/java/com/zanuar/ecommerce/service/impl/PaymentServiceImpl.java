package com.zanuar.ecommerce.service.impl;

import com.zanuar.ecommerce.service.PaymentService;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean pay(Long orderId) {
        // MOCK STRIPE PAYMENT
        // simulate always success
        System.out.println("Processing payment for order " + orderId);
        return true;
    }
}
