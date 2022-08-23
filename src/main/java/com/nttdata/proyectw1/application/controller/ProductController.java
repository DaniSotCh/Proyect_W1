package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Product;
import com.nttdata.proyectw1.domain.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @PostMapping
    public Mono<String> createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return Mono.just("Successful Registration");
    }

    @PutMapping("/{productId}")
    public Mono<String> updateProduct(@RequestBody Product product,@PathVariable String productId){
        productService.updateProduct(product,productId);
        return Mono.just("Successful Update");
    }

    @GetMapping("/{productId}")
    public Mono<ResponseEntity<Product>> getProduct(@PathVariable String productId){
        Mono<ResponseEntity<Product>> response = productService.getProduct(productId);
        return response;
    }

    @GetMapping("/getProducts")
    public Flux<Product> getProducts(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public Mono<String> deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
        return Mono.just("Successful Delete");
    }
}