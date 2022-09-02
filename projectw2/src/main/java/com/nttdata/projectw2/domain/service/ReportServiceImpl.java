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

        return respBankAccountCustomer.collectList().flatMap(x -> {
            Report responseReport = new Report();
            responseReport.setReportAverage(calculateSum(x));
            return Mono.just(responseReport);
        });

    }

    @Override
    public Mono<Report> averageCommission(String accountNumber, String amountMonth) {

        Flux<BankAccount> respBankAccountCustomer = this.webClient.get()
                .uri("/bankAccount/getAllAmounts/{accountNumber}", accountNumber)
                .retrieve()
                .bodyToFlux(BankAccount.class);

        return respBankAccountCustomer.collectList().flatMap(x -> {
            Report responseReport = new Report();
            responseReport.setReportAverage(calculateCommission(x, amountMonth));
            return Mono.just(responseReport);
        });
    }

    private Map<String, Object> calculateCommission(List<BankAccount> x, String amountMonth) {
        LocalDateTime dateNow = LocalDateTime.now();

        x = x.stream().filter(m ->
                dateNow.minusMonths(Long.parseLong(amountMonth)).compareTo(m.getDate()) <= 0
        ).collect(Collectors.toList());

        Map<String, Object> obtainMap = new HashMap<>();
        int i = 0;
        for (BankAccount y : x) {
            if (y.getCommissionAmount() != null) {
                i++;
                obtainMap.put("id" + i, y.getId());
                obtainMap.put("commissionByProduct" + i, y.getCommissionAmount());
            }
        }

        return obtainMap;
    }

    private Map<String, Object> calculateSum(List<BankAccount> x) {

        LocalDateTime dateNow = LocalDateTime.now();
        YearMonth yearMonthObject = YearMonth.of(dateNow.getYear(), dateNow.getMonth());
        int daysInMonth = yearMonthObject.lengthOfMonth();

        x = x.stream()
                .filter(m -> m.getDate().getMonth().equals(dateNow.getMonth()) && (m.getDate().getYear() == dateNow.getYear()))
                .collect(Collectors.toList());
        String[] unique = x.stream().map(BankAccount::getAccountNumber).distinct().toArray(String[]::new);

        double total = 0;
        double commission = 0;

        Map<String, Object> obtainMap = new HashMap<>();


        for (int i = 0; i < unique.length; i++) {
            obtainMap.put("accountNumber" + i, unique[i]);

            for (BankAccount y : x) {

                if (unique[i].equals(y.getAccountNumber())) {
                    total += y.getAmount();
                    commission += y.getCommissionAmount();
                }

            }

            total = Math.round(((total - commission) / daysInMonth) * 100.0) / 100.0;
            obtainMap.put("AveragePerDay" + i, total);
            total = 0;
            commission = 0;
        }

        return obtainMap;
    }

}
