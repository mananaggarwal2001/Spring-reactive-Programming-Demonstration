package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;

import com.mananluvtocode.Recipie.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class RecipeServiceImpl implements com.mananluvtocode.Recipie.service.RecipeService {
    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipe;
    private final RecipeToRecipeCommand recipeCommand;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
    private final RecipeReactiveRepository recipeReactiveRepository;

    @Autowired
    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeCommandToRecipe recipe, RecipeToRecipeCommand recipeCommand, RecipeToRecipeCommand recipeToRecipeCommand, RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeRepository = recipeRepository;
        this.recipe = recipe;
        this.recipeCommand = recipeCommand;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Flux<Recipe> getAllRecipes() {
        Flux<Recipe> recipes = recipeRepository.findAll();
        return recipes;
    }

    @Override
    public Mono<Recipe> findById(String id) {
//        if (recipe == null) {
////            throw new RuntimeException("Recipe Not Found for the respective Id");
//            throw new NotFoundException("Recipe Not Found For ID Value:- " + id);
//        }
        return recipeRepository.findById(id);
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detactedRecipe = recipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detactedRecipe).block();
        RecipeCommand returnedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);
        log.debug("Id of the Saved Recipe is :- {}", returnedRecipeCommand.getId());
        return returnedRecipeCommand;
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return recipeReactiveRepository.findById(id).map(element -> {
            RecipeCommand recipeCommand = recipeToRecipeCommand.convert(element);
            recipeCommand.getIngredients().forEach(ingredient -> {
                ingredient.setRecipeId(recipeCommand.getId());
            });
            return recipeCommand;
        });
    }

    @Override
    @Transactional
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

    @Override
    public Mono<Recipe> getIngridientByRecipeId(String recipeId) {
        Mono<Recipe> finalRecipe = findById(recipeId);
        return finalRecipe;
    }
}
