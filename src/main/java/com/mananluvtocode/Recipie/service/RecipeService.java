package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {
    Flux<Recipe> getAllRecipes();

    Mono<Recipe> findById(String id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    Mono<RecipeCommand> findCommandById(String id);
    void deleteById(String idToDelete);

    Mono<Recipe> getIngridientByRecipeId(String recipeId);
}
