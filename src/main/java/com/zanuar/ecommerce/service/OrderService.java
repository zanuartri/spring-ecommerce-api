package com.zanuar.ecommerce.service;

import com.zanuar.ecommerce.dto.request.CreateOrderRequest;

public interface OrderService {

    Long createOrder(CreateOrderRequest request);
}
