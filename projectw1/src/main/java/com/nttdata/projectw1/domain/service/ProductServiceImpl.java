package com.nttdata.projectw1.domain.service;

import com.nttdata.projectw1.domain.entity.Product;
import com.nttdata.projectw1.domain.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Mono<Product> createProduct(Product product) {
        return productRepository.insert(product);
    }

    @Override
    public Mono<Product> updateProduct(Product product, String productId) {
        Mono<Product> response = productRepository.findByProductId(productId);
        return response.flatMap(x ->productRepository.save(product));
    }

    @Override
    public Mono<Product> getProduct(String productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Void> deleteProduct(String productId) {
        return productRepository.deleteByProductId(productId);
    }
}
