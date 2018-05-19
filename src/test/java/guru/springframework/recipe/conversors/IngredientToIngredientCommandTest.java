package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.IngredientCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.Ingredient;
import guru.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);
    public static final Long UOM_ID = new Long(1L);
    private static final BigDecimal AMOUNT = new BigDecimal("1");

    IngredientToIngredientCommand conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new Ingredient()));
    }

    @Test
    public void convert(){
        //given
        Ingredient ingredient = new Ingredient();
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(ID);
        ingredient.setUom(uom);
        ingredient.setDescription(DESCRIPTION);
        ingredient.setId(UOM_ID);
        ingredient.setAmount(AMOUNT);

        //wben
        IngredientCommand result = this.conversor.convert(ingredient);

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
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(DESCRIPTION);
        ingredient.setId(UOM_ID);
        ingredient.setAmount(AMOUNT);

        //wben
        IngredientCommand result = this.conversor.convert(ingredient);

        //then
        assertNotNull(result);
        assertNull(result.getUom());
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
        assertEquals(AMOUNT, result.getAmount());
    }
}