package com.mananluvtocode.Recipie.controllers;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.ImageService;
import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {
    @Mock
    RecipeService recipeService;
    @Mock
    ImageService imageService;
    @InjectMocks
    ImageController imageController;
    MockMvc mockMvc;

    Recipe recipe;
    @Before
    public void setUp() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(imageController).build();
    }

    @Test
    public void showUploadForm() throws Exception {

    }

    @Test
    public void handImagePost() {
    }

    @Test
    public void renderImageFromDB() {
    }
}