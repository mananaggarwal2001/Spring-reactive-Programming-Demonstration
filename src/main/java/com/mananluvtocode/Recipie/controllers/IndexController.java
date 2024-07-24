package com.mananluvtocode.Recipie.controllers;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

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
        themodel.addAttribute("recipes", recipeService.getAllRecipes());
        log.debug("Getting the index page for this controller and then showing in the frontend.");
        return "index";
    }
}
