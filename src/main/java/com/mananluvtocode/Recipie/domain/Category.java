package com.mananluvtocode.Recipie.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.UUID;


@EqualsAndHashCode(exclude = "recipes")
@Getter
@Setter
@Document(collection = "category")
// just hover over the Category and then press the alt-enter for opening the dialog box and then select the Create test for creating the testing environment for doing the testing.
public class Category {
    @Id
    private String id=UUID.randomUUID().toString();

    private String description;

    @DBRef
    private Set<Recipe> recipes;

}
