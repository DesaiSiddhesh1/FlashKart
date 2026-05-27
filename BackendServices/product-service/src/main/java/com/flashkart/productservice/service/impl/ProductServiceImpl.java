package com.flashkart.productservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.flashkart.productservice.dto.ProductDto;
import com.flashkart.productservice.entity.Product;
import com.flashkart.productservice.repository.ProductRepository;
import com.flashkart.productservice.service.ProductService;


@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public ProductDto addProduct(ProductDto dto) {
		Product product = mapper.map(dto, Product.class);
		
		Product savedProduct = repository.save(product);
		
		return mapper.map(savedProduct, ProductDto.class);
	}
	
	@Override
	public List<ProductDto> getAllProducts(){
		return repository.findAll().stream()
				.map(product -> 
						mapper.map(product,
								ProductDto.class))
					.collect(Collectors.toList());
	}
	
	@Override
	@Cacheable(value="products",key = "#id")
	public ProductDto getProductById(Long id)
	{
		Product product = repository.findById(id)
							.orElseThrow(()->
							new RuntimeException("Product not found"));
		
		return mapper.map(product, ProductDto.class);
	}
}
