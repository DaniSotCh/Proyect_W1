package com.nttdata.projectw1.domain.service;

import com.nttdata.projectw1.domain.entity.*;
import com.nttdata.projectw1.domain.repository.IBankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
public class BankAccountServiceImpl implements IBankAccountService {
    @Autowired
    private IBankAccountRepository bankAccountRepository;
    @Autowired
    private ICustomerService customerService;

    @Override
    public Mono<BankAccount> createTransaction(BankAccount bankAccount) {
        Map<String, Object> actualCustomer = actualAmountPerAccountNumber(bankAccount);
        Customer returnCustomer = (Customer) actualCustomer.get("auxCustomer");
        boolean increment = (boolean) actualCustomer.get("increment");
        if (increment) {
            bankAccount.setDate(LocalDateTime.now());
            bankAccount.setCommissionAmount((Double) actualCustomer.get("commissionAmount"));
            customerService.updateCustomer(returnCustomer, bankAccount.getDocumentNumberCustomer()).subscribe();

            return bankAccountRepository.insert(bankAccount);
        } else {
            return Mono.just(new BankAccount());
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

    @Override
    public Mono<PassiveResponse> getProductByAccountNumber(String accountNumber) {
        Flux<Customer> customerResponse = customerService.getAllCustomers();
        PassiveResponse pasResp = new PassiveResponse();
        List<Customer> auxCustomer = customerResponse.collectList().share().toFuture().join().stream().filter(x->x.getPassiveList()
                .stream().filter(y->y.getAccountNumber().equals(accountNumber)).collect(Collectors.toList()).size()>0).collect(Collectors.toList());

        pasResp.setDocumentNumberCustomer(auxCustomer.get(0).getDocumentNumber());
        pasResp.setProductType(auxCustomer.get(0).getPassiveList().get(0).getProductType());
        pasResp.setAccountNumber(auxCustomer.get(0).getPassiveList().get(0).getAccountNumber());
        pasResp.setCommission(auxCustomer.get(0).getPassiveList().get(0).isCommission());
        pasResp.setCommissionAmount(auxCustomer.get(0).getPassiveList().get(0).getCommissionAmount());
        pasResp.setMovementLimit(auxCustomer.get(0).getPassiveList().get(0).getMovementLimit());
        pasResp.setActualAmount(auxCustomer.get(0).getPassiveList().get(0).getActualAmount());
        pasResp.setAverageMinAmount(auxCustomer.get(0).getPassiveList().get(0).getAverageMinAmount());
        pasResp.setHeadline(auxCustomer.get(0).getPassiveList().get(0).getHeadline());
        pasResp.setSignature(auxCustomer.get(0).getPassiveList().get(0).getSignature());

        return Mono.just(pasResp);
    }

    @Override
    public Flux<BankAccount> getAllAmountsByCustomer(String documentNumberCustomer) {
        return bankAccountRepository.findByDocumentNumberCustomer(documentNumberCustomer);
    }

    private Map<String, Object> actualAmountPerAccountNumber(BankAccount bankAccount) {
        Mono<Customer> customerResponse = customerService.getCustomer(bankAccount.getDocumentNumberCustomer());
        Map<String, Object> objReturn = new HashMap<>();

        double amountReturn = 0.00;
        List<Passive> auxPassive = new ArrayList<>();
        Passive pasResp = new Passive();
        List<Active> auxActive = new ArrayList<>();
        Active actResp = new Active();
        double commissionAmount=0.0;
        boolean commission = false;

        Customer auxCustomer = customerResponse.toFuture().join();

        if (bankAccount.getProductType().getType().equals("PAS")) {

            auxPassive = customerResponse.map(Customer::getPassiveList).toFuture().join();
            auxPassive.stream().filter(passive -> passive.getAccountNumber().equals(bankAccount.getAccountNumber()));
            pasResp = auxPassive.get(0);
            pasResp.setActualAmount(pasResp.getActualAmount() != null ? pasResp.getActualAmount() : 0.00);

            commission=pasResp.isCommission();

            int count = bankAccountRepository.findByAccountNumber(pasResp.getAccountNumber()).collectList().share().toFuture().join().size()+1;

            if(count>pasResp.getMovementLimit()){
                commission = true;
                commissionAmount = pasResp.getCommissionAmount();
            }else if(count==pasResp.getMovementLimit()){
                commission = true;
            }

            if(pasResp.isCommission()){
                switch (bankAccount.getMovementType()) {
                    case DEPOSIT:
                        amountReturn = pasResp.getActualAmount() + bankAccount.getAmount() - commissionAmount;
                        break;
                    case WITHDRAWAL:
                        amountReturn = pasResp.getActualAmount() - bankAccount.getAmount() - commissionAmount;
                        break;
                }
            }else{
                switch (bankAccount.getMovementType()) {
                    case DEPOSIT:
                        amountReturn = pasResp.getActualAmount() + bankAccount.getAmount();
                        break;
                    case WITHDRAWAL:
                        amountReturn = pasResp.getActualAmount() - bankAccount.getAmount();
                        break;
                }
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
            objReturn.put("increment", false);
        } else {
            if (auxPassive.size() > 0) {
                pasResp.setActualAmount(amountReturn);
                pasResp.setCommission(commission);
                auxPassive.set(0, pasResp);
                auxCustomer.setPassiveList(auxPassive);
            }
            if (auxActive.size() > 0) {
                actResp.setActualAmount(amountReturn);
                auxActive.set(0, actResp);
                auxCustomer.setActiveList(auxActive);
            }
            objReturn.put("increment", true);
            objReturn.put("commissionAmount",commissionAmount);

        }
        objReturn.put("auxCustomer", auxCustomer);
        return objReturn;
    }

}