package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.entity.Product;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    ResponseEntity<Mono> createCustomer(Customer customer);

    ResponseEntity<Mono> updateCustomer(Customer customer, String documentNumber);

    ResponseEntity<Mono<Customer>> getCustomer(String documentNumber);

    Flux<Customer> getAllCustomers();

    ResponseEntity<Mono> deleteCustomer(String documentNumber);

    ResponseEntity<Mono> updateProductInCustomer(Product product, String documentNumber);

}
