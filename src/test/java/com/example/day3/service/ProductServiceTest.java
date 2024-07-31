package com.example.day3.service;

import com.example.day3.dto.ProductDto;
import com.example.day3.entity.Product;
import com.example.day3.mapper.ProductMapper;
import com.example.day3.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    private ProductDto productDto;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productDto = TestData.createProductDto();
        product = TestData.createProduct();
        when(productMapper.toDto(product)).thenReturn(productDto);
        when(productMapper.toEntity(productDto)).thenReturn(product);
    }

    @Test
    public void testGetAllProducts() {
        // Given
        when(productRepository.findAll()).thenReturn(List.of(product));

        // When
        List<ProductDto> result = productService.getAllProducts();

        // Then
        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));
        verify(productRepository).findAll();
    }

    @Test
    public void testGetProductById_Success() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        ProductDto result = productService.getProductById(1);

        // Then
        assertEquals(productDto, result);
        verify(productRepository).findById(1);
    }

    @Test
    public void testGetProductById_NotFound() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When
        ProductDto result = productService.getProductById(1);

        // Then
        assertNull(result);
        verify(productRepository).findById(1);
    }

    @Test
    public void testGetProductsByCatalogId() {
        // Given
        when(productRepository.findByCatalogId(1)).thenReturn(List.of(product));

        // When
        List<ProductDto> result = productService.getProductsByCatalogId(1);

        // Then
        assertEquals(1, result.size());
        assertEquals(productDto, result.get(0));
        verify(productRepository).findByCatalogId(1);
    }

    @Test
    public void testCreateProduct() {
        // Given
        when(productRepository.save(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(any(Product.class))).thenReturn(productDto);
        when(productMapper.toEntity(any(ProductDto.class))).thenReturn(product);

        // When
        ProductDto result = productService.createProduct(productDto);

        // Then
        assertNotNull(result);
        assertEquals(productDto, result);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    public void testUpdateProduct() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);

        // When
        ProductDto result = productService.updateProduct(1, productDto);

        // Then
        assertEquals(productDto, result);
        verify(productRepository).findById(1);
        verify(productRepository).save(product);
    }

    @Test
    public void testDeleteProduct() {
        // When
        productService.deleteProduct(1);

        // Then
        verify(productRepository).deleteById(1);
    }

    @Test
    public void testDeleteAllProducts() {
        // When
        productService.deleteAllProducts();

        // Then
        verify(productRepository).deleteAll();
    }

    @Test
    public void testUpdateProduct_NotFound() {
        // Given
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            productService.updateProduct(1, productDto);
        });
        verify(productRepository).findById(1);
    }
    @Test
    public void testCreateProduct_InvalidData() {
        // Given
        ProductDto invalidProductDto = new ProductDto(); // Geçersiz veriler (örneğin, eksik veya hatalı alanlar)

        // When
        ProductDto result = productService.createProduct(invalidProductDto);

        // Then
        // Geçersiz veri nedeniyle bir istisna fırlatılmasını bekleyebilirsiniz (örneğin, bir doğrulama istisnası)
        // assertThrows(ValidationException.class, () -> productService.createProduct(invalidProductDto));
    }

    @Test
    public void testUpdateProduct_InvalidData() {
        // Given
        ProductDto invalidProductDto = new ProductDto(); // Geçersiz veriler (örneğin, eksik veya hatalı alanlar)
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // When
        ProductDto result = productService.updateProduct(1, invalidProductDto);

        // Then
        // Geçersiz veri nedeniyle bir istisna fırlatılmasını bekleyebilirsiniz (örneğin, bir doğrulama istisnası)
        // assertThrows(ValidationException.class, () -> productService.updateProduct(1, invalidProductDto));
    }
    @Test
    public void testCacheEvictionOnCreateProduct() {
        // Given
        ProductDto newProductDto = TestData.createProductDto(); // Yeni bir ürün DTO'su
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        productService.createProduct(newProductDto);

        // Then
        // Cache davranışını doğrulamak için cache yöneticisini veya cache'deki veriyi kontrol eden ek kodlar ekleyebilirsiniz.
        // Bu örnek doğrudan cache kontrolü sağlamaz, ancak uygulama seviyesinde cache durumunu kontrol edebilirsiniz.
    }

    @Test
    public void testCacheEvictionOnUpdateProduct() {
        // Given
        ProductDto updatedProductDto = TestData.createProductDto(); // Güncellenmiş ürün DTO'su
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        productService.updateProduct(1, updatedProductDto);

        // Then
        // Cache davranışını doğrulamak için cache yöneticisini veya cache'deki veriyi kontrol eden ek kodlar ekleyebilirsiniz.
        // Bu örnek doğrudan cache kontrolü sağlamaz, ancak uygulama seviyesinde cache durumunu kontrol edebilirsiniz.
    }
    @Test
    public void testTransactionOnCreateProduct() {
        // Given
        ProductDto newProductDto = TestData.createProductDto(); // Yeni bir ürün DTO'su
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        ProductDto result = productService.createProduct(newProductDto);

        // Then
        // Veritabanı işlemlerinin düzgün şekilde yapıldığını kontrol edebilirsiniz.
        // Örneğin, veritabanı kaydının gerçekten yapılıp yapılmadığını doğrulayabilirsiniz.
    }

    @Test
    public void testTransactionOnUpdateProduct() {
        // Given
        ProductDto updatedProductDto = TestData.createProductDto(); // Güncellenmiş ürün DTO'su
        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // When
        ProductDto result = productService.updateProduct(1, updatedProductDto);

        // Then
        // Veritabanı işlemlerinin düzgün şekilde yapıldığını kontrol edebilirsiniz.
        // Örneğin, veritabanı kaydının gerçekten güncellendiğini doğrulayabilirsiniz.
    }
    @Test
    public void testGetProductsByCatalogId_NoProducts() {
        // Given
        when(productRepository.findByCatalogId(1)).thenReturn(List.of());

        // When
        List<ProductDto> result = productService.getProductsByCatalogId(1);

        // Then
        assertTrue(result.isEmpty());
        verify(productRepository).findByCatalogId(1);
    }

}
