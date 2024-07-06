package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;

public interface IngredientService {
    IngredientCommand findByRecipeIdAndId(Long recipeId, Long id);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
