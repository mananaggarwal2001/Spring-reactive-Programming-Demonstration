package com.mananluvtocode.Recipie.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingridients= new HashSet<>();

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public Byte[] getImage() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Set<Ingredient> getIngridients() {
        return ingridients;
    }

    public void setIngridients(Set<Ingredient> ingridients) {
        this.ingridients = ingridients;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addIngredient(Ingredient ripeAvocados) {
        Set<Ingredient> ingridients = this.getIngridients();
        if (ingridients == null) {
            ingridients = new HashSet<>();
        }
        ingridients.add(ripeAvocados);
        this.setIngridients(ingridients);
    }
}
