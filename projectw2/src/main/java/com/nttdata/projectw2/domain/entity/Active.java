package com.nttdata.projectw2.domain.entity;

import com.nttdata.projectw2.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Active {
    private ProductTypeEnum productType;
    private String accountNumber;
    private Integer activeNumber;
    private Double creditLimit;
    private Double actualAmount;
}