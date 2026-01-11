package com.zanuar.ecommerce.service.impl;

import com.zanuar.ecommerce.domain.Category;
import com.zanuar.ecommerce.domain.Product;
import com.zanuar.ecommerce.dto.request.CreateProductRequest;
import com.zanuar.ecommerce.dto.response.ProductResponse;
import com.zanuar.ecommerce.repository.CategoryRepository;
import com.zanuar.ecommerce.repository.ProductRepository;
import com.zanuar.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public ProductResponse create(CreateProductRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    @Override
    public ProductResponse getById(Long id) {
        return productRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .categoryName(product.getCategory().getName())
                .build();
    }
}
