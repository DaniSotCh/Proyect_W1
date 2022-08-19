package com.nttdata.proyectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Active {
    private String type;//personal - empresarial - tc
    private Double creditLimit;
}