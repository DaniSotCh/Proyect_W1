package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Product product,String productId);

    Mono<Product> getProduct(String productId);

    Flux<Product> getAllProducts();

    Mono<Void> deleteProduct(String productId);
}