package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.conversors.*;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.repository.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeToRecipeCommand = new RecipeToRecipeCommand(new NotesToNotesCommand(), new CategoryToCategoryCommand(), new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
        this.recipeService = new RecipeServiceImpl(this.recipeRepository,
                                    new RecipeCommandToRecipe(new NotesCommandToNotes(),new CategoryCommandToCategory(),new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure())),
                recipeToRecipeCommand);
    }


    @Test
    public void getRecipes() {
        Set<Recipe> recipeData = new HashSet<>();
        recipeData.add(new Recipe());

        when(recipeRepository.findAll()).thenReturn(recipeData);

        Set<Recipe> result = this.recipeService.getRecipes();

        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).findAll();

    }

    @Test
    public void getRecipeById(){
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe foundRecipe = this.recipeService.findById(1L);
        assertNotNull(foundRecipe);
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(0)).findAll();
    }

    @Test
    public void saveRecipe(){
        //given:
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.save(any(Recipe.class))).thenReturn(recipe);
        RecipeCommand recipeCmdToSave = this.recipeToRecipeCommand.convert(recipe);

        //when
        RecipeCommand result = this.recipeService.saveRecipe(recipeCmdToSave);

        //then
        assertNotNull(result);
        assertEquals(recipe.getId(), result.getId());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }

    @Test
    public void deleteRecipe(){
        //Given
        Long recipeId = 1L;

        //when
        this.recipeService.deleteById(recipeId);

        //then
        verify(this.recipeRepository,times(1)).deleteById(anyLong());
    }
}