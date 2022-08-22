package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {
    Mono<Void> createProduct(Product product);

    Mono<Void> updateProduct(Product product);

    Mono<Product> getProduct(String productId);

    Flux<List<Product>> getAllProducts();

    Mono<Void> deleteProduct(String productId);
}