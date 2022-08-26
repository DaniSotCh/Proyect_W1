package com.nttdata.projectw1.domain.repository;

import com.nttdata.projectw1.domain.entity.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ICustomerRepository extends ReactiveMongoRepository<Customer, String> {
    Mono<Customer> findByDocumentNumber(String documentNumber);

    Mono<Void> deleteByDocumentNumber(String documentNumber);
}
