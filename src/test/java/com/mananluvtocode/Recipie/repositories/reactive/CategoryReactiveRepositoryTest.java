package com.mananluvtocode.Recipie.repositories.reactive;

import com.mananluvtocode.Recipie.domain.Category;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryTest {
    @Autowired
    CategoryReactiveRepository categoryReactiveRepository;

    @Before
    public void setUp() throws Exception {
        categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveCategory() {
        Category category = new Category();
        category.setDescription("foo");
        categoryReactiveRepository.save(category).then().block();
        // not null test
        Category findedCategory= categoryReactiveRepository.findByDescription("foo").block();
        assertNotNull(findedCategory.getId());
    }


    @Test
    public void findByDescription() {
        Category category = new Category();
        category.setDescription("Foo");
        categoryReactiveRepository.save(category).then().block();
        // this method is used for finding the category using its description.
        Category fetchedCat = categoryReactiveRepository.findByDescription("Foo").block();
        assertNotNull(fetchedCat.getId());
    }
}