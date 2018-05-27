package guru.springframework.recipe.controllers;

import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ImageController {
    private ImageService imageService;
    private RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("recipe/{id}/image")
    public String showImageForm(@PathVariable String id, Model model) {
        model.addAttribute("recipe", this.recipeService.findRecipeCommandById(Long.valueOf(id)));
        return "/recipe/imageuploadform";
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile") MultipartFile file){

        this.imageService.saveImageFile(Long.valueOf(id), file);
        return "redirect:/recipe/"+id+"/show";
    }
}
