package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {
    private final WebClient webClient;

    public ReportServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Flux<Report> averageReport(String documentNumber) {
        //1. Customer per DocumentNumber
        //  -PassiveList
        //  -ActiveList
        Mono<Customer> customerMono = this.webClient.get().uri("/customer/{documentNumber}", documentNumber).retrieve().bodyToMono(Customer.class);

        //2. getAllAmounts per accountNumber
        //  -BankAccountList
        Mono<BankAccount> passiveTransfer = null;

        List<Flux<BankAccount>> filterBA = new ArrayList<>();

        customerMono.subscribe(v -> {
            Mono<BankAccount> response = null;
            System.out.println("-------------ENTRA2 CustomerMono Subscribe--------------");
            if(v.getPassiveList().size()>0){
                for (Passive w : v.getPassiveList()) {
                    String accountNumber = w.getAccountNumber();
                    System.out.println("AccountNumberPassive: " + accountNumber);
                    Flux<BankAccount> resp2 = this.webClient.get()
                            .uri("/bankAccount/getAllAmounts/{accountNumber}", accountNumber)
                            .retrieve()
                            .bodyToFlux(BankAccount.class);
                    System.out.println("-------------PASSIVE BEFORE RESP2.SUBSCRIBE--------------");
                    resp2.subscribe(x->{
                        System.out.println("BankAccount passive--: " + x.getDocumentNumberCustomer());
                        System.out.println("BankAccount passive--: " + x.getAccountNumber());
                        System.out.println("BankAccount passive--: " + x.getId());
                        System.out.println("BankAccount passive--: " + x.getCommissionAmount());
                        System.out.println("BankAccount passive--: " + x.getAmount());
                        System.out.println("-------------");
                    });
                    filterBA.add(resp2);
                }
            }

            if(v.getActiveList().size()>0) {
                for (Active w : v.getActiveList()) {
                    String accountNumber2 = w.getAccountNumber();
                    Flux<BankAccount> resp3 = this.webClient.get()
                            .uri("/bankAccount/getAllAmounts/{accountNumber2}", accountNumber2)
                            .retrieve()
                            .bodyToFlux(BankAccount.class);
                    System.out.println("-------------ACTIVE BEFORE RESP3.SUBSCRIBE--------------");
                    resp3.subscribe(x->{
                        System.out.println("BankAccount active--: " + x.getDocumentNumberCustomer());
                        System.out.println("BankAccount active--: " + x.getAccountNumber());
                        System.out.println("BankAccount active--: " + x.getId());
                    });

                    System.out.println("-------------FILTRA ACTIVE--------------");
                    filterBA.add(resp3);
                }
            }

        });

        Report response = new Report();

        return Flux.just(response);
    }

}
