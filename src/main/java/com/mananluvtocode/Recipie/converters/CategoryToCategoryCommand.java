package com.mananluvtocode.Recipie.converters;


import com.mananluvtocode.Recipie.commands.CategoryCommand;
import com.mananluvtocode.Recipie.domain.Category;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand> {
    @Synchronized
    @Nullable
    @Override
    public CategoryCommand convert(Category source) {
        if (source == null) {
            return null;
        }
        final CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(source.getId());
        categoryCommand.setDescription(source.getDescription());
        return categoryCommand;
    }
}
