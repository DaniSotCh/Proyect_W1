package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.BankAccount;
import com.nttdata.projectw2.domain.entity.Customer;
import com.nttdata.projectw2.domain.entity.Passive;
import com.nttdata.projectw2.domain.entity.Report;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
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
        System.out.println("-------------ENTRA1--------------");
        Mono<BankAccount> passiveTransfer = null;

        List<Flux<BankAccount>> filterBA = new ArrayList<>();

        customerMono.subscribe(x -> {
            Mono<BankAccount> response = null;
            System.out.println("-------------ENTRA2--------------");
            for (Passive y : x.getPassiveList()) {
                String accountNumber = y.getAccountNumber();
                Flux<BankAccount> resp2 = this.webClient.get().uri("/bankAccount/getAllAmounts/{accountNumber}", accountNumber).retrieve().bodyToFlux(BankAccount.class);
                System.out.println("-------------FILTRA--------------");
                filterBA.add(resp2);
            }
            System.out.println("-------------SALE1--------------");
        });

        System.out.println("-------------SALE2--------------");

        Report response = new Report();

        return Flux.just(response);
    }
}
