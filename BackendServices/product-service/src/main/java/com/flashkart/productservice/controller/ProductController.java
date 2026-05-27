package com.flashkart.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flashkart.productservice.dto.ProductDto;
import com.flashkart.productservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	@Autowired
	private ProductService service;
	
	@PostMapping
	public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto dto){
		return new ResponseEntity<>(service.addProduct(dto),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAllProducts(){
		return ResponseEntity.ok(service.getAllProducts());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
		return ResponseEntity.ok(service.getProductById(id));
	}
}
