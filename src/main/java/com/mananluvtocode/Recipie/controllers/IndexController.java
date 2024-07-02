package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
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
    private final RecipeRepository recipeRepository;

    public IndexController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model themodel) {
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        themodel.addAttribute("recipes", recipes);
        log.debug("Getting the index page for this controller and then showing in the frontend.");
        return "index";
    }
}
