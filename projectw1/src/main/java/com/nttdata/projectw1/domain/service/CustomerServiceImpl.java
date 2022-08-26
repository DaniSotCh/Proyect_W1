package com.nttdata.projectw1.domain.service;


import com.nttdata.projectw1.domain.entity.Active;
import com.nttdata.projectw1.domain.entity.Customer;
import com.nttdata.projectw1.domain.entity.Passive;
import com.nttdata.projectw1.domain.entity.Product;
import com.nttdata.projectw1.domain.repository.ICustomerRepository;
import com.nttdata.projectw1.domain.util.constant.CustomerTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.nttdata.projectw1.domain.util.constant.CustomerTypeEnum.*;
import static com.nttdata.projectw1.domain.util.constant.ProductTypeEnum.*;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public Mono<Customer> updateCustomer(Customer customer, String documentNumber) {
        Mono<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
        return optionalCustomer.flatMap(x-> customerRepository.save(customer));
    }

    @Override
    public Mono<Customer> getCustomer(String documentNumber) {
        return customerRepository.findByDocumentNumber(documentNumber);
    }

    @Override
    public Flux<Customer> getAllCustomers() {
            return customerRepository.findAll();
    }

    @Override
    public Mono<Void> deleteCustomer(String documentNumber) {
        return customerRepository.deleteByDocumentNumber(documentNumber);
    }

    @Override
    public Mono<Customer> updateProductInCustomer(Product product, String documentNumber) {
        Mono<Customer> optionalCustomer = customerRepository.findByDocumentNumber(documentNumber);
        return optionalCustomer.flatMap(x->{
            if(product.getPassiveProduct()!=null){
                List<Passive> auxP;
                if(x.getPassiveList()!=null){
                    x.getPassiveList().add(product.getPassiveProduct());
                }else{
                    auxP=new ArrayList<>();
                    auxP.add(product.getPassiveProduct());
                    x.setPassiveList(auxP);
                }

            }
            if(product.getActiveProduct()!=null){
                List<Active> auxA;
                if(x.getActiveList()!=null){
                    x.getActiveList().add(product.getActiveProduct());
                }else{
                    auxA=new ArrayList<>();
                    auxA.add(product.getActiveProduct());
                    x.setActiveList(auxA);
                }
            }
            if(businessRules(x,x.getCustomerType())){
               return customerRepository.save(x);
            }
            return optionalCustomer;
        });
    }

    private boolean businessRules(Customer customer, CustomerTypeEnum customerType){
        if(customerType.equals(PERSONAL)){
            if(customer.getPassiveList()!=null){
                if(customer.getPassiveList().size()<4){
                    long countP1 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(CURRENT_ACCOUNT))
                            .count();
                    long countP2 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(CHECKING_ACCOUNT))
                            .count();
                    long countP3 = customer.getPassiveList().stream()
                            .filter(p -> p.getProductType().equals(FIXED_TERM_DEPOSITS))
                            .count();

                    if(countP1>1 || countP2>1 || countP3>1){
                        return false;
                    }
                }else{
                    return false;
                }
            }

            if (customer.getActiveList() != null) {
                long countA1 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(PERSONAL_CREDIT))
                        .count();
                long countA2 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(CREDIT_CARD))
                        .count();

                return countA1 <= 1 && countA2 <= 1;
            }
        }else if(customerType.equals(VIP)||customerType.equals(PYME)){
            if (customer.getActiveList() != null) {
                long countA1 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(CREDIT_CARD))
                        .count();
                return countA1 >= 1;
            }else{
                return false;
            }
        }else{
            if(customer.getPassiveList()!=null){
                long countP2 = customer.getPassiveList().stream()
                        .filter(p -> !p.getProductType().equals(CHECKING_ACCOUNT))
                        .count();
                return countP2 == 0;
            }
            if (customer.getActiveList() != null) {
                long countA1 = customer.getActiveList().stream()
                        .filter(p -> p.getProductType().equals(CREDIT_CARD))
                        .count();
                return countA1 <= 1;
            }

        }
        return true;
    }

}
