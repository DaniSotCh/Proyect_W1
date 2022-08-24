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
    public ResponseEntity<Mono> createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Mono> updateProduct(@RequestBody Product product,@PathVariable String productId){
        return productService.updateProduct(product,productId);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Mono<Product>> getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @GetMapping("/getProducts")
    public Flux<Product> getProducts(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Mono> deleteProduct(@PathVariable String productId){
        return productService.deleteProduct(productId);
    }
}