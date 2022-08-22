package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ICustomerService {

    Mono<Void> createCustomer(Customer customer);

    Mono<Void> updateCustomer(Customer customer, String documentNumber);

    Mono<Customer> getCustomer(String documentNumber);

    Flux<List<Customer>> getAllCustomers();

    Mono<Void> deleteCustomer(String documentNumber);

}
