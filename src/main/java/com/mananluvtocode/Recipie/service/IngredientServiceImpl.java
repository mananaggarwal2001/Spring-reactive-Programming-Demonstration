package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.converters.IngredientCommandToIngredient;
import com.mananluvtocode.Recipie.converters.IngredientToIngredientCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.IngredientRepository;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import com.mananluvtocode.Recipie.repositories.UnitOfMeasureRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);
    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final IngredientRepository ingredientRepository;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCommand ingredientToIngredientCommand, UnitOfMeasureRepository unitOfMeasureRepository, RecipeCommandToRecipe recipeCommandToRecipe, IngredientCommandToIngredient ingredientCommandToIngredient, IngredientRepository ingredientRepository, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.ingredientRepository = ingredientRepository;
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
                replacedIngridient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND FOR THIS ID")));
            } else {
                // add new ingredient for the recipe.
                Ingredient originalIngredient = ingredientCommandToIngredient.convert(ingredientCommand);
                originalIngredient.setRecipe(originalRecipe);
                originalRecipe.addIngredient(originalIngredient);
            }
            Recipe savedRecipe = recipeRepository.save(originalRecipe);
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
//            return new IngredientCommand();
        }
    }

    @Override
    public void deleteIngredient(Long id) {
        Optional<Ingredient> findIngredient = ingredientRepository.findById(id);
        Ingredient ingredient = null;
        if (findIngredient.isPresent()) {
            ingredient = findIngredient.get();
        } else {
            throw new RuntimeException("Ingredient Not found");
        }
        Optional<Recipe> recipe = recipeRepository.findById(ingredient.getRecipe().getId());
        Recipe recipe1 = null;
        if (recipe.isPresent()) {
            recipe1 = recipe.get();
        }
        Recipe updatedRecipe= null;
        if (recipe1 != null && recipe1.getIngridients() != null) {
            recipe1.getIngridients().remove(ingredient);
            recipe1.setIngridients(recipe1.getIngridients());
            updatedRecipe = recipeRepository.save(recipe1);
        }

        System.out.println(updatedRecipe);

        ingredient.setRecipe(null);
        ingredient.setUnitOfMeasure(null);
        ingredientRepository.delete(ingredient);
    }
}
