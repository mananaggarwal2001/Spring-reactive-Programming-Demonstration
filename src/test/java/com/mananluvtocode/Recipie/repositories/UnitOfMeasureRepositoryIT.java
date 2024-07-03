package com.mananluvtocode.Recipie.repositories;

import com.mananluvtocode.Recipie.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


// This is used for doing the integration testing for the Spring boot.
@RunWith(SpringRunner.class)
@DataJpaTest
// this will bring up the Jpa database and that will configure the Spring data JPA for us for doing the work with the JPA Repository for testing.
public class UnitOfMeasureRepositoryIT {

    @Autowired // tell spring to intialize it automatically and then use this repository for testing.
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {

    }

//    @Test
//    public void findByDescription() {
//        // for extracting the unitOfMeasure for doing the work.
//        Optional<UnitOfMeasure> unit = unitOfMeasureRepository.findByDescription("Teaspoon");
//        assertEquals("Teaspoon", unit.get().getDescription());
//    }
}