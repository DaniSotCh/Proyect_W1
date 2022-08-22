package com.nttdata.proyectw1.domain.entity;

import com.nttdata.proyectw1.domain.util.constant.MovementTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class BankAccount {
    private Customer customer;
    private Product product;
    private Double amount;
    private LocalDateTime date;
    private MovementTypeEnum movementType;
}