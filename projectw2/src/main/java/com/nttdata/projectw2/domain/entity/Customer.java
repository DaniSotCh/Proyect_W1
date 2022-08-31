package com.nttdata.projectw2.domain.entity;
import com.nttdata.projectw2.domain.util.constant.CustomerTypeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
public class Customer {
    private String documentNumber;
    private CustomerTypeEnum customerType;
    private String name;
    private List<Passive> passiveList;
    private List<Active> activeList;
}