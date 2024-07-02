package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class IndexControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    Model themodel;

    // testing will be done on this class only.
    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {
        String viewName = indexController.getIndexPage(themodel);
        // for testing whether the function is returning the correct String or not for the frontend.
        assertEquals("index", viewName);
        // for verifying that this 2 mocks will be called a 1 number of time
        // for checking that the controller function is being called for the one number of time.
        verify(recipeService, times(1)).getAllRecipes();
        // for checking that the service will be called one number of time.
        // the below statement means that the model is being checked whether it's called 1 time or more than 1 time.
        // and the addAttribute which is the function of the model tells that the attribute name should be
        // name which is given in the original and value should be passed as Set data structure nothing else is allowed.
        // in the getIndexPage() which is made in the index controller.
        // we have used the equal matcher for matching the String with the original String.
        verify(themodel, times(1)).addAttribute(eq("recipes"), anySet());
    }
}