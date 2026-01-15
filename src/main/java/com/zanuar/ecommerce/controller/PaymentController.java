package com.zanuar.ecommerce.controller;

import com.zanuar.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final OrderService orderService;

    @PostMapping("/{orderId}")
    public String pay(@PathVariable Long orderId) {
        orderService.payOrder(orderId);
        return "Payment success";
    }
}
