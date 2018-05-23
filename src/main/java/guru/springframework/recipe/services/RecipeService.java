package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.Recipe;

import java.util.Optional;
import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(long l);

    RecipeCommand saveRecipe (RecipeCommand recipeCommand);

    RecipeCommand findRecipeCommandById(Long id);

    void deleteById(long l);
}
