package com.example.day3.dto;

import lombok.Data;

@Data
public class ProductDto {
    private Integer id;
    private String name;
    private Double price;
    private String description;
    private Boolean status;
    private Integer catalogId;
}
