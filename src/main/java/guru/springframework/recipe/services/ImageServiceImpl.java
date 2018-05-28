package guru.springframework.recipe.services;

import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(long id, MultipartFile file) {
        log.debug("Received image" + id);

        int i=0;
        Recipe recipe = this.recipeRepository.findById(id).get();
        try {
            Byte[] byteObject = new Byte[file.getBytes().length];
            for (byte b:file.getBytes()){
                byteObject[i++] = b;
            }
            recipe.setImage(byteObject);
            this.recipeRepository.save(recipe);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
