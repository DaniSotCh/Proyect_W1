package com.nttdata.projectw2.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankTransfer {

    private String originAccountNumber;
    private String targetAccountNumber;
    private Double amount;

}
