package com.nttdata.projectw1.application.controller;

import com.nttdata.projectw1.domain.entity.Product;
import com.nttdata.projectw1.domain.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    IProductService productService;
    @PostMapping
    public Mono<Product> createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }

    @PutMapping("/{productId}")
    public Mono<Product> updateProduct(@RequestBody Product product,@PathVariable String productId){
        return productService.updateProduct(product,productId);
    }

    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@PathVariable String productId){
        return productService.getProduct(productId);
    }

    @GetMapping("/getProducts")
    public Flux<Product> getProducts(){
        return productService.getAllProducts();
    }

    @DeleteMapping("/{productId}")
    public Mono<Void> deleteProduct(@PathVariable String productId){
        return productService.deleteProduct(productId);
    }
}