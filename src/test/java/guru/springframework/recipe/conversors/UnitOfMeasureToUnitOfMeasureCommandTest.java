package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);

    UnitOfMeasureToUnitOfMeasureCommand conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert(){
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setDescription(DESCRIPTION);
        uom.setId(ID);

        //wben
        UnitOfMeasureCommand result = this.conversor.convert(uom);

        //then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
    }
}