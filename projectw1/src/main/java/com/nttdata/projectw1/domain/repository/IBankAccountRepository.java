package com.nttdata.projectw1.domain.repository;

import com.nttdata.projectw1.domain.entity.BankAccount;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface IBankAccountRepository extends ReactiveMongoRepository<BankAccount, String> {

    Flux<BankAccount> findByDocumentNumberCustomer(String documentNumberCustomer);
    Flux<BankAccount> findByAccountNumber(String accountNumber);
}
