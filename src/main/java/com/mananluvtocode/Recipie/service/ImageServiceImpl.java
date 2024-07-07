package com.mananluvtocode.Recipie.service;

import com.mananluvtocode.Recipie.domain.Recipe;
import com.mananluvtocode.Recipie.repositories.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.sql.Blob;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try {
            Optional<Recipe> recipe = recipeRepository.findById(recipeId);
            Recipe finalrecipe = recipe.get();
            Blob blob = new SerialBlob(file.getBytes());
            finalrecipe.setImage(blob);
            recipeRepository.save(finalrecipe);
        } catch (Exception e) {
            // todo handle things better for doing all the things right.
            log.error("Images Uploading Error occured for going the things wrong.");
            e.printStackTrace();
        }
    }
}
