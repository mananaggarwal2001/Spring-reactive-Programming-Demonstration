package com.mananluvtocode.Recipie.converters;


import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.domain.Notes;
import com.mananluvtocode.Recipie.domain.Recipe;
import jakarta.annotation.Nullable;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {
    private final CategoryCommandToCategory category;
    private final IngredientCommandToIngredient ingredient;
    private final NotesCommandToNotes notes;

    public RecipeCommandToRecipe(CategoryCommandToCategory category, IngredientCommandToIngredient ingredient, NotesCommandToNotes notes) {
        this.category = category;
        this.ingredient = ingredient;
        this.notes = notes;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setCookTime(source.getCookTime());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDescription(source.getDescription());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setDirections(source.getDirections());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setNotes(notes.convert(source.getNotes()));
        // for setting the categories we have

        if (source.getCategories() != null && !source.getCategories().isEmpty()) {
            source.getCategories().forEach(element -> recipe.getCategories().add(category.convert(element)));
        }

        // for the ingredients we have this command.

        if (source.getIngredients() != null && !source.getIngredients().isEmpty()) {
            source.getIngredients().forEach(element -> recipe.getIngridients().add(ingredient.convert(element)));
        }

        return recipe;
    }
}
