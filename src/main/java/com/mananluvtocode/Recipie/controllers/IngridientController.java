package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import com.mananluvtocode.Recipie.service.IngredientService;
import com.mananluvtocode.Recipie.service.RecipeService;
import com.mananluvtocode.Recipie.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Set;

@Controller
public class IngridientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngridientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/ingredient/{recipeId}")
    public String ingridient(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId));
        model.addAttribute("recipeId", recipeId);
        return "ingredient/list";
    }

    // for showing the recipe ingredient
    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipeId") Long recipeId, @PathVariable("id") Long id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        return "ingredient/show";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String showUpdateIngredientform(@PathVariable("recipeId") Long recipeId, @PathVariable("id") Long id, Model themodel) {
        themodel.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        themodel.addAttribute("uomList", unitOfMeasureService.listAllUom());
        themodel.addAttribute("recipeId", recipeId);
        return "ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String updateTheIngredients(@PathVariable("recipeId") Long recipePath, @ModelAttribute("ingredient") IngredientCommand filledIngredients) {
        System.out.println(recipePath);
        System.out.println("The filled ingredient is :- " + filledIngredients.getDescription());
        System.out.println("The uom descriptionn is :- " + filledIngredients.getUnitOfMeasure().getId());
        IngredientCommand returnedIngredientCommand = ingredientService.saveIngredientCommand(filledIngredients);
        return "redirect:/recipe/" + recipePath + "/ingredient/" + returnedIngredientCommand.getId() + "/show";
//       return "redirect:/";
    }

    // for creting the new recipe controller for doing the stuffs right.
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String createNewRecipe(@PathVariable("recipeId") Long recipeId, Model themodel) {
        System.out.println(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        themodel.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        themodel.addAttribute("uomList", unitOfMeasureService.listAllUom());
        return "ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String recipeController(@PathVariable("recipeId") Long recipeId, @PathVariable("id") Long ingredientId, Model themodel) {
        System.out.println(recipeId);
        System.out.println(ingredientId);
        ingredientService.deleteIngredient(ingredientId);
        return "redirect:/recipe/ingredient/" + recipeId;
    }
}
