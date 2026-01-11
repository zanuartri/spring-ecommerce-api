package com.zanuar.ecommerce.service;

import com.zanuar.ecommerce.dto.request.CreateProductRequest;
import com.zanuar.ecommerce.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse create(CreateProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAll();
}
