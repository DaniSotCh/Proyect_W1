package com.nttdata.proyectw1.domain.service;


import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.repository.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Mono<ResponseEntity> createCustomer(Customer customer) {
        try{
            customerRepository.insert(customer);
            return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Mono<ResponseEntity> updateCustomer(Customer customer, String documentNumber) {
        try{
            Optional<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
            if(optionalCustomer.isPresent()){
                customerRepository.save(customer);
                return Mono.just(ResponseEntity.status(HttpStatus.CREATED).build());
            }
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Mono<ResponseEntity<Customer>> getCustomer(String documentNumber) {
        try{
            Optional<Customer> customer = customerRepository.findByDocumentNumber(documentNumber);
            if(customer.isPresent()){
                return Mono.just(ResponseEntity.ok(customer.get()));
            }
            return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }

    @Override
    public Flux<List<Customer>> getAllCustomers() {
            List<Customer> customers = customerRepository.findAll();
            return Flux.just(customers);
    }

    @Override
    public Mono<ResponseEntity> deleteCustomer(String documentNumber) {
        try{
            customerRepository.deleteByDocumentNumber(documentNumber);
            return Mono.just(ResponseEntity.status(HttpStatus.OK).build());
        }catch (Exception ex){
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
        }
    }
}
