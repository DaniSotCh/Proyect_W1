package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ICustomerRepository extends MongoRepository<Customer, String> {
    Optional<Customer> findByDocumentNumber(String documentNumber);

    void deleteByDocumentNumber(String documentNumber);
}
