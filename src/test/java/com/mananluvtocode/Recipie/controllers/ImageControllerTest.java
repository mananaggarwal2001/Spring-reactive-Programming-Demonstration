package com.mananluvtocode.Recipie.controllers;
import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.service.ImageService;
import com.mananluvtocode.Recipie.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
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
    WebTestClient webTestClient;

    Recipe recipe;
    @BeforeEach
    public void setUp() throws Exception {
        webTestClient = WebTestClient.bindToController(imageController).build();
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