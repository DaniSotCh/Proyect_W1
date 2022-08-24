package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductService {
    ResponseEntity<Mono> createProduct(Product product);

    ResponseEntity<Mono> updateProduct(Product product,String productId);

    ResponseEntity<Mono<Product>> getProduct(String productId);

    Flux<Product> getAllProducts();

    ResponseEntity<Mono> deleteProduct(String productId);
}