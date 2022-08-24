package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.BankAccount;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
    Mono<BankAccount> createTransaction(BankAccount bankAccount);
    Flux<BankAccount> getAllProductsAmounts(String documentNumberCustomer);
    Flux<BankAccount> getAllAmountsByProduct(String accountNumber, String documentNumberCustomer);
}
