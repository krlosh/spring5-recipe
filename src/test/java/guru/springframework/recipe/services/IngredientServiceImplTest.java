package guru.springframework.recipe.services;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.conversors.IngredientCommandToIngredient;
import guru.springframework.recipe.conversors.IngredientToIngredientCommand;
import guru.springframework.recipe.conversors.UnitOfMeasureCommandToUnitOfMeasure;
import guru.springframework.recipe.conversors.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.Recipe;
import guru.springframework.recipe.domain.UnitOfMeasure;
import guru.springframework.recipe.repository.RecipeRepository;
import guru.springframework.recipe.repository.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    IngredientService ingredientService;
    private UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        uomToUomCommand = new
                UnitOfMeasureToUnitOfMeasureCommand();
        this.ingredientService = new IngredientServiceImpl(new IngredientToIngredientCommand(uomToUomCommand), new IngredientCommandToIngredient(new
                UnitOfMeasureCommandToUnitOfMeasure()), recipeRepository, unitOfMeasureRepository);
    }

    @Test
    public void findByRecipeIdAndIngredientId() {
        //given:
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        IngredientCommand ingredientCommand = this.ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

        //then
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());

    }

    @Test
    public void testSaveRecipeCommand() {
        //given:
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(2L);
        command.setId(3L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        Ingredient ingredient  =new Ingredient();
        ingredient.setId(3L);
        savedRecipe.addIngredient(ingredient);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(command);

        //then
        assertNotNull(savedCommand);
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }

    @Test
    public void testSaveNewIngredient(){
        //given:
        UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId(5L);
        IngredientCommand command = new IngredientCommand();
        command.setRecipeId(2L);
        command.setUom(uomCommand);
        command.setAmount(new BigDecimal(2));
        command.setDescription("new ingredient");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.setId(2L);
        Ingredient createdIngredient = new Ingredient();
        createdIngredient.setId(0L);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(5L);
        createdIngredient.setUom(uom2);
        createdIngredient.setAmount(new BigDecimal(2));
        createdIngredient.setDescription("new ingredient");
        savedRecipe.addIngredient(createdIngredient);

        when(this.recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(this.recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = this.ingredientService.saveIngredientCommand(command);

        //then
        assertNotNull(savedCommand);
        assertEquals("new ingredient", savedCommand.getDescription());
        assertEquals(new Long(2), savedCommand.getRecipeId());

        verify(recipeRepository,times(1)).findById(anyLong());
        verify(recipeRepository,times(1)).save(any(Recipe.class));

    }
}