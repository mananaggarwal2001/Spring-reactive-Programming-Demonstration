package com.mananluvtocode.Recipie.repositories.reactive;

import com.mananluvtocode.Recipie.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        // block statement is to force the previous statement to run
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() {
        Recipe recipe = new Recipe();
        recipe.setDescription("This is new recipe");
        // block is used for forcing the action to do without any publisher.
        recipeReactiveRepository.save(recipe).block();
        Long finalvalue = recipeReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), finalvalue);
    }

    @Test
    public void findByDescription() {
        Recipe recipe = new Recipe();
        recipe.setDescription("Recipe");
        recipeReactiveRepository.save(recipe).then().block();
        Recipe resultRecipe = recipeReactiveRepository.findById(recipe.getId()).block();
        assertNotNull(resultRecipe);
    }
}