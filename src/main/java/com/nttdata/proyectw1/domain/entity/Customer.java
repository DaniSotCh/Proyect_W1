package com.nttdata.proyectw1.domain.entity;

import com.nttdata.proyectw1.domain.util.constant.CustomerTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("customer")
public class Customer {
    @Id
    private String documentNumber;
    private CustomerTypeEnum customerType;
    private String name;
    private List<Passive> passiveList;
    private List<Active> activeList;
}