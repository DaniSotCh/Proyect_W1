package com.nttdata.proyectw1.domain.entity;

import com.nttdata.proyectw1.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Passive {
    private ProductTypeEnum productType;
    private String accountNumber;
    private boolean commission;
    private boolean movementLimit;
    private Double actualAmount;
    private List<Headline> headline;
    private List<Headline> signature;
}