package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import com.mananluvtocode.Recipie.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository, RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeRepository = recipeRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        Mono<Recipe> returnedrecipeObject = recipeReactiveRepository.findById(recipeId).map(recipe -> {
            Byte[] imagebytes = new Byte[0];
            try {
                imagebytes = new Byte[file.getBytes().length];
                int i = 0;
                for (Byte b : file.getBytes()) {
                    imagebytes[i++] = b;
                }

                recipe.setImage(imagebytes);
                return recipe;
            } catch (IOException exception) {
                exception.printStackTrace();
                throw new RuntimeException(exception.getMessage());
            }
        });
        recipeReactiveRepository.save(returnedrecipeObject.block()).block();
        return Mono.empty();
    }
}
