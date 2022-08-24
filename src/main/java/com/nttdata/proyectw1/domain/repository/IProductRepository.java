package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
    Mono<Product> findByProductId(String productId);
    Mono<Void> deleteByProductId(String productId);
}