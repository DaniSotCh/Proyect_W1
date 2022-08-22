package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Customer;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomerService {

    Mono<ResponseEntity> createCustomer(Customer customer);

    Mono<ResponseEntity> updateCustomer(Customer customer, String documentNumber);

    Mono<ResponseEntity<Customer>> getCustomer(String documentNumber);

    Flux<List<Customer>> getAllCustomers();

    Mono<ResponseEntity> deleteCustomer(String documentNumber);

}
