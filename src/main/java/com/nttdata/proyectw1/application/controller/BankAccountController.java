package com.nttdata.proyectw1.application.controller;

import com.nttdata.proyectw1.domain.entity.BankAccount;
import com.nttdata.proyectw1.domain.service.IBankAccountService;
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
    public Flux<BankAccount> getAllProductsAmounts(@PathVariable String documentNumberCustomer){
        return bankAccountService.getAllProductsAmounts(documentNumberCustomer);
    }

    @GetMapping("/getAllAmounts/{documentNumberCustomer}")
    public Flux<BankAccount> getAllAmountsByProduct(@RequestBody String accountNumber, @PathVariable String documentNumberCustomer){
        return bankAccountService.getAllAmountsByProduct(accountNumber,documentNumberCustomer);
    }
}