package com.nttdata.projectw1.domain.entity;

import com.nttdata.projectw1.domain.util.constant.MovementTypeEnum;
import com.nttdata.projectw1.domain.util.constant.ProductTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Getter
@Setter
@Document("bankAccount")
public class BankAccount {
    @Id
    private String Id;
    private String documentNumberCustomer;
    private String accountNumber;
    private ProductTypeEnum productType;
    private Double amount;
    private LocalDateTime date;
    private MovementTypeEnum movementType;
    private Double commissionAmount;
}