package com.flashkart.productservice.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String category;

    private Double price;

    private Integer stock;
}