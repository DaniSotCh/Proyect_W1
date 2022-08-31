package com.nttdata.projectw2.domain.service;

import com.nttdata.projectw2.domain.entity.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReportService {
    Flux<Report> averageReport(String documentNumber);
}
