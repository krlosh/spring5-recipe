package guru.springframework.recipe.controllers;

import guru.springframework.recipe.services.ImageService;
import guru.springframework.recipe.services.RecipeService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

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

    @GetMapping("/recipe/{id}/recipeimage")
    public void renderImageFromDB(@PathVariable String id, HttpServletResponse response) throws IOException {
        Byte[] recipeImageAsBytes = this.recipeService.findRecipeCommandById(Long.valueOf(id)).getImage();
        if(recipeImageAsBytes!=null){
            byte[] bytesToShow = new byte[recipeImageAsBytes.length];
            int i=0;
            for(Byte b:recipeImageAsBytes){
                bytesToShow[i++] = b;
            }
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(bytesToShow);
            IOUtils.copy(inputStream,response.getOutputStream());
        }
    }
}
