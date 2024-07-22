package com.mananluvtocode.Recipie.repositories;

import com.mananluvtocode.Recipie.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {

    Optional<Category> findByDescription(String american);
}