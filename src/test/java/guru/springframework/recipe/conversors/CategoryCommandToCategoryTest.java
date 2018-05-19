package guru.springframework.recipe.conversors;

import guru.springframework.recipe.commands.CategoryCommand;
import guru.springframework.recipe.commands.UnitOfMeasureCommand;
import guru.springframework.recipe.domain.Category;
import guru.springframework.recipe.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {

    public static final String DESCRIPTION = "description";
    public static final Long ID = new Long(1L);

    CategoryCommandToCategory conversor;

    @Before
    public void setUp() throws Exception {
        conversor = new CategoryCommandToCategory();
    }

    @Test
    public void testNullParameter(){
        assertNull(conversor.convert(null));
    }

    @Test
    public void testEmpyObject() {
        assertNotNull(conversor.convert(new CategoryCommand()));
    }

    @Test
    public void convert(){
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(DESCRIPTION);
        categoryCommand.setId(ID);

        //wben
        Category result = this.conversor.convert(categoryCommand);

        //then
        assertNotNull(result);
        assertEquals(DESCRIPTION, result.getDescription());
        assertEquals(ID, result.getId());
    }
}