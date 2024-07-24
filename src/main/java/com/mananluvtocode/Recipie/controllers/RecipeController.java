package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.exceptions.NumberException;
import com.mananluvtocode.Recipie.service.RecipeService;
import com.mananluvtocode.Recipie.service.RecipeServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/show/{id}")
    public String showByid(@PathVariable("id") String id, Model themodel) {
//        System.out.println(recipeService.findById(id).block());
        themodel.addAttribute("recipe", recipeService.findById(id));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String getRecipeForm(Model themodel) {
        themodel.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

//    private Long numberFormatExceptionHandler(String value) {
//        Long finalvalue = null;
//        try {
//            finalvalue = Long.parseLong(value);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//            throw new NumberException("Invalid ID Format Used:- " + value);
//        }
//        return finalvalue;
//    }

    @GetMapping("/recipe/update/{id}")
    public String getRecipeUpdateForm(@PathVariable("id") String id, Model themodel) {

        themodel.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/recipeform";
    }

    @PostMapping("/recipe/")
    public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipe, BindingResult result, Model themodel) {
        if (result.hasErrors()) {
            result.getAllErrors().forEach(errors -> {
                log.debug(errors.toString());
            });
            return "recipe/recipeform";
        }
        if (recipe.getId().isEmpty()) {
            recipe.setId(UUID.randomUUID().toString());
        }
        if (recipe.getNotes().getId().isEmpty()) {
            recipe.getNotes().setId(UUID.randomUUID().toString());
        }
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipe);
        return "redirect:/recipe/show/" + savedRecipe.getId();
    }

    // for delete endpoint we must do this thing which is :-
    @GetMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable("id") String id) {
        recipeService.deleteById(id);
        return "redirect:/";
    }
}
