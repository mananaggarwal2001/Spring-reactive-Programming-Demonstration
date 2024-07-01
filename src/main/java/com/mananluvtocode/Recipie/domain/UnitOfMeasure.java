package com.mananluvtocode.Recipie.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@Entity
public class UnitOfMeasure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @OneToMany(mappedBy = "unitOfMeasure")
    private Set<Ingredient> ingredient;

}
