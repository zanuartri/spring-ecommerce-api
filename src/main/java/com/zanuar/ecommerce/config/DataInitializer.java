package com.zanuar.ecommerce.config;

import com.zanuar.ecommerce.domain.Category;
import com.zanuar.ecommerce.domain.Product;
import com.zanuar.ecommerce.repository.CategoryRepository;
import com.zanuar.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }

        Category electronics = new Category();
        electronics.setName("Electronics");
        electronics.setDescription("Electronic devices");
        categoryRepository.save(electronics);

        Product laptop = new Product();
        laptop.setName("Laptop");
        laptop.setDescription("Gaming laptop");
        laptop.setPrice(new BigDecimal("15000000"));
        laptop.setStock(10);
        laptop.setCategory(electronics);

        productRepository.save(laptop);
    }
}
