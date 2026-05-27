package com.flashkart.productservice.service;

import java.util.List;

import com.flashkart.productservice.dto.ProductDto;

public interface ProductService {

    ProductDto addProduct(ProductDto dto);

    List<ProductDto> getAllProducts();

    ProductDto getProductById(Long id);
}