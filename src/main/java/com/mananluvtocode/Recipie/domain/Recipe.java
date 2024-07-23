package com.mananluvtocode.Recipie.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Document(collection = "recipe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recipe {
    @Id
    private String id = UUID.randomUUID().toString();
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    // mapped by is the property of the child class.
    private Set<Ingredient> ingridients = new HashSet<>();
    private Difficulty difficulty;

    // Blob meaning Binary Large objects it is to allow database to Store the data more than 255 characters like image and all stuffs.
    // this also known as the large object fields.
    // this annotation is used for storing the large objects which can has more than 255 characters of the value for doing the work.
    private Byte[] image;
    private Notes notes;

    @DBRef
    private Set<Category> categories = new HashSet<>();

    @Builder
    public Recipe(String id, String description, Integer prepTime, Integer cookTime, Integer servings, String source, String url, String directions, Difficulty difficulty) {
        this.id = id;
        this.description = description;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.source = source;
        this.url = url;
        this.directions = directions;
        this.difficulty = difficulty;
    }

    public void addIngredient(Ingredient ripeAvocados) {
        Set<Ingredient> ingridients = this.getIngridients();
        System.out.println(ingridients);
        if (ingridients == null) {
            ingridients = new HashSet<>();
        }
        ingridients.add(ripeAvocados);
        this.setIngridients(ingridients);
        System.out.println(this.getIngridients());
    }

}
