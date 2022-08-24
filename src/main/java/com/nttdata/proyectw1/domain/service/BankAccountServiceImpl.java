package com.nttdata.proyectw1.domain.service;

import com.nttdata.proyectw1.domain.entity.Active;
import com.nttdata.proyectw1.domain.entity.BankAccount;
import com.nttdata.proyectw1.domain.entity.Customer;
import com.nttdata.proyectw1.domain.entity.Passive;
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
public class BankAccountServiceImpl implements IBankAccountService{
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Mono<BankAccount> createTransaction(BankAccount bankAccount) {
        Map<Object, Object> actualCustomer = actualAmountPerAccountNumber(bankAccount);
        Customer returnCustomer = (Customer) actualCustomer.get("auxCustomer");
        boolean increment = (boolean) actualCustomer.get("increment");
        if(increment){
            customerRepository.save(returnCustomer);
            return bankAccountRepository.insert(bankAccount);
        }else{
         return Mono.just(new BankAccount());
        }
    }

    private Map<Object, Object> actualAmountPerAccountNumber(BankAccount bankAccount){
        Mono<Customer> customerResponse = customerRepository.findByDocumentNumber(bankAccount.getDocumentNumberCustomer());
        Map<Object, Object> objReturn = new HashMap<>();

        Double amountReturn = 0.00;
        List<Passive> auxPassive = new ArrayList<>();
        Passive pasResp = new Passive();
        List<Active> auxActive = new ArrayList<>();
        Active actResp = new Active();

        Customer auxCustomer = new Customer();
        auxCustomer = customerResponse.toFuture().join();

        if(bankAccount.getProductType().getType().equals("PAS")){
            auxPassive = customerResponse.map(Customer::getPassiveList).toFuture().join();
            auxPassive.stream().filter(passive -> passive.getAccountNumber().equals(bankAccount.getAccountNumber()));
            pasResp = auxPassive.get(0);
            pasResp.setActualAmount(pasResp.getActualAmount()!=null?pasResp.getActualAmount():0.00);
            switch (bankAccount.getMovementType()){
                case DEPOSIT:
                    amountReturn = pasResp.getActualAmount()+bankAccount.getAmount();
                    break;
                case WITHDRAWAL:
                    amountReturn = pasResp.getActualAmount()-bankAccount.getAmount();
                    break;
            }
        }
        if(bankAccount.getProductType().getType().equals("ACT")){
            auxActive = customerResponse.map(Customer::getActiveList).toFuture().join();
            auxActive.stream().filter(active -> active.getAccountNumber().equals(bankAccount.getAccountNumber()));
            actResp = auxActive.get(0);
            switch (bankAccount.getMovementType()){
                case DEPOSIT:
                    amountReturn = actResp.getActualAmount()+bankAccount.getAmount();
                    break;
                case PAYMENT:
                    WITHDRAWAL:
                    amountReturn = actResp.getCreditLimit()-bankAccount.getAmount();
                    break;
            }
        }
        if(amountReturn<0){
            objReturn.put("auxCustomer", auxCustomer);
            objReturn.put("increment", false);
            return objReturn;
        }else{
            if(auxPassive.size()>0) {
                pasResp.setActualAmount(amountReturn);
                auxPassive.set(0, pasResp);
                auxCustomer.setPassiveList(auxPassive);
            }
            if(auxActive.size()>0) {
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
    public Flux<BankAccount> getAllProductsAmounts(String documentNumberCustomer) {
        return null;
    }

    @Override
    public Flux<BankAccount> getAllAmountsByProduct(String accountNumber, String documentNumberCustomer) {
        return null;
    }
}