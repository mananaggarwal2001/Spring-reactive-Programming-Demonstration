package com.mananluvtocode.Recipie.repositories;

import com.mananluvtocode.Recipie.domain.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends MongoRepository<Ingredient, String> {

}
