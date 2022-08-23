package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.entity.Product;
import com.nttdata.proyectw1.domain.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    ICustomerService customerService;

    @PostMapping()
    public ResponseEntity<Mono> createCustomer(@RequestBody Customer customer){
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{documentNumber}")
    public ResponseEntity<Mono> updateCustomer(@RequestBody Customer customer, @PathVariable(name = "documentNumber") String documentNumber){
        return customerService.updateCustomer(customer,documentNumber);
    }

    @GetMapping("/{documentNumber}")
    public ResponseEntity<Mono<Customer>> getCustomer(@PathVariable(name = "documentNumber") String documentNumber){
        return customerService.getCustomer(documentNumber);
    }

    @GetMapping("/getCustomers")
    public Flux<Customer> getCustomers(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/{documentNumber}")
    public ResponseEntity<Mono> deleteCustomer(@PathVariable(name = "documentNumber") String documentNumber){
        return customerService.deleteCustomer(documentNumber);
    }

    @PutMapping("/addProduct/{documentNumber}")
    public ResponseEntity<Mono> updateProductInCustomer(@RequestBody Product product, @PathVariable(name = "documentNumber") String documentNumber){
        return customerService.updateProductInCustomer(product, documentNumber);
    }


}
