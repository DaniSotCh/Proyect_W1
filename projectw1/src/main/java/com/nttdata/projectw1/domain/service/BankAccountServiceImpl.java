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
import java.util.stream.Stream;


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
    public Mono<Passive> getProductByAccountNumber(String accountNumber) {
        Flux<Customer> customerResponse = customerService.getAllCustomers();
        List<List<Passive>> auxPassive = new ArrayList<>();
        Passive pasResp = new Passive();
        List<Passive> pass2;
        auxPassive = customerResponse.map(Customer::getPassiveList).collectList().share().toFuture().join();
        //pass2 = auxPassive.stream().filter(x->x.stream().filter(y-> y.getAccountNumber().equals(accountNumber))).collect(Collectors.toList());
        //auxPassive.stream().filter(passive -> passive.);
        pasResp = auxPassive.get(0).get(0);
        return Mono.just(pasResp);
    }

    private Map<String, Object> actualAmountPerAccountNumber(BankAccount bankAccount) {
        Mono<Customer> customerResponse = customerService.getCustomer(bankAccount.getDocumentNumberCustomer());
        Map<String, Object> objReturn = new HashMap<>();

        Double amountReturn = 0.00;
        List<Passive> auxPassive = new ArrayList<>();
        Passive pasResp = new Passive();
        List<Active> auxActive = new ArrayList<>();
        Active actResp = new Active();
        double comissionAmount=0.0;
        boolean comission = false;

        Customer auxCustomer = new Customer();
        auxCustomer = customerResponse.toFuture().join();

        if (bankAccount.getProductType().getType().equals("PAS")) {

            auxPassive = customerResponse.map(Customer::getPassiveList).toFuture().join();
            auxPassive.stream().filter(passive -> passive.getAccountNumber().equals(bankAccount.getAccountNumber()));
            pasResp = auxPassive.get(0);
            pasResp.setActualAmount(pasResp.getActualAmount() != null ? pasResp.getActualAmount() : 0.00);

            comission=pasResp.isCommission();
            List<BankAccount> lista=new ArrayList<>();

            int count = bankAccountRepository.findByAccountNumber(pasResp.getAccountNumber()).collectList().share().toFuture().join().size()+1;

            if(count>pasResp.getMovementLimit()){
                comission = true;
                comissionAmount = pasResp.getCommissionAmount();
            }else if(count==pasResp.getMovementLimit()){
                comission = true;
            }

            if(pasResp.isCommission()){
                switch (bankAccount.getMovementType()) {
                    case DEPOSIT:
                        amountReturn = pasResp.getActualAmount() + bankAccount.getAmount() - comissionAmount;
                        break;
                    case WITHDRAWAL:
                        amountReturn = pasResp.getActualAmount() - bankAccount.getAmount() - comissionAmount;
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
            objReturn.put("auxCustomer", auxCustomer);
            objReturn.put("increment", false);
            return objReturn;
        } else {
            if (auxPassive.size() > 0) {
                pasResp.setActualAmount(amountReturn);
                pasResp.setCommission(comission);
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
            objReturn.put("commissionAmount",comissionAmount);
            return objReturn;
        }
    }

}