package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import com.nttdata.proyectw1.domain.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Mono<Void> createProduct(Product product) {
        productRepository.insert(product);
        return null;
    }

    @Override
    public Mono<Void> updateProduct(Product product) {
        Optional<Product> optionalPassiveProduct = productRepository.findByProductTypeId(product.getPassiveProduct().getAccountNumber());
        Optional<Product> optionalActiveProduct = productRepository.findByProductTypeId(product.getActiveProduct().getActiveNumber());
        if(optionalPassiveProduct.isPresent()||optionalActiveProduct.isPresent()){
            productRepository.save(product);
        }
        return null;
    }

    @Override
    public Mono<Product> getProduct(String productId) {
        Optional<Product> product = productRepository.findByProductTypeId(productId);
        if(product.isPresent()){
            return Mono.just(product.get());
        }
        return null;
    }

    @Override
    public Flux<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return Flux.just(products);
    }

    @Override
    public Mono<Void> deleteProduct(String productId) {
        productRepository.deleteByProductId(productId);
        return null;
    }
}
