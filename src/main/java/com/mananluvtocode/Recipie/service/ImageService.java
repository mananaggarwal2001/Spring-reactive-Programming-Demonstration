package com.mananluvtocode.Recipie.service;

import org.springframework.data.repository.CrudRepository;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file);
}