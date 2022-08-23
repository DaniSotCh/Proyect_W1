package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IProductRepository extends ReactiveMongoRepository<Product, String> {
    Optional<Product> findByProductId(String productId);
    void deleteByProductId(String productId);
}