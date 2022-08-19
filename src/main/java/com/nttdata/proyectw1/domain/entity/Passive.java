package com.nttdata.proyectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Passive {
    private String type;//ahorro - c.corriente- plazo fijo
    private boolean commission;
    private boolean movementLimit;
    private String movementType;//deposito - retiro
    private List<Client> headline;
    private List<Client> signature;
}