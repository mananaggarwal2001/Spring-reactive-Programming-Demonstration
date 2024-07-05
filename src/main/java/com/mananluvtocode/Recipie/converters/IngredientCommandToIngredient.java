package com.mananluvtocode.Recipie.converters;


import com.mananluvtocode.Recipie.commands.IngredientCommand;
import com.mananluvtocode.Recipie.domain.Ingredient;
import jakarta.annotation.Nullable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {
    private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {
        if (source == null) {
            return null;
        }
        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUnitOfMeasure(uomConverter.convert(source.getUnitOfMeasure()));
        return ingredient;
    }
}
