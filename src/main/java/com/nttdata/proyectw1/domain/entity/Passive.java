package com.nttdata.proyectw1.domain.entity;

import com.nttdata.proyectw1.domain.util.constant.MovementTypeEnum;
import com.nttdata.proyectw1.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Passive {
    private ProductTypeEnum productType;
    private boolean commission;
    private boolean movementLimit;
    private MovementTypeEnum movementType;
    private List<Client> headline;
    private List<Client> signature;
}