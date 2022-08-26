package com.nttdata.proyectw1.domain.util.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductTypeEnum {

    CURRENT_ACCOUNT("CUA","PAS"),
    CHECKING_ACCOUNT("CHA","PAS"),
    FIXED_TERM_DEPOSITS("FTD","PAS"),
    PERSONAL_CREDIT("PCR","ACT"),
    BUSINESS_CREDIT("BCR","ACT"),
    CREDIT_CARD("CRC","ACT");

    private final String code;
    private final String type;

}
