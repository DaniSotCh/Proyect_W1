package com.nttdata.projectw2.domain.entity;

import com.nttdata.projectw2.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Passive {

    private String documentNumberCustomer;
    private ProductTypeEnum productType;
    private String accountNumber;
    private boolean commission;
    private Double commissionAmount;
    private int movementLimit;
    private Double actualAmount;
    private Double averageMinAmount;
    private List<Headline> headline;
    private List<Headline> signature;
}