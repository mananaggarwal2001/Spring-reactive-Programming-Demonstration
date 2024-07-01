package com.mananluvtocode.Recipie.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "recipes")
@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
