package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getAllRecipes();

    Recipe findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(String id);
    void deleteById(String idToDelete);

    Set<Ingredient> getIngridientByRecipeId(String recipeId);
}
