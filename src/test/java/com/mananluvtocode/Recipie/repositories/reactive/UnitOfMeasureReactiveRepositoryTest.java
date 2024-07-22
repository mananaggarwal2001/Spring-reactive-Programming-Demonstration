package com.mananluvtocode.Recipie.repositories.reactive;

import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void findByDescription() {
        UnitOfMeasure unit = new UnitOfMeasure();
        unit.setDescription("Each");
        unitOfMeasureReactiveRepository.save(unit).block();
        UnitOfMeasure resultUnitOfmeasure = unitOfMeasureReactiveRepository.findByDescription("Each").block();
        assertNotNull(resultUnitOfmeasure);
    }

    @Test
    public void EmptyIdTest() {
        UnitOfMeasure unit = new UnitOfMeasure();
        unit.setDescription("Each");
        unitOfMeasureReactiveRepository.save(unit).then().block();
        UnitOfMeasure unitOfMeasure = unitOfMeasureReactiveRepository.findByDescription("Each").block();
        if (unit.getId().isEmpty()) {
            unit.setId(null);
        }
        assertNotNull(unit.getId());
    }
}