package com.nttdata.projectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("product")
public class Product {
    @Id
    private String productId;
    private Passive passiveProduct;
    private Active activeProduct;
}