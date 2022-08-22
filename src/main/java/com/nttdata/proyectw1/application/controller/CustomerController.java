package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @PostMapping()
    public Mono<ResponseEntity> createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{documentNumber}")
    public Mono<ResponseEntity> updateCustomer(@RequestBody Customer customer, @PathVariable(name = "documentNumber") String documentNumber){
        return customerService.updateCustomer(customer,documentNumber);
    }

    @GetMapping("/{documentNumber}")
    public Mono<ResponseEntity<Customer>> getCustomer(@PathVariable(name = "documentNumber") String documentNumber){
        return customerService.getCustomer(documentNumber);
    }

    @GetMapping("/getCustomers")
    public Flux<List<Customer>> getCustomers(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/{documentNumber}")
    public Mono<ResponseEntity> deleteCustomer(@PathVariable(name = "documentNumber") String documentNumber){
        return customerService.deleteCustomer(documentNumber);
    }




}
