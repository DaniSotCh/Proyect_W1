package com.nttdata.proyectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document("client")
public class Client {
    private String clientType;
    private String name;
    private String documentNumber;
    private List<Passive> passiveList;
    private List<Active> activeList;
}