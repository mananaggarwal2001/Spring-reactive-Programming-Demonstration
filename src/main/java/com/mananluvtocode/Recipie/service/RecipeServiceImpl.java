package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.exceptions.NotFoundException;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RecipeServiceImpl implements com.mananluvtocode.Recipie.service.RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipe;
    private final RecipeToRecipeCommand recipeCommand;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipe, RecipeToRecipeCommand recipeCommand, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipe = recipe;
        this.recipeCommand = recipeCommand;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getAllRecipes() {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
//            throw new RuntimeException("Recipe Not Found for the respective Id");
            throw new NotFoundException("Recipe Not Found For ID Value:- " + id);
        }
        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detactedRecipe = recipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detactedRecipe);
        RecipeCommand returnedRecipeCommand = recipeToRecipeCommand.convert(savedRecipe);
        log.debug("Id of the Saved Recipe is :- {}", returnedRecipeCommand.getId());
        return returnedRecipeCommand;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {
        return recipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(String idToDelete) {
        recipeRepository.deleteById(idToDelete);
    }

    @Override
    public Set<Ingredient> getIngridientByRecipeId(String recipeId) {
        Recipe finalRecipe = findById(recipeId);
        return finalRecipe.getIngridients();
    }
}
