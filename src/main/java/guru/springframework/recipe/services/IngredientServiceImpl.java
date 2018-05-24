package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.conversors.IngredientToIngredientCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private IngredientToIngredientCommand converter;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand converter, RecipeRepository recipeRepository) {
        this.converter = converter;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new IllegalArgumentException("Recipe doesn't exist");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream().filter(ingrendient -> ingrendient.getId().equals(ingredientId)).map(ingredient -> converter.convert(ingredient)).findFirst();
        if(!optionalIngredientCommand.isPresent()){
            throw new IllegalArgumentException("Ingredient doesn't exist in the recipe");
        }
        return optionalIngredientCommand.get();
    }
}
