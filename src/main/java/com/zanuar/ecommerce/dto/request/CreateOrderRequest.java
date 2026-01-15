package com.zanuar.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CreateOrderRequest {

    @NotNull
    private Long userId;

    @NotNull
    private List<OrderItemRequest> items;
}
