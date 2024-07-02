package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@Slf4j
public class IndexController {
    // for writing the multiple requests for showing the same page.
    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model themodel) {
        Set<Recipe> recipes = new HashSet<>(recipeService.getAllRecipes());
        themodel.addAttribute("recipes", recipes);
        log.debug("Getting the index page for this controller and then showing in the frontend.");
        return "index";
    }
}
