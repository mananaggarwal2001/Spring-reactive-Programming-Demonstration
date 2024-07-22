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
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Set;
import java.util.UUID;

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
    public String ingridient(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findById(recipeId));
        model.addAttribute("recipeId", recipeId);
        return "ingredient/list";
    }

    // for showing the recipe ingredient
    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        return "ingredient/show";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String showUpdateIngredientform(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model themodel) {
        themodel.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        List<UnitOfMeasureCommand> unitOfMeasureList= unitOfMeasureService.listAllUom().collectList().block();
        themodel.addAttribute("uomList", unitOfMeasureList);
        themodel.addAttribute("recipeId", recipeId);
        return "ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String updateTheIngredients(@PathVariable("recipeId") String recipePath, @ModelAttribute("ingredient") IngredientCommand filledIngredients) {
        System.out.println(recipePath);
        System.out.println("The filled ingredient is :- " + filledIngredients.getDescription());
        System.out.println("The uom descriptionn is :- " + filledIngredients.getUnitOfMeasure().getId());
        System.out.println("Form post mapping this is being displayed:- " + filledIngredients.getId());
        if (filledIngredients.getId().isEmpty()) {
            filledIngredients.setId(UUID.randomUUID().toString());
        }
        IngredientCommand returnedIngredientCommand = ingredientService.saveIngredientCommand(filledIngredients);
        return "redirect:/recipe/" + recipePath + "/ingredient/" + returnedIngredientCommand.getId() + "/show";
//       return "redirect:/";
    }

    // for creting the new recipe controller for doing the stuffs right.
    @GetMapping("recipe/{recipeId}/ingredient/new")
    public String createNewRecipe(@PathVariable("recipeId") String recipeId, Model themodel) {
        System.out.println(recipeId);
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        themodel.addAttribute("ingredient", ingredientCommand);
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());
        themodel.addAttribute("uomList", unitOfMeasureService.listAllUom().collectList().block());
        return "ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}/delete")
    public String recipeController(@PathVariable("recipeId") String recipeId, @PathVariable("id") String ingredientId, Model themodel) {
        System.out.println(recipeId);
        System.out.println(ingredientId);
        ingredientService.deleteIngredient(ingredientId, recipeId);
        return "redirect:/recipe/ingredient/" + recipeId;
    }
}
