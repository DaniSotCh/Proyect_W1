package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Optional<Customer> findByDocumentNumber(String documentNumber);

    Mono<Void> deleteByDocumentNumber(String documentNumber);
}
