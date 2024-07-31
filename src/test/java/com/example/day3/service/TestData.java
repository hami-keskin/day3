package com.example.day3.service;

import com.example.day3.entity.Catalog;
import com.example.day3.entity.Product;
import com.example.day3.entity.Stock;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static Catalog createCatalog(Integer id, String name, String description, Boolean status, List<Product> products) {
        Catalog catalog = new Catalog();
        catalog.setId(id);
        catalog.setName(name);
        catalog.setDescription(description);
        catalog.setStatus(status);
        catalog.setProducts(products);
        return catalog;
    }

    public static Product createProduct(Integer id, String name, Double price, String description, Boolean status, Catalog catalog, Stock stock) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setDescription(description);
        product.setStatus(status);
        product.setCatalog(catalog);
        product.setStock(stock);
        return product;
    }

    public static Stock createStock(Integer id, Product product, Integer quantity) {
        Stock stock = new Stock();
        stock.setId(id);
        stock.setProduct(product);
        stock.setQuantity(quantity);
        return stock;
    }

    public static List<Catalog> createCatalogList() {
        List<Catalog> catalogs = new ArrayList<>();
        List<Product> products = createProductList();
        catalogs.add(createCatalog(1, "Electronics", "Various electronic products", true, products));
        catalogs.add(createCatalog(2, "Books", "Various books", true, null));
        return catalogs;
    }

    public static List<Product> createProductList() {
        List<Product> products = new ArrayList<>();
        Catalog catalog = createCatalog(1, "Electronics", "Various electronic products", true, null);

        products.add(createProduct(1, "Laptop", 1200.0, "A high-end laptop", true, catalog, null));
        products.add(createProduct(2, "Smartphone", 800.0, "A latest model smartphone", true, catalog, null));
        return products;
    }

    public static List<Stock> createStockList() {
        List<Stock> stocks = new ArrayList<>();
        Product product1 = createProduct(1, "Laptop", 1200.0, "A high-end laptop", true, null, null);
        Product product2 = createProduct(2, "Smartphone", 800.0, "A latest model smartphone", true, null, null);

        stocks.add(createStock(1, product1, 50));
        stocks.add(createStock(2, product2, 200));
        return stocks;
    }
}
