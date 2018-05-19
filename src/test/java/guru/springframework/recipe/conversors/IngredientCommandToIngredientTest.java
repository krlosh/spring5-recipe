package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);
    public static final Long UOM_ID = new Long(1L);
    private static final BigDecimal AMOUNT = new BigDecimal("1");

    IngredientCommandToIngredient conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new IngredientCommand()));
    }

    @Test
    public void convert(){
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(ID);
        ingredientCommand.setUom(uom);
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setId(UOM_ID);
        ingredientCommand.setAmount(AMOUNT);

        //wben
        Ingredient result = this.conversor.convert(ingredientCommand);

        //then
        assertNotNull(result);
        assertNotNull(result.getUom());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
        assertEquals(AMOUNT, result.getAmount());
        assertEquals(UOM_ID, result.getUom().getId());
    }

    @Test
    public void convertWithNullUom() {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setId(UOM_ID);
        ingredientCommand.setAmount(AMOUNT);

        //wben
        Ingredient result = this.conversor.convert(ingredientCommand);

        //then
        assertNotNull(result);
        assertNull(result.getUom());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
        assertEquals(AMOUNT, result.getAmount());
    }
}