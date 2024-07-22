package com.mananluvtocode.Recipie.repositories.reactive;

import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryTest {
    @Autowired
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @Before
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