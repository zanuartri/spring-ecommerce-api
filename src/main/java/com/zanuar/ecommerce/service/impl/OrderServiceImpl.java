package com.zanuar.ecommerce.service.impl;

import com.zanuar.ecommerce.domain.Order;
import com.zanuar.ecommerce.domain.OrderItem;
import com.zanuar.ecommerce.domain.Product;
import com.zanuar.ecommerce.domain.User;
import com.zanuar.ecommerce.domain.entity.OrderStatus;
import com.zanuar.ecommerce.dto.request.CreateOrderRequest;
import com.zanuar.ecommerce.dto.request.OrderItemRequest;
import com.zanuar.ecommerce.exception.BadRequestException;
import com.zanuar.ecommerce.exception.ResourceNotFoundException;
import com.zanuar.ecommerce.repository.OrderItemRepository;
import com.zanuar.ecommerce.repository.OrderRepository;
import com.zanuar.ecommerce.repository.ProductRepository;
import com.zanuar.ecommerce.repository.UserRepository;
import com.zanuar.ecommerce.service.OrderService;
import com.zanuar.ecommerce.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentService paymentService;

    @Override
    @Transactional
    public Long createOrder(CreateOrderRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

            if (product.getStock() < itemRequest.getQuantity()) {
                throw new BadRequestException("Insufficient stock for product: " + product.getName());
            }

            // reduce stock
            product.setStock(product.getStock() - itemRequest.getQuantity());

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice()); // snapshot price

            BigDecimal itemTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(itemRequest.getQuantity()));

            totalAmount = totalAmount.add(itemTotal);
            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        Order savedOrder = orderRepository.save(order);

        orderItems.forEach(item -> item.setOrder(savedOrder));
        orderItemRepository.saveAll(orderItems);

        return savedOrder.getId();
    }

    @Override
    @Transactional
    public void payOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));


        if (order.getStatus() != OrderStatus.CREATED) {
            throw new BadRequestException("Order cannot be paid");

        }

        boolean paymentSuccess = paymentService.pay(orderId);

        if (!paymentSuccess) {
            throw new BadRequestException("Payment failed");
        }

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }

}
