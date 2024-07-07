package com.mananluvtocode.Recipie.converters;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {
    private final CategoryToCategoryCommand category;
    private final IngredientToIngredientCommand ingredient;
    private final NotesToNotesCommand notes;

    public RecipeToRecipeCommand(CategoryToCategoryCommand category, IngredientToIngredientCommand ingredient, NotesToNotesCommand notes) {
        this.category = category;
        this.ingredient = ingredient;
        this.notes = notes;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe source) {
        if (source == null) {
            return null;
        }
        final RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(source.getId());
        recipeCommand.setCookTime(source.getCookTime());
        recipeCommand.setPrepTime(source.getPrepTime());
        recipeCommand.setDescription(source.getDescription());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setImage(source.getImage());
        recipeCommand.setNotes(notes.convert(source.getNotes()));

        if (source.getCategories() != null && !source.getCategories().isEmpty()) {
            source.getCategories().forEach(element -> recipeCommand.getCategories().add(category.convert(element)));
        }
        if (source.getIngridients() != null && !source.getIngridients().isEmpty()) {
            source.getIngridients().forEach(element -> recipeCommand.getIngredients().add(ingredient.convert(element)));
        }
        return recipeCommand;
    }
}
