package com.mananluvtocode.Recipie.controllers;
import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.commands.UnitOfMeasureCommand;
import com.mananluvtocode.Recipie.service.IngredientService;
import com.mananluvtocode.Recipie.service.IngredientServiceImpl;
import com.mananluvtocode.Recipie.service.RecipeService;
import com.mananluvtocode.Recipie.service.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Controller
public class IngridientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;
    private final IngredientServiceImpl ingredientServiceImpl;

    public IngridientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService, IngredientServiceImpl ingredientServiceImpl) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
        this.ingredientServiceImpl = ingredientServiceImpl;
    }

    @GetMapping("/recipe/ingredient/{recipeId}")
    public String ingridient(@PathVariable String recipeId, Model model) {
        // this returns the mono and thymeleaf template engine is going to handle that mono in the frontend for fetching the real object from that mono.
        model.addAttribute("recipe", recipeService.findById(recipeId));
        model.addAttribute("recipeId", recipeId);
        return "ingredient/list";
    }

    // for showing the recipe ingredient
    @GetMapping("/recipe/{recipeId}/ingredient/{id}/show")
    public String showIngredient(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model model) {
        model.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id).subscribe());
        return "ingredient/show";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
    public String showUpdateIngredientform(@PathVariable("recipeId") String recipeId, @PathVariable("id") String id, Model themodel) {
        themodel.addAttribute("ingredient", ingredientService.findByRecipeIdAndId(recipeId, id));
        Flux<UnitOfMeasureCommand> unitOfMeasureList= unitOfMeasureService.listAllUom();
        themodel.addAttribute("uomList", unitOfMeasureList);
        themodel.addAttribute("recipeId", recipeId);
        return "ingredient/ingredientform";
    }

    @PostMapping("recipe/{recipeId}/ingredient")
    public String updateTheIngredients(@PathVariable("recipeId") String recipePath, @ModelAttribute("ingredient") IngredientCommand filledIngredients) {
        String id= UUID.randomUUID().toString();
        if (filledIngredients.getId().isEmpty()) {
            filledIngredients.setId(id);
        }
        ingredientServiceImpl.saveIngredientCommand(filledIngredients);
        return "redirect:/recipe/" + recipePath + "/ingredient/" + filledIngredients.getId() + "/show";
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
        themodel.addAttribute("uomList", unitOfMeasureService.listAllUom());
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
