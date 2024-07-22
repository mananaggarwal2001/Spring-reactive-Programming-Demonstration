package com.mananluvtocode.Recipie.repositories.reactive;

import com.mananluvtocode.Recipie.domain.Ingredient;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IngredientReactiveRepository extends ReactiveMongoRepository<Ingredient, String> {
}