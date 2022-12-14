package com.nttdata.projectw1.application.controller;

import com.nttdata.projectw1.domain.entity.BankAccount;
import com.nttdata.projectw1.domain.entity.PassiveResponse;
import com.nttdata.projectw1.domain.entity.ProductList;
import com.nttdata.projectw1.domain.service.IBankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bankAccount")
public class BankAccountController {
    @Autowired
    IBankAccountService bankAccountService;

    @PostMapping()
    public Mono<BankAccount> createTransaction(@RequestBody BankAccount bankAccount){
        return bankAccountService.createTransaction(bankAccount);
    }

    @GetMapping("/getAllProducts/{documentNumberCustomer}")
    public Flux<ProductList> getAllProductsAmounts(@PathVariable String documentNumberCustomer){
        return bankAccountService.getAllProductsAmounts(documentNumberCustomer);
    }
    @GetMapping("/getProduct/{accountNumber}")
    public Mono<PassiveResponse> getProductByAccountNumber(@PathVariable String accountNumber){
        return bankAccountService.getProductByAccountNumber(accountNumber);
    }
    @GetMapping("/getAllAmounts/{accountNumber}")
    public Flux<BankAccount> getAllAmountsByProduct(@PathVariable String accountNumber){
        return bankAccountService.getAllAmountsByProduct(accountNumber);
    }

    @GetMapping("/getAllAmountsCustomer/{documentNumberCustomer}")
    public Flux<BankAccount> getAllAmountsByCustomer(@PathVariable String documentNumberCustomer){
        return bankAccountService.getAllAmountsByCustomer(documentNumberCustomer);
    }
}