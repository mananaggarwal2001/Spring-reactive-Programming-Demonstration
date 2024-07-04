package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    public Set<Recipe> getAllRecipes();

    public Recipe findById(Long id);
}
