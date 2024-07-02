package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {
    RecipeServiceImpl recipeService;
    // mockito is giving the empty set and it is not using the actual data for running the test cases.
    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    void getAllRecipes() {
        Recipe recipe = new Recipe();
        HashSet<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        // telling the mockito to return some data for doing the testing.
        when(recipeRepository.findAll()).thenReturn(recipes);
        Set<Recipe> recipes1 = recipeService.getAllRecipes();
        assertEquals(recipes1.size(), 1);
        // for doing the verification of the data whether the testing is done true or not.
        // we are telling mocktio verify that the findAll() method is called once and only once not more than once.
        // and if the function is called more than once then there is a problem.
        // Mockito verify() method can be used to test number of method invocations too. We can test exact number of times
        // , at least once
        // , at least, at most number of invocation times for a mocked method.
        verify(recipeRepository, times(1)).findAll();
    }
}