package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(name = "/product")
public class ProductController {
    @PostMapping
    public Mono<String> createProduct(@RequestBody Product product){
        return Mono.just("Successful Registration");
    }

    @PutMapping("/{productId}")
    public Mono<String> updateProduct(@RequestBody Product product){
        return Mono.just("Successful Update");
    }

    @GetMapping("/{productId}")
    public Mono<Product> getProduct(@RequestAttribute String productId){
        return Mono.just(new Product());
    }

    @GetMapping("/getProducts")
    public Flux<List<Product>> getProducts(){
        return Flux.just(new ArrayList<Product>());
    }

    @DeleteMapping("/{productId}")
    public Mono<String> deleteProduct(@RequestAttribute String productId){
        return Mono.just("Successful Delete");
    }
}