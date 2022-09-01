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
        System.out.println("-------------ENTRA1--------------");
        Mono<BankAccount> passiveTransfer = null;

        List<Flux<BankAccount>> filterBA = new ArrayList<>();
//        Map<String,Object> mapResult= new HashMap<>();

        customerMono.subscribe(x -> {
            Mono<BankAccount> response = null;
            System.out.println("-------------ENTRA2--------------");
//            mapResult.put("documentNumber",x.getDocumentNumber());
            if(x.getPassiveList().size()>0){
                for (Passive y : x.getPassiveList()) {
//                    mapResult.put("accountNumber",y.getAccountNumber());
                    String accountNumber = y.getAccountNumber();
                    Flux<BankAccount> resp2 = this.webClient.get()
                            .uri("/bankAccount/getAllAmounts/{accountNumber}", accountNumber)
                            .retrieve()
                            .bodyToFlux(BankAccount.class);
                    resp2.subscribe(z->{
//                        mapResult.put("z",z.toString());
                        System.out.println("BankAccount passive--: "+z.getId());
                    });
                    System.out.println("-------------FILTRA PASSIVE--------------");
                    filterBA.add(resp2);
                }
            }

            if(x.getActiveList().size()>0) {
                for (Active z : x.getActiveList()) {
                    String accountNumber2 = z.getAccountNumber();
                    Flux<BankAccount> resp3 = this.webClient.get()
                            .uri("/bankAccount/getAllAmounts/{accountNumber2}", accountNumber2)
                            .retrieve()
                            .bodyToFlux(BankAccount.class);
                    resp3.subscribe(w -> {
                        System.out.println("BankAccount active--: " + w.getId());
                    });
                    System.out.println("-------------FILTRA ACTIVE--------------");
                    filterBA.add(resp3);
                }
            }
//            System.out.println("mapResult1: "+mapResult.get("documentNumber"));

        });

//        System.out.println("mapResult2: "+mapResult.get("documentNumber"));

        Report response = new Report();

        return Flux.just(response);
    }

}
