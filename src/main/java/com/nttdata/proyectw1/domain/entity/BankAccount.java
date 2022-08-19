package com.nttdata.proyectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class BankAccount {
    private Client client;
    private Product product;
    private Double amount;
    private Date date;
    private String movementType;
}