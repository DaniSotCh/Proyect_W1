package com.nttdata.projectw2.application.controller;

import com.nttdata.projectw2.domain.entity.Report;
import com.nttdata.projectw2.domain.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    IReportService reportService;

    @GetMapping("/average/{documentNumber}")
    public Mono<Report> averageReport(@PathVariable String documentNumber) {
        return reportService.averageReport(documentNumber);
    }

    @GetMapping("/averageCommission/{accountNumber}/{amountMonth}")
    public Mono<Report> averageCommission(@PathVariable String accountNumber, @PathVariable String amountMonth) {
        return reportService.averageCommission(accountNumber,amountMonth);
    }
}