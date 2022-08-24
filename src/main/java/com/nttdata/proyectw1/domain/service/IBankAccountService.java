package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.BankAccount;
import com.nttdata.proyectw1.domain.entity.ProductList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
    Mono<BankAccount> createTransaction(BankAccount bankAccount);
    Flux<ProductList> getAllProductsAmounts(String documentNumberCustomer);
    Flux<BankAccount> getAllAmountsByProduct(String accountNumber);
}
