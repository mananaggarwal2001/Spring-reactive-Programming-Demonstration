package com.mananluvtocode.Recipie.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;


@EqualsAndHashCode(exclude = "recipes")
@Entity
@Getter
@Setter
// just hover over the Category and then press the alt-enter for opening the dialog box and then select the Create test for creating the testing environment for doing the testing.
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String description;
    @ManyToMany(mappedBy = "categories")
    private Set<Recipe> recipes;

}
