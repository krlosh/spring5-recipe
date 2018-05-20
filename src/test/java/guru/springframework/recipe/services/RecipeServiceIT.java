package guru.springframework.recipe.services;

import antlr.ASTNULLType;
import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.conversors.RecipeCommandToRecipeTest;
import guru.springframework.recipe.conversors.RecipeToRecipeCommand;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {


    public static final String NEW_DESCRIPTION = "NEW_DESCRIPTION";

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeCommand conversor;

    @Autowired
    private RecipeService recipeService;

    @Test
    @Transactional
    public void saveRecipeDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = this.recipeRepository.findAll();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand testRecipeCommand = this.conversor.convert(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = this.recipeService.saveRecipe(testRecipeCommand);

        //then
        assertNotNull(savedRecipeCommand);
        assertEquals(NEW_DESCRIPTION, savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}