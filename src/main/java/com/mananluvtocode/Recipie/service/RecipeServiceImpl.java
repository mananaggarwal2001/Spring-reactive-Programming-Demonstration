package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.converters.RecipeCommandToRecipe;
import com.mananluvtocode.Recipie.converters.RecipeToRecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public Recipe findById(Long id) {
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if (recipe.isEmpty()) {
            throw new RuntimeException("Recipe Not Found for the respective Id");
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
    public RecipeCommand findCommandById(Long id) {
        return recipeCommand.convert(findById(id));
    }
}
