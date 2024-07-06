package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.converters.IngredientCommandToIngredient;
import com.mananluvtocode.Recipie.converters.IngredientToIngredientCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import com.mananluvtocode.Recipie.repositories.UnitOfMeasureRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureRepository unitOfMeasureRepository, RecipeCommandToRecipe recipeCommandToRecipe, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public IngredientCommand findByRecipeIdAndId(Long recipeId, Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            log.debug("Recipe not found ");
        }
        Recipe foundRecipe = recipe.get();
        Optional<IngredientCommand> ingredient = foundRecipe.getIngridients()
                .stream().filter(foundingredient -> foundingredient.getId().equals(id))
                .map(finalingredient -> ingredientToIngredientCommand.convert(finalingredient))
                .findFirst();
        if (ingredient.isEmpty()) {
            log.debug("Ingredient not found");
        }
        return ingredient.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (recipeOptional.isEmpty()) {
            log.error("Recipe Not found for this id:- " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe originalRecipe = recipeOptional.get();
            Optional<Ingredient> foundIngredient = originalRecipe
                    .getIngridients()
                    .stream()
                    .filter(element -> element.getId().equals(ingredientCommand.getId())).findFirst();
            if (foundIngredient.isPresent()) {
                Ingredient replacedIngridient = foundIngredient.get();
                replacedIngridient.setDescription(ingredientCommand.getDescription());
                replacedIngridient.setAmount(ingredientCommand.getAmount());
                replacedIngridient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND FOR THIS ID")));
            } else {
                originalRecipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }
            Recipe savedRecipe = recipeRepository.save(originalRecipe);
            Optional<Ingredient> finalingredient = savedRecipe.getIngridients().stream().filter(element -> element.getId().equals(ingredientCommand.getId())).findFirst();
            IngredientCommand finalIngredientCommand = null;
            if (finalingredient.isPresent()) {
                finalIngredientCommand = ingredientToIngredientCommand.convert(finalingredient.get());
            }
            return finalIngredientCommand;
        }
    }
}
