package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.RecipeCommand;
import guru.springframework.recipe.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class RecipeToRecipeCommandTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My Recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CAT_ID_1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID_1 = 3L;
    public static final Long INGRED_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;
    RecipeToRecipeCommand converter;

    @Before
    public void setUp(){
        this.converter = new RecipeToRecipeCommand(new NotesToNotesCommand(), new CategoryToCategoryCommand(),new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));

    }

    @Test
    public void testNullParameter(){
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void convert() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);
        recipe.setCookTime(COOK_TIME);
        recipe.setPrepTime(PREP_TIME);
        recipe.setDescription(DESCRIPTION);
        recipe.setDifficulty(DIFFICULTY);
        recipe.setDirections(DIRECTIONS);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);

        recipe.setNotes(notes);

        Category category = new Category();
        category.setId(CAT_ID_1);

        Category category2 = new Category();
        category2.setId(CAT_ID2);

        recipe.getCategories().add(category);
        recipe.getCategories().add(category2);

        Ingredient ingredient = new Ingredient();
        ingredient.setId(INGRED_ID_1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGRED_ID_2);

        recipe.addIngredient(ingredient);
        recipe.addIngredient(ingredient2);

        //when
        RecipeCommand result = this.converter.convert(recipe);

        //then
        assertNotNull(result);
        assertEquals(RECIPE_ID, result.getId());
        assertEquals(COOK_TIME, result.getCookTime());
        assertEquals(PREP_TIME, result.getPrepTime());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(DIFFICULTY, result.getDifficulty());
        assertEquals(DIRECTIONS, result.getDirections());
        assertEquals(SERVINGS, result.getServings());
        assertEquals(SOURCE, result.getSource());
        assertEquals(URL, result.getUrl());
        assertEquals(NOTES_ID, result.getNotes().getId());
        assertEquals(2, result.getCategories().size());
        assertEquals(2, result.getIngredients().size());
    }
}