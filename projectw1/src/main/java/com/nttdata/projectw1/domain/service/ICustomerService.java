package com.nttdata.projectw1.domain.service;

import com.nttdata.projectw1.domain.entity.Customer;
import com.nttdata.projectw1.domain.entity.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<Customer> createCustomer(Customer customer);

    Mono<Customer> updateCustomer(Customer customer, String documentNumber);

    Mono<Customer> getCustomer(String documentNumber);

    Flux<Customer> getAllCustomers();

    Mono<Void> deleteCustomer(String documentNumber);

    Mono<Customer> updateProductInCustomer(Product product, String documentNumber);

}
