package com.nttdata.projectw2.domain.entity;

import lombok.Getter;
import lombok.Setter;
import reactor.core.publisher.Flux;

import java.util.List;

@Getter
@Setter
public class Report {
    List<BankAccount> reportAverage;
}
