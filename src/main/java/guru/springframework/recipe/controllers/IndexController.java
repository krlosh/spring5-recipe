package guru.springframework.recipe.controllers;

import guru.springframework.recipe.repository.CategoryRepository;
import guru.springframework.recipe.repository.UnitOfMeasureRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","index"})
    public String getIndexPage(){
        System.out.println("CAT id: " + this.categoryRepository.findByDescription("American").get().getId());
        System.out.println("Uom id: " + this.unitOfMeasureRepository.findByDescription("Teaspon").get().getId());


        return "index";
    }
}
