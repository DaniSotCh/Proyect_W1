package com.nttdata.projectw1.domain.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductList {
    private List<Passive> passiveList;
    private List<Active> activeList;
}
