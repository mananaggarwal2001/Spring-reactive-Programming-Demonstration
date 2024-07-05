package com.mananluvtocode.Recipie.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    // mapped by is the property of the child class.
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Ingredient> ingridients;

    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    // Blob meaning Binary Large objects it is to allow database to Store the data more than 255 characters like image and all stuffs.
    // this also known as the large object fields.
    @Lob
    // this annotation is used for storing the large objects which can has more than 255 characters of the value for doing the work.
    private Byte[] image;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notes_id")
    private Notes notes;

    @ManyToMany
    @JoinTable(name = "recipe_categories"
            , joinColumns = @JoinColumn(name = "recipe_id")
            , inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories= new HashSet<>();

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
