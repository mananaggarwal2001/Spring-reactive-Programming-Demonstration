package com.mananluvtocode.Recipie.commands;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class IngredientCommand {
    private Long id;
    private Long RecipeId;
    private String description;
    private BigDecimal amount;
    private UnitOfMeasureCommand unitOfMeasure;
}