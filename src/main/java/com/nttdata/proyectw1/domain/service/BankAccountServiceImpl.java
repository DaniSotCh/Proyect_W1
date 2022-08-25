package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.*;
import com.nttdata.proyectw1.domain.repository.IBankAccountRepository;
import com.nttdata.proyectw1.domain.repository.ICustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BankAccountServiceImpl implements IBankAccountService {
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private ICustomerService customerService;

    @Override
    public Mono<BankAccount> createTransaction(BankAccount bankAccount) {
        Map<Object, Object> actualCustomer = actualAmountPerAccountNumber(bankAccount);
        Customer returnCustomer = (Customer) actualCustomer.get("auxCustomer");
        boolean increment = (boolean) actualCustomer.get("increment");
        if (increment) {
            customerService.updateCustomer(returnCustomer, bankAccount.getDocumentNumberCustomer()).subscribe();
            return bankAccountRepository.insert(bankAccount);
        } else {
            return Mono.just(new BankAccount());
        }
    }

    private Map<Object, Object> actualAmountPerAccountNumber(BankAccount bankAccount) {
        Mono<Customer> customerResponse = customerService.getCustomer(bankAccount.getDocumentNumberCustomer());
        Map<Object, Object> objReturn = new HashMap<>();

        Double amountReturn = 0.00;
        List<Passive> auxPassive = new ArrayList<>();
        Passive pasResp = new Passive();
        List<Active> auxActive = new ArrayList<>();
        Active actResp = new Active();

        Customer auxCustomer = new Customer();
        auxCustomer = customerResponse.toFuture().join();

        if (bankAccount.getProductType().getType().equals("PAS")) {
            auxPassive = customerResponse.map(Customer::getPassiveList).toFuture().join();
            auxPassive.stream().filter(passive -> passive.getAccountNumber().equals(bankAccount.getAccountNumber()));
            pasResp = auxPassive.get(0);
            pasResp.setActualAmount(pasResp.getActualAmount() != null ? pasResp.getActualAmount() : 0.00);
            switch (bankAccount.getMovementType()) {
                case DEPOSIT:
                    amountReturn = pasResp.getActualAmount() + bankAccount.getAmount();
                    break;
                case WITHDRAWAL:
                    amountReturn = pasResp.getActualAmount() - bankAccount.getAmount();
                    break;
            }
        }
        if (bankAccount.getProductType().getType().equals("ACT")) {
            auxActive = customerResponse.map(Customer::getActiveList).toFuture().join();
            auxActive.stream().filter(active -> active.getAccountNumber().equals(bankAccount.getAccountNumber()));
            actResp = auxActive.get(0);
            switch (bankAccount.getMovementType()) {
                case DEPOSIT:
                    amountReturn = actResp.getActualAmount() + bankAccount.getAmount();
                    break;
                case PAYMENT:
                    WITHDRAWAL:
                    amountReturn = actResp.getCreditLimit() - bankAccount.getAmount();
                    break;
            }
        }
        if (amountReturn < 0) {
            objReturn.put("auxCustomer", auxCustomer);
            objReturn.put("increment", false);
            return objReturn;
        } else {
            if (auxPassive.size() > 0) {
                pasResp.setActualAmount(amountReturn);
                auxPassive.set(0, pasResp);
                auxCustomer.setPassiveList(auxPassive);
            }
            if (auxActive.size() > 0) {
                actResp.setActualAmount(amountReturn);
                auxActive.set(0, actResp);
                auxCustomer.setActiveList(auxActive);
            }
            objReturn.put("auxCustomer", auxCustomer);
            objReturn.put("increment", true);
            return objReturn;
        }
    }

    @Override
    public Flux<ProductList> getAllProductsAmounts(String documentNumberCustomer) {
        Mono<Customer> customerResponse = customerService.getCustomer(documentNumberCustomer);
        Customer auxCustomer = customerResponse.toFuture().join();
        ProductList response = new ProductList();
        response.setPassiveList(auxCustomer.getPassiveList());
        response.setActiveList(auxCustomer.getActiveList());

        return Flux.just(response);
    }

    @Override
    public Flux<BankAccount> getAllAmountsByProduct(String accountNumber) {
        return bankAccountRepository.findByAccountNumber(accountNumber);
    }
}