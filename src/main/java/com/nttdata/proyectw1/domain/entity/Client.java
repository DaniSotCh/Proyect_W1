package com.nttdata.proyectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Client {
    private String clientType;
    private String name;
    private String documentNumber;
    private List<Product> productList;
}