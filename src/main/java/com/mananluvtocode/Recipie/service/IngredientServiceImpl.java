package com.mananluvtocode.Recipie.service;
import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.converters.IngredientCommandToIngredient;
import com.mananluvtocode.Recipie.converters.IngredientToIngredientCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.reactive.RecipeReactiveRepository;
import com.mananluvtocode.Recipie.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.Set;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndId(String recipeId, String id) {
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        Optional<IngredientCommand> ingredient = recipe.getIngridients()
                .stream().filter(foundingredient -> foundingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();
        if (ingredient.isPresent()) {
            log.debug("Ingredient not found");
            return Mono.just(ingredient.get());
        }
        return null;
    }

    @Override
    public Mono<Void> saveIngredientCommand(IngredientCommand command) {
        Mono<Recipe> recipeMono = recipeReactiveRepository.findById(command.getRecipeId());
        Ingredient ingredient = ingredientCommandToIngredient.convert(command);
        System.out.println("Recipe Mono is just the recipe Mono:- " + recipeMono);
        Mono<Recipe> updatedRecipe = recipeMono.flatMap(recipe -> {
            System.out.println("This is flatmap function used for saving the recipe");
            recipe.getIngridients().add(ingredient);
            return recipeReactiveRepository.save(recipe);
        });
        updatedRecipe.subscribe();
        return Mono.empty();
    }


    @Override
    @Transactional
    public void deleteIngredient(String ingridientId, String recipeId) {
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        System.out.println(recipe);
        Set<Ingredient> ingredients = recipe.getIngridients();
        Optional<Ingredient> ingredientToBeDeleted = recipe.getIngridients().stream().filter(object -> ingridientId.equals(object.getId())).findFirst();
        if (ingredientToBeDeleted.isPresent()) {
            ingredients.remove(ingredientToBeDeleted.get());
        }
        recipe.setIngridients(ingredients);
        recipeReactiveRepository.save(recipe).block();
    }
}
