package com.mananluvtocode.Recipie.commands;

import com.mananluvtocode.Recipie.domain.Difficulty;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import java.sql.Blob;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/21/17.
 */
@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private String id;

    @NotNull
    @NotEmpty
    @Size(min = 3, max = 300)
    private String description;

    @Min(value = 1)
    @Max(value = 255)
    private Integer prepTime;


    @Min(1)
    @Max(300)
    private Integer cookTime;

    @Min(1)
    @Max(300)
    private Integer servings;
    private String source;
    @URL
    @NotBlank
    private String url;


    @NotBlank
    private String directions;

    private Set<IngredientCommand> ingredients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Blob image;
    private Set<CategoryCommand> categories = new HashSet<>();

    @Builder
    public RecipeCommand(String id) {
        if (id != null) {
            this.id = id;
        }
    }
}