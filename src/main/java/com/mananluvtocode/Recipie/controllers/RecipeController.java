package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.exceptions.NotFoundException;
import com.mananluvtocode.Recipie.exceptions.NumberException;
import com.mananluvtocode.Recipie.service.RecipeService;
import com.mananluvtocode.Recipie.service.RecipeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeServiceImpl recipeServiceImpl;

    public RecipeController(RecipeService recipeService, RecipeServiceImpl recipeServiceImpl) {
        this.recipeService = recipeService;
        this.recipeServiceImpl = recipeServiceImpl;
    }

    @GetMapping("/recipe/show/{id}")
    public String showByid(@PathVariable("id") String id, Model themodel) {
        Long finalid = numberFormatExceptionHandler(id);
        themodel.addAttribute("recipe", recipeService.findById(finalid));
        return "recipe/show";
    }

    @RequestMapping("recipe/new")
    public String getRecipeForm(Model themodel) {
        themodel.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    private Long numberFormatExceptionHandler(String value) {
        Long finalvalue = null;
        try {
            finalvalue = Long.parseLong(value);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new NumberException("Invalid ID Format Used:- " + value);
        }
        return finalvalue;
    }

    @GetMapping("/recipe/update/{id}")
    public String getRecipeUpdateForm(@PathVariable("id") String id, Model themodel) {
        Long finalId = numberFormatExceptionHandler(id);
        themodel.addAttribute("recipe", recipeService.findCommandById(finalId));
        return "recipe/recipeform";
    }

    @PostMapping("/recipe/")
    public String saveOrUpdateRecipe(@ModelAttribute("recipe") RecipeCommand recipe) {
        RecipeCommand savedRecipe = recipeService.saveRecipeCommand(recipe);
        return "redirect:/recipe/show/" + savedRecipe.getId();
    }

    // for delete endpoint we must do this thing which is :-
    @GetMapping("/recipe/delete/{id}")
    public String deleteRecipe(@PathVariable("id") String id) {
        recipeService.deleteById(numberFormatExceptionHandler(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView showingErrorPage(Exception exception) {
        log.error("Not Found Error Exception for doing the particular Task");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberException.class)
    public ModelAndView showBadRequestPage(Exception exception) {
        log.error("The url contains the inappropriate things which can cause the trouble");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
