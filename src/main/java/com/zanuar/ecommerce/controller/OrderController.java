package com.zanuar.ecommerce.controller;

import com.zanuar.ecommerce.dto.request.CreateOrderRequest;
import com.zanuar.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Long create(@RequestBody @Valid CreateOrderRequest request) {
        return orderService.createOrder(request);
    }
}
