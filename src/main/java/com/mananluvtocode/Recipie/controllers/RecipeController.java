package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import com.mananluvtocode.Recipie.service.RecipeServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeServiceImpl recipeServiceImpl;

    public RecipeController(RecipeService recipeService, RecipeServiceImpl recipeServiceImpl) {
        this.recipeService = recipeService;
        this.recipeServiceImpl = recipeServiceImpl;
    }

    @GetMapping("/recipe/show/{id}")
    public String showByid(@PathVariable("id") Long id, Model themodel) {
        themodel.addAttribute("recipe", recipeService.findById(id));
        System.out.println(recipeService.findById(id).getIngridients());
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String getRecipeForm(Model themodel) {
        themodel.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/update/{id}")
    public String getRecipeUpdateForm(@PathVariable("id") Long id, Model themodel) {
        themodel.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }
    @PostMapping("/recipe/")
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") RecipeCommand recipe) {
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipe);
        return "redirect:/recipe/show/" + savedRecipe.getId();
    }
}
