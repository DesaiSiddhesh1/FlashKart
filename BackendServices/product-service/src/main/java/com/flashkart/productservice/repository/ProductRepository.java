package com.flashkart.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.flashkart.productservice.entity.Product;

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}