package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.conversors.RecipeCommandToRecipe;
import guru.springframework.recipe.conversors.RecipeToRecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.exceptions.NotFoundException;
import guru.springframework.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I'm in the service");
        Set<Recipe> recipeSet = new HashSet<>();
        this.recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(long l) {
        Optional<Recipe> optionalRecipe = this.recipeRepository.findById(l);
        if (!optionalRecipe.isPresent()) {
            throw new NotFoundException("Recipe not found!");
        }
        return optionalRecipe.get();
    }

    @Transactional
    @Override
    public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
        Recipe recipe = this.recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = this.recipeRepository.save(recipe);
        log.debug("Saved Recipe with id:"+savedRecipe.getId());
        return this.recipeToRecipeCommand.convert(savedRecipe);
    }

    @Transactional
    @Override
    public RecipeCommand findRecipeCommandById(Long id) {
        Recipe recipe = this.findById(id);
        return this.recipeToRecipeCommand.convert(recipe);
    }

    @Override
    public void deleteById(long id) {
        this.recipeRepository.deleteById(id);
    }
}
