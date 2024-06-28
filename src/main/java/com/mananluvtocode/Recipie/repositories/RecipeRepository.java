package com.mananluvtocode.Recipie.repositories;

import com.mananluvtocode.Recipie.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
