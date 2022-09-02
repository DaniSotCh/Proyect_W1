package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements IReportService {
    private final WebClient webClient;

    public ReportServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @Override
    public Mono<Report> averageReport(String documentNumber) {

        Flux<BankAccount> respBankAccountCustomer = this.webClient.get()
                .uri("/bankAccount/getAllAmountsCustomer/{documentNumber}", documentNumber)
                .retrieve()
                .bodyToFlux(BankAccount.class);

        return respBankAccountCustomer.collectList().flatMap(x-> {
            Report reporte = new Report();
            reporte.setReportAverage(calculateSum(x));
            Mono<Report> res = Mono.just(reporte);
            return res;
        });

    }

    @Override
    public Mono<Report> averageCommission(String accountNumber, String amountMonth) {

        Flux<BankAccount> respBankAccountCustomer = this.webClient.get()
                .uri("/bankAccount/getAllAmounts/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToFlux(BankAccount.class);

        return respBankAccountCustomer.collectList().flatMap(x-> {
            Report reporte = new Report();
            reporte.setReportAverage(calculateCommission(x,amountMonth));
            Mono<Report> res = Mono.just(reporte);
            return res;
        });
    }

    private Map<String, Object> calculateCommission(List<BankAccount> x, String amountMonth) {
        LocalDateTime dateNow = LocalDateTime.now();

        x=x.stream()
                .filter(m->m.getDate().getMonth().compareTo(dateNow.getMonth().minus(Long.parseLong(amountMonth)))<=0
                        && m.getDate().getYear()==dateNow.getYear())
                .collect(Collectors.toList());

        Map<String,Object> mapeo=new HashMap<>();
        int i =0;
            for(BankAccount y:x){
                if(y.getCommissionAmount()!=null){
                    i++;
                    mapeo.put("id"+i,y.getId());
                    mapeo.put("CommisionByProduct"+i,y.getCommissionAmount());
                }
            }

        return mapeo;
    }

    private Map<String, Object> calculateSum(List<BankAccount> x) {

        LocalDateTime dateNow = LocalDateTime.now();
        YearMonth yearMonthObject = YearMonth.of(dateNow.getYear(), dateNow.getMonth());
        int daysInMonth = yearMonthObject.lengthOfMonth();

        x=x.stream()
                .filter(m->m.getDate().getMonth().equals(dateNow.getMonth()) && (m.getDate().getYear()==dateNow.getYear()))
                .collect(Collectors.toList());
        String[] unique = x.stream().map(BankAccount::getAccountNumber).distinct().toArray(String[]::new);

        double total=0;
        double commission=0;

        Map<String,Object> mapeo=new HashMap<>();


        for(int i =0 ; i<unique.length; i++){
            mapeo.put("accountNumber"+i,unique[i]);

            for(BankAccount y:x){

                if(unique[i].equals(y.getAccountNumber())){
                    total+=y.getAmount();
                    commission += y.getCommissionAmount();
                }

            }

            total = Math.round(((total-commission)/daysInMonth)*100.0)/100.0;
            mapeo.put("AveragePerDay"+i,total);
            total =0;
            commission = 0;
        }

        return mapeo;
    }

}
