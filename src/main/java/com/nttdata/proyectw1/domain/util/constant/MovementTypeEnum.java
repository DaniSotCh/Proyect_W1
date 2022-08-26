package com.nttdata.proyectw1.domain.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovementTypeEnum {

    PAYMENT("PAY"),
    WITHDRAWAL("WIT"),
    DEPOSIT("DEP");

    private final String code;
}
