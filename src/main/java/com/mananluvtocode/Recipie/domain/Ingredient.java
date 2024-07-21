package com.mananluvtocode.Recipie.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
//@EqualsAndHashCode(exclude = {"recipe"})
public class Ingredient {
    @Id
    private String id = UUID.randomUUID().toString();

    private String description;
    private BigDecimal amount;

    @DBRef
    private Recipe recipe;

    @DBRef
    private UnitOfMeasure unitOfMeasure;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }


    public Ingredient() {
    }
}
