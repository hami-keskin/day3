package com.example.day3.mapper;

import com.example.day3.dto.ProductDto;
import com.example.day3.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product product);
}
