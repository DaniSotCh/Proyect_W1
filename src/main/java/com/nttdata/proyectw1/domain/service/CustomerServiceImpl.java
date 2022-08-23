package com.nttdata.proyectw1.domain.service;


import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.entity.Product;
import com.nttdata.proyectw1.domain.repository.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static com.nttdata.proyectw1.domain.util.constant.ProductTypeEnum.*;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public ResponseEntity<Mono> createCustomer(Customer customer) {
        try{
            if(businessRules(customer, customer.getCustomerType())){
                return new ResponseEntity<Mono>(customerRepository.insert(customer), HttpStatus.CREATED);
            }
            return new ResponseEntity<Mono>(HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Mono> updateCustomer(Customer customer, String documentNumber) {
        try{
            Optional<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
            if(optionalCustomer.isPresent()){
                return new ResponseEntity<Mono>(customerRepository.save(customer), HttpStatus.CREATED);
            }
            return new ResponseEntity<Mono>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Mono<Customer>> getCustomer(String documentNumber) {
        try{
            Optional<Customer> customer = customerRepository.findByDocumentNumber(documentNumber);
            if(customer.isPresent()){
                return new ResponseEntity<Mono<Customer>>(Mono.just(customer.get()),HttpStatus.OK);
            }
            return new ResponseEntity<Mono<Customer>>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<Mono<Customer>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Flux<Customer> getAllCustomers() {
            return customerRepository.findAll();
    }

    @Override
    public ResponseEntity<Mono> deleteCustomer(String documentNumber) {
        try{
            return new ResponseEntity<Mono>(customerRepository.deleteByDocumentNumber(documentNumber), HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Mono> updateProductInCustomer(Product product, String documentNumber) {
        try{
            Optional<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
            if(optionalCustomer.isPresent()){
                Customer updatedCustomer = optionalCustomer.get();
                updatedCustomer.getPassiveList().add(product.getPassiveProduct());
                updatedCustomer.getActiveList().add(product.getActiveProduct());

                if(businessRules(updatedCustomer, updatedCustomer.getCustomerType())){
                    return new ResponseEntity<Mono>(customerRepository.save(updatedCustomer), HttpStatus.CREATED);
                }
                return new ResponseEntity<Mono>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<Mono>(HttpStatus.NOT_FOUND);
        }catch (Exception ex){
            return new ResponseEntity<Mono>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean businessRules(Customer customer, String customerType){
        if(customerType.equals("P")){
            if(customer.getPassiveList()!=null){
                if(customer.getPassiveList().size()<4){
                    long countP1 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(CURRENT_ACCOUNT))
                            .count();
                    long countP2 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(CHECKING_ACCOUNT))
                            .count();
                    long countP3 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(FIXED_TERM_DEPOSITS))
                            .count();

                    if(countP1>1 || countP2>1 || countP3>1){
                        return false;
                    }
                }else{
                    return false;
                }
            }

            if (customer.getActiveList() != null) {
                long countA1 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(PERSONAL_CREDIT))
                        .count();
                long countA2 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(CREDIT_CARD))
                        .count();

                return countA1 <= 1 && countA2 <= 1;
            }
        }else{
            if(customer.getPassiveList()!=null){
                long countP2 = customer.getPassiveList().stream()
                        .filter(p -> !p.getProductType().equals(CHECKING_ACCOUNT))
                        .count();
                return countP2 == 0;
            }
            if (customer.getActiveList() != null) {
                long countA1 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(CREDIT_CARD))
                        .count();
                return countA1 <= 1;
            }

        }
        return true;
    }

}
