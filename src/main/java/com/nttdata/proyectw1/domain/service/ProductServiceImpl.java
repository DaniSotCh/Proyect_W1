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

import java.util.Optional;
@Slf4j
@Service
public class ProductServiceImpl implements IProductService{

    @Autowired
    private IProductRepository productRepository;

    @Override
    public ResponseEntity<Mono> createProduct(Product product) {
        try{
            return new ResponseEntity<Mono>(productRepository.insert(product), HttpStatus.CREATED);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Mono> updateProduct(Product product, String productId) {
        try{
            Optional<Product> optionalProduct = productRepository.findByProductId(productId);
            if(optionalProduct.isPresent()){
                return new ResponseEntity<Mono>(productRepository.save(product), HttpStatus.CREATED);
            }
            return new ResponseEntity<Mono>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Mono<Product>> getProduct(String productId) {
        try{
            Optional<Product> product = productRepository.findByProductId(productId);
            if(product.isPresent()){
                return new ResponseEntity<Mono<Product>>(Mono.just(product.get()),HttpStatus.OK);
            }
            return new ResponseEntity<Mono<Product>>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            log.info(ex.getMessage());
            return new ResponseEntity<Mono<Product>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Flux<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ResponseEntity<Mono> deleteProduct(String productId) {
        try{
            return new ResponseEntity<Mono>(productRepository.deleteByProductId(productId), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
