package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Customer;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(name = "/customer")
public class CustomerController {

    @PostMapping
    public Mono<String> createCustomer(@RequestBody Customer customer){
        return Mono.just("Successful Registration");
    }

    @PutMapping("/{documentNumber}")
    public Mono<String> updateCustomer(@RequestBody Customer customer, @RequestAttribute String documentNumber){
        return Mono.just("Successful Update");
    }

    @GetMapping("/{documentNumber}")
    public Mono<Customer> getCustomer(@RequestAttribute String documentNumber){
        return Mono.just(new Customer());
    }

    @GetMapping("/getCustomers")
    public Flux<List<Customer>> getCustomers(){
        return Flux.just(new ArrayList<Customer>());
    }

    @DeleteMapping("/{documentNumber}")
    public Mono<String> deleteCustomer(@RequestAttribute String documentNumber){
        return Mono.just("Successful Delete");
    }




}
