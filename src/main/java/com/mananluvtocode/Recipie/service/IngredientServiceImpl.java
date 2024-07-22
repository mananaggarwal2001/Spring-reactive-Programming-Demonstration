package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.converters.IngredientCommandToIngredient;
import com.mananluvtocode.Recipie.converters.IngredientToIngredientCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.reactive.IngredientReactiveRepository;
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

    private final IngredientReactiveRepository ingredientReactiveRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IngredientServiceImpl(RecipeReactiveRepository recipeReactiveRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientReactiveRepository ingredientReactiveRepository, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureReactiveRepository = unitOfMeasureReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientReactiveRepository = ingredientReactiveRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(String recipeId, String id) {
        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();
        Optional<IngredientCommand> ingredient = recipe.getIngridients()
                .stream().filter(foundingredient -> foundingredient.getId().equals(id))
                .map(ingredientToIngredientCommand::convert)
                .findFirst();
        if (ingredient.isPresent()) {
            log.debug("Ingredient not found");
            return Mono.just(ingredient.get()).block();
        }
        return null;
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(ingredientCommand.getRecipeId()).blockOptional();
        if (recipeOptional.isEmpty()) {
            log.error("Recipe Not found for this id:- " + ingredientCommand.getRecipeId());
            System.out.println("If condition will run");
            return new IngredientCommand();
        } else {
//            System.out.println("else method will run for finding recipe");
            Recipe originalRecipe = recipeOptional.get();
            System.out.println(originalRecipe.getNotes());
            Optional<Ingredient> foundIngredient = originalRecipe
                    .getIngridients()
                    .stream()
                    .filter(element -> element.getId().equals(ingredientCommand.getId())).findFirst();
            if (foundIngredient.isPresent()) {
                Ingredient replacedIngridient = foundIngredient.get();
                replacedIngridient.setDescription(ingredientCommand.getDescription());
                replacedIngridient.setAmount(ingredientCommand.getAmount());
                replacedIngridient.setUnitOfMeasure(unitOfMeasureReactiveRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).blockOptional().orElseThrow(() -> new RuntimeException("UOM NOT FOUND FOR THIS ID")));
            } else {
                // add new ingredient for the recipe.
                Ingredient originalIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
                originalRecipe.addIngredient(originalIngredient);
            }
            Recipe savedRecipe = recipeReactiveRepository.save(originalRecipe).block();
            Optional<Ingredient> finalingredient = savedRecipe.getIngridients().stream()
                    .filter(element -> element.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(element -> element.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(element -> element.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                    .findFirst();
            IngredientCommand finalIngredientCommand = null;
            if (finalingredient.isPresent()) {
                finalIngredientCommand = ingredientToIngredientCommand.convert(finalingredient.get());
            }
            return finalIngredientCommand;
        }
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
