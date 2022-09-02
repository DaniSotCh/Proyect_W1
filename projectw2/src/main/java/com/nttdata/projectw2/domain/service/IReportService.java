package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.Report;
import reactor.core.publisher.Mono;

public interface IReportService {
    Mono<Report> averageReport(String documentNumber);

    Mono<Report> averageCommission(String accountNumber, String amountMonth);
}
