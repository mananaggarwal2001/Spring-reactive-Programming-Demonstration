package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndId(String recipeId, String id);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(String ingredientId, String recipeId);
}
