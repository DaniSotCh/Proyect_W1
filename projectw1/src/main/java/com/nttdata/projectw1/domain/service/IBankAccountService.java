package com.nttdata.projectw1.domain.service;

import com.nttdata.projectw1.domain.entity.BankAccount;
import com.nttdata.projectw1.domain.entity.Passive;
import com.nttdata.projectw1.domain.entity.PassiveResponse;
import com.nttdata.projectw1.domain.entity.ProductList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBankAccountService {
    Mono<BankAccount> createTransaction(BankAccount bankAccount);
    Flux<ProductList> getAllProductsAmounts(String documentNumberCustomer);
    Flux<BankAccount> getAllAmountsByProduct(String accountNumber);
    Mono<PassiveResponse> getProductByAccountNumber(String accountNumber);
}
