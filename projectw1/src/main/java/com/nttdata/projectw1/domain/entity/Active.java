package com.nttdata.projectw1.domain.entity;

import com.nttdata.projectw1.domain.util.constant.ProductTypeEnum;
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