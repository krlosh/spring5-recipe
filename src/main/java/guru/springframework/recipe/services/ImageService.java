package guru.springframework.recipe.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(long l, MultipartFile file);
}
