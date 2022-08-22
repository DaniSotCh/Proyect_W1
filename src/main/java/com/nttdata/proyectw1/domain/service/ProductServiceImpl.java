package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Product;
import com.nttdata.proyectw1.domain.repository.IProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Mono<ResponseEntity> createProduct(Product product) {
        try{
            productRepository.insert(product);
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Mono<ResponseEntity> updateProduct(Product product, String productId) {
        try{
            Optional<Product> optionalProduct = productRepository.findByProductId(productId);
            if(optionalProduct.isPresent()){
                productRepository.save(product);
                return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Mono<ResponseEntity<Product>> getProduct(String productId) {
        try{
            Optional<Product> product = productRepository.findByProductId(productId);
            if(product.isPresent()){
                return Mono.just(ResponseEntity.ok(product.get()));
            }
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Flux<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return Flux.just(products);
    }

    @Override
    public Mono<ResponseEntity> deleteProduct(String productId) {
        try{
            productRepository.deleteByProductId(productId);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }
}
