package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IProductService {
    Mono<ResponseEntity> createProduct(Product product);

    Mono<ResponseEntity> updateProduct(Product product,String productId);

    Mono<ResponseEntity<Product>> getProduct(String productId);

    Flux<List<Product>> getAllProducts();

    Mono<ResponseEntity> deleteProduct(String productId);
}