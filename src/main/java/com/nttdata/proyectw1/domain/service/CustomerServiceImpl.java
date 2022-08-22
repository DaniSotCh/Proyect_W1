package com.nttdata.proyectw1.domain.service;


import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Mono<Void> createCustomer(Customer customer) {
        customerRepository.insert(customer);
        return null;
    }

    @Override
    public Mono<Void> updateCustomer(Customer customer, String documentNumber) {
        Optional<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
        if(optionalCustomer.isPresent()){
            customerRepository.save(customer);
        }
        return null;
    }

    @Override
    public Mono<Customer> getCustomer(String documentNumber) {
        Optional<Customer> customer = customerRepository.findByDocumentNumber(documentNumber);
        if(customer.isPresent()){
            return Mono.just(customer.get());
        }
        return null;
    }

    @Override
    public Flux<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return Flux.just(customers);
    }

    @Override
    public Mono<Void> deleteCustomer(String documentNumber) {
        customerRepository.deleteByDocumentNumber(documentNumber);
        return null;
    }
}
