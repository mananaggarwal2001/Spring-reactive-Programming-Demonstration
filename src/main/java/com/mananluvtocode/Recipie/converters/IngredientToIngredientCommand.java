package com.mananluvtocode.Recipie.converters;

import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {
    private final unitOfMeasureToUnitOfMeasureCommandConverter uomConverter;

    public IngredientToIngredientCommand(unitOfMeasureToUnitOfMeasureCommandConverter uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Override
    public IngredientCommand convert(Ingredient source) {
        if (source == null) {
            return null;
        }
        final IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(source.getId());
        ingredientCommand.setDescription(source.getDescription());
        if(source.getRecipe()!=null){
            ingredientCommand.setRecipeId(source.getRecipe().getId());
        }
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        return ingredientCommand;
    }
}
