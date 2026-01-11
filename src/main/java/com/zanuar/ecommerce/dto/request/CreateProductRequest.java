package com.zanuar.ecommerce.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;

    @NotNull
    private Long categoryId;
}
