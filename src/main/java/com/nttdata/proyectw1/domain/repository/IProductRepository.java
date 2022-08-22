package com.nttdata.proyectw1.domain.repository;


import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IProductRepository  extends MongoRepository<Product, String> {
    Optional<Product> findByProductTypeId(String productId);
    void deleteByProductId(String productId);
}