package com.nttdata.projectw2.application.controller;

import com.nttdata.projectw2.domain.entity.Report;
import com.nttdata.projectw2.domain.service.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    IReportService reportService;

    @GetMapping("/average/{documentNumber}")
    public Flux<Report> averageReport(@PathVariable String documentNumber) {
        return reportService.averageReport(documentNumber);
    }
}