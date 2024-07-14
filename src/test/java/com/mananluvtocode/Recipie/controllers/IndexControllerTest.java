package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    IndexController controller;
    MockMvc mockMvc;

    Set<Recipe> recipes;

    @Before
    public void setUp() throws Exception {
        recipes = new HashSet<>();
        recipes.add(Recipe.builder().id(1L).cookTime(2).build());
        recipes.add(Recipe.builder().id(2L).cookTime(10).build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getIndexPage() {
        when(recipeService.getAllRecipes()).thenReturn(recipes);
    }
}