package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
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

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
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
}
