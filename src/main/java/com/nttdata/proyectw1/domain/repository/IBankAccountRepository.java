package com.nttdata.proyectw1.domain.repository;

import com.nttdata.proyectw1.domain.entity.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IBankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {

    Mono<BankAccount> findByDocumentNumberCustomer(String documentNumberCustomer);
}
