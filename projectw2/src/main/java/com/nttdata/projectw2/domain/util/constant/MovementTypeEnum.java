package com.nttdata.projectw2.domain.util.constant;

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
