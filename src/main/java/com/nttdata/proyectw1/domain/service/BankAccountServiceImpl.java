package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.BankAccount;
import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.repository.IBankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class BankAccountServiceImpl implements IBankAccountService{
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private CustomerServiceImpl customerService;

    @Override
    public Mono<BankAccount> createTransaction(BankAccount bankAccount) {
        Customer updateCustomer = new Customer();
        Double transactionAmount = 0.00;
        Double actualAmount = actualAmountPerAccountNumber(bankAccount);
        /*switch (bankAccount.getMovementType()){
            case DEPOSIT:
                return null;
            case PAYMENT:
                return null;
            case WITHDRAWAL:
                return null;
        }*/
        return bankAccountRepository.insert(bankAccount);
    }

    private Double actualAmountPerAccountNumber(BankAccount bankAccount){
        Mono<Customer> customerResponse = customerService.getCustomer(bankAccount.getDocumentNumberCustomer());
        Customer auxCustomer = new Customer();
        Double amountReturn = 0.00;
        if(bankAccount.getProductType().getType().equals("PAS")){
            auxCustomer = customerResponse.block();
            amountReturn = 2.00;
            /*auxCustomer.flatMap(customer -> {
                customer.getPassiveList().stream().map(passive -> {
                   if(passive.getAccountNumber().equals(bankAccount.getAccountNumber())){
                       return passive.getActualAmount();
                   }else{
                       return 0.00;
                   }
                });
                return null;
            });*/
        }else{

        }
        return amountReturn;
    }

    @Override
    public Flux<BankAccount> getAllProductsAmounts(String documentNumberCustomer) {
        return null;
    }

    @Override
    public Flux<BankAccount> getAllAmountsByProduct(String accountNumber, String documentNumberCustomer) {
        return null;
    }
}