package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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

//    @Test
//    void getIndexPage() {
//        String viewName = indexController.getIndexPage(themodel);
//        // for testing whether the function is returning the correct String or not for the frontend.
//        assertEquals("index", viewName);
//        // for verifying that this 2 mocks will be called a 1 number of time
//        // for checking that the controller function is being called for the one number of time.
//        verify(recipeService, times(1)).getAllRecipes();
//        // for checking that the service will be called one number of time.
//        // the below statement means that the model is being checked whether it's called 1 time or more than 1 time.
//        // and the addAttribute which is the function of the model tells that the attribute name should be
//        // name which is given in the original and value should be passed as Set data structure nothing else is allowed.
//        // in the getIndexPage() which is made in the index controller.
//        // we have used the equal matcher for matching the String with the original String.
//        verify(themodel, times(1)).addAttribute(eq("recipes"), anySet());
//    }


//    @Test
//    void getIndexPage() {
//        String viewName = indexController.getIndexPage(themodel);
//        // what is the need for the argument capture ??
//        // for doing the verification of the set that we are providing to the addAttribute we use the argument capture.
////        Set<Recipe> recipes = new HashSet<>();
////        Recipe recipe1 = new Recipe();
////        recipe1.setId(12L);
////        Recipe recipe2 = new Recipe();
////        recipe2.setId(13L);
////        recipes.add(recipe1);
//        // in the statement above we are providing the 2 recipes.
//        // using the mockito when for returning the actual values for doing the stuffs.
////        when(recipeService.getAllRecipes()).thenReturn(recipes);
//        // for checking whether the set is the valid set or not.
//        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
//
//
//        assertEquals("index", viewName);
//        verify(recipeService, times(1)).getAllRecipes();
//        verify(themodel, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
//        Set<Recipe> recipeSet = argumentCaptor.getValue();
//        System.out.println(recipeSet.size());
////        assertEquals(2, recipeSet.size());
//    }


    // Introduction to the SpringMockMVC Test

    // this is basically the unit testing for testing the particular endpoint for doing the things right.

//    @Test
//    void testMocMVC() {
//        // It is the fluent API
//        MockMvc mockMvc= MockMvcBuilders.standaloneSetup(indexController).build();
//        mockMvc.perform(get()).andExpect(status().isOk()).andExpect(view().name("index"));
//    }

    @Test
    void getIndexPage() {
        String viewName = indexController.getIndexPage(themodel);
        // what is the need for the argument capture ??
        // for doing the verification of the set that we are providing to the addAttribute we use the argument capture.
//        Set<Recipe> recipes = new HashSet<>();
//        Recipe recipe1 = new Recipe();
//        recipe1.setId(12L);
//        Recipe recipe2 = new Recipe();
//        recipe2.setId(13L);
//        recipes.add(recipe1);
        // in the statement above we are providing the 2 recipes.
        // using the mockito when for returning the actual values for doing the stuffs.
//        when(recipeService.getAllRecipes()).thenReturn(recipes);
        // for checking whether the set is the valid set or not.
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);


        assertEquals("index", viewName);
        verify(recipeService, times(1)).getAllRecipes();
        verify(themodel, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipeSet = argumentCaptor.getValue();
        System.out.println(recipeSet.size());
//        assertEquals(2, recipeSet.size());
    }
}