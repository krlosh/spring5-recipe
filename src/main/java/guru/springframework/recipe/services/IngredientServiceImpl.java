package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.conversors.IngredientCommandToIngredient;
import guru.springframework.recipe.conversors.IngredientToIngredientCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import guru.springframework.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);
        if (!recipeOptional.isPresent()) {
            throw new IllegalArgumentException("Recipe doesn't exist");
        }
        Recipe recipe = recipeOptional.get();
        Optional<IngredientCommand> optionalIngredientCommand = recipe.getIngredients().stream().filter(ingrendient -> ingrendient.getId().equals(ingredientId)).map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
        if(!optionalIngredientCommand.isPresent()){
            throw new IllegalArgumentException("Ingredient doesn't exist in the recipe");
        }
        return optionalIngredientCommand.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(ingredientCommand.getRecipeId());
        if(!recipeOptional.isPresent()){
            //todo throw error
            log.error("Not found recipe by id "+ ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient
                .getId()
                .equals(ingredientCommand.getId()))
                .findFirst();
        if(ingredientOptional.isPresent()){
            Ingredient ingredientFound = ingredientOptional.get();
            ingredientFound.setDescription(ingredientCommand.getDescription());
            ingredientFound.setAmount(ingredientCommand.getAmount());
            ingredientFound.setUom(this.unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this);
        }
        else {
            recipe.addIngredient(this.ingredientCommandToIngredient.convert(ingredientCommand));
        }
        Recipe savedRecipe = this.recipeRepository.save(recipe);

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
        if(!savedIngredientOptional.isPresent()){
            savedIngredientOptional =savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                    .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                    .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUom().getId())).findFirst();
        }
        //todo check fail
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Override
    public void deleteById(long recipeId, long ingredientId) {
        log.debug("Deleting ingredient with id" + recipeId + ":"+ ingredientId);
        Optional<Recipe> optionalRecipe  = this.recipeRepository.findById(recipeId);
        if(optionalRecipe.isPresent()){
            Recipe recipe = optionalRecipe.get();
            log.debug("Found recipe");
            Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();
            if (ingredientOptional.isPresent()){
                Ingredient ingredient = ingredientOptional.get();
                ingredient.setRecipe(null);
                recipe.getIngredients().remove(ingredient);
                this.recipeRepository.save(recipe);
            }
        }
    }
}
