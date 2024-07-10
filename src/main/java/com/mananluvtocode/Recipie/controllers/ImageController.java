package com.mananluvtocode.Recipie.controllers;

import com.mananluvtocode.Recipie.commands.RecipeCommand;
import com.mananluvtocode.Recipie.service.ImageService;
import com.mananluvtocode.Recipie.service.RecipeService;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;


@Controller
public class ImageController {
    private final RecipeService recipeService;
    private final ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{id}/image")
    public String showUploadForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(id));
        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{id}/image")
    public String handImagePost(@PathVariable Long id, @RequestParam("imagefile") MultipartFile file) {
        imageService.saveImageFile(id, file);
        return "redirect:/recipe/show/" + id;
    }

    // for showing the image in the frontend for doing the things right.
    @GetMapping("recipe/{id}/recipeImage")
    public void renderImageFromDB(@PathVariable("id") Long recipeId, HttpServletResponse response) throws IOException, SQLException {
        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        byte[] byteArray = recipeCommand.getImage().getBytes(1L, (int) recipeCommand.getImage().length());
        response.setContentType("image/png");
        InputStream is = new ByteArrayInputStream(byteArray);
        // maven is providing this to convert this input stream to the respective output stream for doing the things right.
        IOUtils.copy(is, response.getOutputStream());
    }
//    @GetMapping("/recipe/{id}/recipeImage")
//    public ResponseEntity<byte[]> getRecipeImage(@PathVariable("id") Long id) throws IOException, SQLException {
//        RecipeCommand recipeCommand = recipeService.findCommandById(id);
//        byte[] getBytes = recipeCommand.getImage().getBytes(1L, (int) recipeCommand.getImage().length());
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(getBytes);
//    }
}
