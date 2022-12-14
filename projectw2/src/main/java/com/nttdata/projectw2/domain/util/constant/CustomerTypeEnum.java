package com.nttdata.projectw2.domain.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerTypeEnum {

    PERSONAL("PE"),
    VIP("VIP"),
    BUSINESS("BU"),
    PYME("PY");

    private final String code;
}
