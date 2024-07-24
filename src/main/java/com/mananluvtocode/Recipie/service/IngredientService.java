package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientCommand> findByRecipeIdAndId(String recipeId, String id);

    Mono<Void> saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteIngredient(String ingredientId, String recipeId);
}
