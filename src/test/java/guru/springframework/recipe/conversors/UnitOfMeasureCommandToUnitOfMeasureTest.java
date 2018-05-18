package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);

    UnitOfMeasureCommandToUnitOfMeasure conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert(){
        //given
        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setDescription(DESCRIPTION);
        uom.setId(ID);

        //wben
        UnitOfMeasure result = this.conversor.convert(uom);

        //then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
    }
}