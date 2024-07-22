package com.mananluvtocode.Recipie.repositories;

import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UnitOfMeasureRepository extends MongoRepository<UnitOfMeasure, String> {

    Optional<UnitOfMeasure> findByDescription(String each);
}
