package com.mananluvtocode.Recipie.controllers;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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
        recipes.add(Recipe.builder().id(anyString()).cookTime(2).build());
        recipes.add(Recipe.builder().id(anyString()).cookTime(10).build());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getIndexPage() {
        when(recipeService.getAllRecipes()).thenReturn(recipes);
    }
}