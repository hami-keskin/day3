package com.example.day3.service;

import com.example.day3.dto.StockDto;
import com.example.day3.entity.Stock;
import com.example.day3.mapper.StockMapper;
import com.example.day3.repository.StockRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class StockServiceTest {

    @Mock
    private StockRepository stockRepository;

    @Mock
    private StockMapper stockMapper;

    @Mock
    private EntityManager entityManager;

    private StockService stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stockService = new StockService(stockRepository, stockMapper, entityManager);
    }

    @Test
    void testGetStockById() {
        Stock stock = new Stock();
        StockDto stockDto = new StockDto();
        when(stockRepository.findById(1)).thenReturn(Optional.of(stock));
        when(stockMapper.toDto(stock)).thenReturn(stockDto);

        Optional<StockDto> result = stockService.getStockById(1);
        assertTrue(result.isPresent());
        assertEquals(stockDto, result.get());
    }

    @Test
    void testCreateStock() {
        Stock stock = new Stock();
        StockDto stockDto = new StockDto();
        when(stockMapper.toEntity(stockDto)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(stock);
        when(stockMapper.toDto(stock)).thenReturn(stockDto);

        StockDto result = stockService.createStock(stockDto);
        assertEquals(stockDto, result);
    }

    @Test
    void testUpdateStock() {
        Stock stock = new Stock();
        StockDto stockDto = new StockDto();
        when(stockMapper.toEntity(stockDto)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(stock);
        when(stockMapper.toDto(stock)).thenReturn(stockDto);

        StockDto result = stockService.updateStock(stockDto);
        assertEquals(stockDto, result);
    }

    @Test
    void testDeleteStock() {
        doNothing().when(stockRepository).deleteById(1);
        stockService.deleteStock(1);
        verify(stockRepository, times(1)).deleteById(1);
    }

    @Test
    void testReduceStock() {
        Stock stock = new Stock();
        stock.setId(1);
        stock.setQuantity(100);

        when(entityManager.find(Stock.class, 1, LockModeType.PESSIMISTIC_WRITE)).thenReturn(stock);
        when(stockRepository.save(stock)).thenReturn(stock);

        stockService.reduceStock(1, 10);

        assertEquals(90, stock.getQuantity());
        verify(stockRepository, times(1)).save(stock);
    }
}
