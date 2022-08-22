package com.nttdata.proyectw1.domain.entity;

import com.nttdata.proyectw1.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Active {
    private ProductTypeEnum productType;
    private Integer activeNumber;
    private Double creditLimit;
}